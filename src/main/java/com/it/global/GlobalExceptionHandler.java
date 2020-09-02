package com.it.global;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @ControllerAdvice注解的作用：是一个Controller增强器，可对controller中被@RequestMapping注解的方法加一些逻辑处理，最常用的就是异常处理；【三种使用场景】全局异常处理。全局数据绑定，全局数据预处理
 * @Order 注解@Order或者接口Ordered的作用是定义SpringIOC容器中Bean的执行顺序的优先级，而不是定义Bean的加载顺序，Bean的加载顺序不受@Order或Ordered接口的影响；
 * @ExceptionHandler 统一异常处理
 * 
 */
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
	@ExceptionHandler(value = AuthorizationException.class)
	public String handleAuthorizationException() {
		return "403";
	}
}
