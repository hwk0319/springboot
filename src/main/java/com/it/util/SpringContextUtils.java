package com.it.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: SpringContextUtils  
 * @Description: TODO  Spring Context 工具类
 * @author Administrator  
 * @date 2019年11月8日  
 *
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

	public static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

	//通过name获取 Bean.
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	//通过name,以及Clazz返回指定的Bean
	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}

	public static Class<? extends Object> getType(String name) {
		return applicationContext.getType(name);
	}

}
