package com.it.websocket;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;

import com.it.po.UserInfo;

public class PushMessage {
	@Autowired
	private static MyWebSocketHandler handler;
	
	public static void push() {
		String msg = "登录成功";
		UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
		handler.sendMessageToUser(String.valueOf(user.getId()), new TextMessage(msg));
		
	}

}
