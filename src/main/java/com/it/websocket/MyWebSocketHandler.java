package com.it.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: MyWebSocketHandler  
 * @Description: TODO  MyWebSocketHandler处理器
 * @author Administrator  
 * @date 2020年9月22日  
 *
 */
@Service
public class MyWebSocketHandler implements WebSocketHandler{
	private Logger log = LoggerFactory.getLogger(MyWebSocketHandler.class);

	//在线用户列表
	private static final Map<String, WebSocketSession> users;

	static {
		users = new HashMap<>();
	}

	/**
	 * 建立新的socket连接后回调的方法
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("connect websocket successful!");
		String ID = session.getUri().toString().split("ID=")[1];
		if (ID != null) {
			users.put(ID, session);
			session.sendMessage(new TextMessage("{\"message\":\"socket successful connection!\"}"));
			log.info("id:" + ID + ",session:" + session + "");
		}
		log.info("current user number is:"+users.size());
	}

	/**
	 * 接收客户端发送的Socket。获取客户端发送的信息
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		try{
			JSONObject jsonobject = JSONObject.fromObject((String)message.getPayload());
			log.info("receive message:" + jsonobject);
			sendMessageToAllUsers(new TextMessage(jsonobject.toString()));
		}catch (Exception e) {
			log.error("e",e);
		}
	}

	/**
	 * 连接出错时，回调的方法
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		log.error("connect error", exception);
		users.remove(getClientId(session));
	}

	/**
	 * 连接关闭时，回调的方法
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.error("connection closed: " + closeStatus);
		users.remove(getClientId(session));
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 发送信息给指定用户
	 * @param clientId
	 * @param message
	 * @return
	 */
	public boolean sendMessageToUser(String clientId, TextMessage message) {
		if (users.get(clientId) == null) return false;
		WebSocketSession session = users.get(clientId);
		log.info("sendMessage:" + message);
		if (!session.isOpen()) {
			return false;
		} 
		try {
			session.sendMessage(message);
		} catch (IOException e) {
			log.error("sendMessageToUser IOException", e);
			return false;
		}
		return true;
	}

	/**
	 * 广播信息
	 * @param message
	 * @return
	 */
	public boolean sendMessageToAllUsers(TextMessage message) {
		boolean allSendSuccess = true;
		Set<String> clientIds = users.keySet();
		WebSocketSession session = null;
		for (String clientId : clientIds) {
			try {
				session = users.get(clientId);
				if (session.isOpen()) {
					session.sendMessage(message);
				}
			} catch (IOException e) {
				log.error("e", e);
				allSendSuccess = false;
			}
		}
		return allSendSuccess;
	}

	/**
	 * 获取用户标识
	 * @param session
	 * @return
	 */
	private String getClientId(WebSocketSession session) {
		try {
			String clientId = (String) session.getAttributes().get("WEBSOCKET_USERID");
			return clientId;
		} catch (Exception e) {
			log.error("e", e);
			return null;
		}
	}
	/**
	 * 获取在线人数
	 * @return
	 */
    public synchronized int getOnlineNum(){
        return users.size();
    }

}
