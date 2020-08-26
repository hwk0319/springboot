package com.it.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="icons")
public class IconsController {

	/**
	 * 
	 * @Title: Fontawesome  
	 * @Description: TODO  
	 * @param @param req
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/fontawesome")
	public String Fontawesome(HttpServletRequest req) {
		return "icons/icon-font-awesome";
	}
	
	/**
	 * 
	 * @Title: SimpleLineIcon  
	 * @Description: TODO  
	 * @param @param req
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/simpleLineIcon")
	public String SimpleLineIcon(HttpServletRequest req) {
		return "icons/icon-simple-lineicon";
	}
}
