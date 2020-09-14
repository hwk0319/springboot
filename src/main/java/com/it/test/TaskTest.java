package com.it.test;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.it.po.UserInfo;
import com.it.service.UserService;
@Component("TaskTest")
public class TaskTest {
	
	private static Logger logger = LoggerFactory.getLogger(TaskTest.class);
	
	@Resource(name="userService")
	private UserService service;

	public void test(String value) {
		logger.info("定时任务测试....");
		if(value != "" && value != null) {
			logger.info("参数为："+value);
		}
		service.search(new UserInfo());
	}
}
