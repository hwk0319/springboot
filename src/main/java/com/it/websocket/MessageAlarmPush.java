package com.it.websocket;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

/**
 * 
 * @ClassName: MessageAlarmPush  
 * @Description: TODO  消息推送测试
 * @author Administrator  
 * @date 2020年9月22日  
 *
 */
@Service
public class MessageAlarmPush {
	private Logger log = LoggerFactory.getLogger(MessageAlarmPush.class);

//	@Autowired
//	private MyWebSocketHandler handler;

//	@PostConstruct
//	public void consumeAlarmData() {
//		log.info("start websocket alarm thread...");
//		Consume consume = new Consume();
//		consume.start();
//	}
	
	
	public class Consume extends Thread {
		@Override
		public void run() {
			/*while(true) {
				String data = "欢迎登陆";
				boolean isSuccess = handler.sendMessageToAllUsers(new TextMessage(data));
				log.info("websocket-alarm-data: " + data + ",result: " + isSuccess);
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
		}
	}
}
