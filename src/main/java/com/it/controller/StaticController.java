package com.it.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @ClassName: StaticController  
 * @Description: TODO  跳转静态页面
 * @author Administrator  
 * @date 2019年10月18日  
 *
 */
@Controller
@RequestMapping(value="static")
public class StaticController {

	@RequestMapping(value="/dashboard")
	public String Fontawesome(HttpServletRequest req) {
		return "dashboard";
	}
}
