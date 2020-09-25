package com.it.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Administrator
 *
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer{
	private Logger logger = LoggerFactory.getLogger(WebConfig.class);
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("").setViewName("");
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new MyInterceptor());
        //添加需要拦截的路径和不需要拦截的路径
//        interceptorRegistration.excludePathPatterns("/error");
//        interceptorRegistration.excludePathPatterns("/login/login");
//        interceptorRegistration.excludePathPatterns("/login");
//        interceptorRegistration.excludePathPatterns("/css/**", "/images/**", "/imgs/**", "/jquery/**", "/js/**", "/layui/**");
//        interceptorRegistration.addPathPatterns("/**");
    }
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //需要配置1：需要告知系统，这是要被当成静态文件的！
        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
 
    private class MyInterceptor implements HandlerInterceptor {
    	/**
    	 * 在后端控制器执行前调用
    	 */
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            Object admin = request.getSession().getAttribute("user");
            if (admin == null) {
                logger.warn("当前用户未登录!");
                response.sendRedirect("/login");
                return false;
            }
            return true;
        }
 
        /**
         * 在后端控制器执行后调用
         */
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
            //controller方法处理完毕后，调用此方法
        }
 
        /**
         * 整个请求执行完成后调用
         */
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            //页面渲染完毕后调用此方法
        }
    }
}
