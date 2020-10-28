package com.it.websocket;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.Session;

import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.it.util.Util;

/**
 * 
 * @ClassName: PushAlarm  
 * @Description: TODO  消息推送类，服务启动后自动加载该类
 * @author Administrator  
 * @date 2020年10月19日  
 *
 */
@Component//被spring容器管理
public class PushAlarm{
	private static final Logger log = LoggerFactory.getLogger(PushAlarm.class);

	
	/**
	 * 定时执行任务 ，每10秒执行一次
	 * */
//	@Scheduled(cron = "*/10 * * * * *")
	public void push() {
		String message = "服务器发送的消息";
		
		Iterator<Entry<Session,String>> iterator = WebSocketServer.map.entrySet().iterator();
		WebSocketServer webSocketServer = new WebSocketServer();
		while (iterator.hasNext()) {
			Entry<Session,String> entry = iterator.next();
			Session session = entry.getKey();
			String userPower = entry.getValue();
			log.info("登录的账户："+userPower);
			log.info("推送内容："+message);
			webSocketServer.sendMessage(session,message);
		}
	}

}
