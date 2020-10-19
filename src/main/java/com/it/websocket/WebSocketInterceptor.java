package com.it.websocket;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * 
 * @ClassName: WebSocketInterceptor  
 * @Description: TODO  WebSocket拦截器
 * @author Administrator  
 * @date 2020年9月22日  
 *
 */
public class WebSocketInterceptor implements HandshakeInterceptor{
	private Logger log = LoggerFactory.getLogger(WebSocketInterceptor.class);

	//在握手之前执行该方法, 继续握手返回true, 中断握手返回false. 通过attributes参数设置WebSocketSession的属性
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			String ID = request.getURI().toString().split("ID=")[1];
			log.info("current session id is:"+ID);
			attributes.put("WEBSOCKET_USERID",ID);
		}
		return true;
	}

	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
		log.info("coming webSocketInterceptor afterHandshake method...");
	}

}
