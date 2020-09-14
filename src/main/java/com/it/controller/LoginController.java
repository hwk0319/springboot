package com.it.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.it.po.LoginInfo;
import com.it.po.Permission;
import com.it.po.UserInfo;
import com.it.service.LoginLogsService;
import com.it.service.LoginService;
import com.it.service.SystemMenuService;

import eu.bitwalker.useragentutils.UserAgent;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="loginService")
	private LoginService service;
	@Resource(name = "loginLogsService")
	private LoginLogsService loginLogsService;
	@Resource
	private SystemMenuService menuservice;
//	private static String publicKeyString = null;
	
	/**
	 * 登录页面
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String login(HttpServletRequest req) {
		return "login/login";
	}
	
	/**
	 * 跳转到主页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView indexHtml(Model model) {
		Object obj = SecurityUtils.getSubject().getPrincipal();
		UserInfo user = (UserInfo) obj;
		List<Permission> list = menuservice.findMenuByUser(user);
		List<Permission> list1 = new ArrayList<Permission>();
		for (int i = 0; i < list.size(); i++) {
			List<Permission> list2 = new ArrayList<Permission>();
			if(list.get(i).getPid() == 0) {
				for (int j = 0; j < list.size(); j++) {
					if(list.get(j).getPid() == list.get(i).getId()) {
						Permission p = list.get(j);
						list2.add(p);
					}
				}
				Permission p1 = list.get(i);
				p1.setChildern(list2);
				list1.add(p1);
			}
		}
		model.addAttribute("menu", list1);
		return new ModelAndView("index");
	}
	
	/**
	 * 
	 * @Title: signUp  
	 * @Description: TODO  跳转到注册页
	 * @param @param req
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping("/signUp")
	public String signUp(HttpServletRequest req) {
		return "login/registration";
	}
	
	/**
	 * 
	 * @Title: resetPwd  
	 * @Description: TODO  重置密码页
	 * @param @param req
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping("/resetPwd")
	public String resetPwd(HttpServletRequest req) {
		return "login/reset-password";
	}
	
	/**
	 * 初始化rsa公钥
	 * @return
	 */
	/*@RequestMapping(value="/initModels")
	@ResponseBody
	public String initModels1(HttpServletRequest request){
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/");
		RSAUtils.setUrlPath(path);
		RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
		publicKeyString = new String(Hex.encodeHex(publicKey.getModulus().toByteArray()));
		request.getSession().setAttribute("model", publicKeyString);
		return publicKeyString;
	}*/
	
	/**
	 * @throws Exception 
	 * @Title: login  
	 * @Description: TODO  此方法不处理登录成功,由shiro进行处理
	 * @param @param name
	 * @param @param pwd
	 * @param @param req
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject login(String name, String pwd, HttpServletRequest req) throws Exception {
		// 1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
        JSONObject json = new JSONObject();
        try{
        	// 3.执行登录方法
            subject.login(token);
            req.getSession().setAttribute("userName", name);
            json.put("status", "success");
            json.put("msg", "登录成功！");
        } catch (UnknownAccountException e){
        	json.put("status", "failed");
        	json.put("msg", "用户名不存在！");
        } catch (IncorrectCredentialsException e){
        	json.put("status", "failed");
        	json.put("msg", "用户名或密码错误！");
        }
        recordLoginInfo(json.getString("status"), json.getString("msg"), req);
		return json;
	}

	/**
	 * 用户注销
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout")
	@ResponseBody
	public String logout(HttpServletRequest req) throws Exception {
		//取出当前验证主体
		Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
        	//不为空，执行一次logout的操作，将session全部清空
            subject.logout();
        }
        return "login";
	}
	
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUsername")
	@ResponseBody
	public UserInfo getUsername(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		UserInfo user = (UserInfo) subject.getPrincipal();
		return user;
	}	
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: recordLoginInfo  
	 * @Description: TODO  记录登录信息
	 * @param @param subject    参数  
	 * @return void    返回类型  
	 * @throws
	 */
	public void recordLoginInfo(String status, String msg, HttpServletRequest req) throws Exception {
		String name = req.getParameter("name");
		String ip = req.getRemoteAddr();
		UserAgent userAgent = UserAgent.parseUserAgentString(req.getHeader("User-Agent"));
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setUserName(name);
		loginInfo.setIp(ip);
//		String location = AddressUtils.getAddresses("ip="+ip, "UTF-8");
//		loginInfo.setLocation(location);
		loginInfo.setLocation("");
		loginInfo.setBrowser(browser);
		loginInfo.setOperatingSystem(os);
		loginInfo.setStatus(status);
		loginInfo.setMessage(msg);
		loginLogsService.addLoginInfo(loginInfo);
	}
}