package com.it.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: WebSocketServer  
 * @Description: TODO  WebSocket服务端代码，包含接收消息，推送消息等接口
 * @author Administrator  
 * @date 2020年10月19日  
 *
 */
@Component
@ServerEndpoint(value = "/ws/{userName}")//userName是接收的参数
public class WebSocketServer {
	private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);
	static Map<Session, String> map = new HashMap<Session, String>();     //存储系统的用户信息
	
	   /**
	    * 
	    * @Title: OnOpen  
	    * @Description: TODO  建立连接
	    * @param @param userName
	    * @param @param session    参数  
	    * @return void    返回类型  
	    * @throws
	    */
	   @OnOpen
	   public void OnOpen(@PathParam("userName")String userName,Session session) {
		   	map.put(session, userName);
		   	log.info("websocket连接建立成功...");
	   }

	   /**
	    * 连接关闭的方法
	    */
	   @OnClose
	   public void OnClose(Session session) {
	   		if (map.containsKey(session)) {
	   			map.remove(session);
			}
	   		log.info("websocket连接已经关闭...");
	   }

	   /**
	    * 接收消息的方法
	    * @param msg
	    * @param session
	    * @throws InterruptedException 
	    */
	   @OnMessage
	   public void OnMessage(String msg, Session session) throws InterruptedException {
	       log.info("已从客户端接收消息：" + msg);
	   }

		/**
	    * 出错的方法，注意参数不能错
	    * @param session
	    * @param error
	    */
	   @OnError
	   public void OnError(Session session,Throwable error) {
	   		if (map.containsKey(session)) {
	   			map.remove(session);
			}
	   		log.info("websocket出错...");
	   }
	   /**
	    * 推送数据的方法
	    * @param session map里存的登录信息
	    * @param message 推送数据
	    */
	   public void sendMessage(Session session,String message){
			try {
				session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }

}
