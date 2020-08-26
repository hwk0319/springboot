package com.it.filter;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 添加@Configurationz 注解，将自定义Filter加入过滤链
 * @author Administrator
 *
 */
@Configuration
public class WebConfiguration {
	@Bean
	public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }
	
	@Bean
	public FilterRegistrationBean<MyFilter> testFilterRegistration() {
        FilterRegistrationBean<MyFilter> registration = new FilterRegistrationBean<MyFilter>();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }
}
