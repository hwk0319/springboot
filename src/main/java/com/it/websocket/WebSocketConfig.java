package com.it.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 
 * @ClassName: WebSocketConfig  
 * @Description: TODO  WebSocket配置
 * @author Administrator  
 * @date 2020年9月22日  
 *
 */
@Configuration
public class WebSocketConfig{

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

}
