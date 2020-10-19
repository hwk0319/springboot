package com.it.shiro;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.it.po.UserInfo;
import com.it.util.Util;

/**
 * 
 * @ClassName: KickoutSessionControlFilter  
 * @Description: TODO  自定义过滤器，配置账号被踢出或在其它地方登陆限制
 * @author Administrator  
 * @date 2020年9月17日  
 *
 */
public class KickoutSessionControlFilter extends AccessControlFilter{
	
	private Logger logger = LoggerFactory.getLogger(KickoutSessionControlFilter.class);
	
	private String kickoutUrl; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
    //设置Cache的key的前缀
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro_redis_cache");
    }

    /**
             * 是否允许访问，如果允许访问返回true，否则false；
     */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	   * 当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }

        Session session = subject.getSession();
        UserInfo user = (UserInfo) subject.getPrincipal();
        String username = user.getName();
        Serializable sessionId = session.getId();

        //读取缓存,没有就存入
        Deque<Serializable> deque = cache.get(username);
        if(deque == null){
        	deque = new LinkedList<Serializable>();
        }
        
        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            //将sessionId存入队列
            deque.push(sessionId);
            //将用户的sessionId队列缓存
            cache.put(username, deque);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if(kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            //踢出后再更新下缓存队列
            cache.put(username, deque);
            
            try {
                //获取被踢出的sessionId的session对象
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        Object kickout = session.getAttribute("kickout");
        if (kickout != null && (Boolean)kickout == true) {
            //会话被踢出了
            try {
                //退出登录
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(request);
			//判断是不是Ajax请求
			if (Util.isAjaxRequest((HttpServletRequest) request)) {
				String result = "<script>alert('您已被踢出或账号在其它地方登录，请重新登录！');top.location.href='login';</script>";
				//输出内容
				out(response, result);
			}else{
				//重定向
				WebUtils.issueRedirect(request, response, kickoutUrl);
			}
        }
        return true;
	}
	
	/**
	 * 
	 * @Title: out  
	 * @Description: TODO  向页面输出内容
	 * @param @param response
	 * @param @param result
	 * @param @throws IOException    参数  
	 * @return void    返回类型  
	 * @throws
	 */
	private void out(ServletResponse response, String result)
			throws IOException {
		try {
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.println(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("KickoutSessionFilter 输出数据异常");
		}
	}

}
