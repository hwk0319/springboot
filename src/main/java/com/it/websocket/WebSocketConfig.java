package com.it.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 
 * @ClassName: WebSocketConfig  
 * @Description: TODO  WebSocket配置
 * @author Administrator  
 * @date 2020年9月22日  
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new MyWebSocketHandler(), "/websocket/{ID}")
		.setAllowedOrigins("*")
		.addInterceptors(new WebSocketInterceptor());
	}

}
