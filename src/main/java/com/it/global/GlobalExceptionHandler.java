package com.it.global;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.it.util.Util;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: GlobalExceptionHandler  
 * @Description: TODO  统一异常处理
 * @author Administrator  
 * @date 2020年10月13日
 * @ControllerAdvice注解的作用：是一个Controller增强器，可对controller中被@RequestMapping注解的方法加一些逻辑处理，最常用的就是异常处理；【三种使用场景】全局异常处理、全局数据绑定、全局数据预处理
 * @ExceptionHandler 异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public Object exception(HttpServletRequest request,
            HttpServletResponse response, Exception ex) {
		log.error(ex.getMessage());
		//判断是否是ajax请求
		if(Util.isAjaxRequest(request)) {
			JSONObject json = new JSONObject();
			json.put("code", "500");
			json.put("msg", "服务器异常！");
			Util.outObject(response, json.toString());
			return null;
		}else {
			//指定错误页面的模板页
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("500");
			modelAndView.addObject("code", "500");
			modelAndView.addObject("msg", "服务器异常！");
			return modelAndView;
		}
	}
	
	/**
	 * 
	 * @Title: handleAuthorizationException  
	 * @Description: TODO  处理未授权访问异常
	 * @param @param request
	 * @param @param response
	 * @param @param ex
	 * @param @return    参数  
	 * @return ModelAndView    返回类型  
	 * @throws
	 */
	@ExceptionHandler(AuthorizationException.class)
	public ModelAndView handleAuthorizationException(HttpServletRequest request,
            HttpServletResponse response, Exception ex) {
		return doException(request, response, ex);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ModelAndView handleShiroException(HttpServletRequest request,
            HttpServletResponse response, Exception ex) {
		return doException(request, response, ex);
	}
	
	
	/**
	 * 
	 * @Title: doException  
	 * @Description: TODO  处理异常（AuthorizationException，UnauthorizedException）
	 * @param @param request
	 * @param @param response
	 * @param @param ex
	 * @param @return    参数  
	 * @return ModelAndView    返回类型  
	 * @throws
	 */
	public ModelAndView doException(HttpServletRequest request,
            HttpServletResponse response, Exception ex) {
		log.error(ex.getMessage());
		//判断是否是ajax请求
		if(Util.isAjaxRequest(request)) {
			JSONObject json = new JSONObject();
			json.put("code", "403");
			json.put("msg", "您没有访问权限！");
			Util.outObject(response, json.toString());
			return null;
		}else {
			//指定错误页面的模板页
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("403");
			modelAndView.addObject("code", "403");
			modelAndView.addObject("msg", "您没有访问权限！");
			return modelAndView;
		}
	}
}
