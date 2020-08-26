package com.it.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 模板页面
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/template")
public class TemplateController {
	
	/**
	 * 
	 * @Title: loadPage  
	 * @Description: TODO  表单页
	 * @param @param model
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/form")
	public String formPage(HttpServletRequest req) {
		return "templatePage/form";
	}
	
}
