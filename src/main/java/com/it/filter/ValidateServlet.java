package com.it.filter;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.it.util.ValidationcodeServlet;

/**
 * 将自定义servlet加入配置
 * @author Administrator
 *
 */
@Configuration
public class ValidateServlet {
	@Bean
    public ServletRegistrationBean<ValidationcodeServlet> servletRegistration0() {
        ServletRegistrationBean<ValidationcodeServlet> registration = new ServletRegistrationBean<ValidationcodeServlet>(new ValidationcodeServlet());
        registration.addUrlMappings("/validate.so");//验证码请求servlet
        registration.setLoadOnStartup(0);
        return registration;
    }
}
