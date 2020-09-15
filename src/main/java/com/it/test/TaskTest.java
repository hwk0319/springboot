package com.it.test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: TaskTest  
 * @Description: TODO  定时任务测试类
 * @author Administrator  
 * @date 2020年9月14日  
 *
 */
@Component("TaskTest")
public class TaskTest {
	
	private static Logger logger = LoggerFactory.getLogger(TaskTest.class);
	
	public void test(String value) {
		logger.info("定时任务执行测试....");
		if(value != "" && value != null) {
			logger.info("参数为："+value);
		}
	}
}
