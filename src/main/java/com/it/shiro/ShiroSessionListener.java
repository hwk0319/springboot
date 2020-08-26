package com.it.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * 
 * @ClassName: ShiroSessionListener  
 * @Description: TODO  配置session监听器
 * @author Administrator  
 * @date 2019年9月20日  
 *
 */
public class ShiroSessionListener implements SessionListener{
	/**
     * 统计在线人数
     * juc包下线程安全自增
     */
    private final AtomicInteger sessionCount = new AtomicInteger(0);

    /**
     * 会话创建时触发
     * @param session
     */
	@Override
	public void onStart(Session session) {
		//会话创建，在线人数加一
        sessionCount.incrementAndGet();
	}

	 /**
     * 退出会话时触发
     * @param session
     */
	@Override
	public void onStop(Session session) {
		//会话退出,在线人数减一
        sessionCount.decrementAndGet();
	}

	/**
     * 会话过期时触发
     * @param session
     */
    @Override
    public void onExpiration(Session session) {
        //会话过期,在线人数减一
        sessionCount.decrementAndGet();
    }
    
    /**
     * 获取在线人数使用
     * @return
     */
    public AtomicInteger getSessionCount() {
        return sessionCount;
    }

}
