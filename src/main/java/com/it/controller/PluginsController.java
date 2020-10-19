package com.it.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @ClassName: PluginsController  
 * @Description: TODO  组件管理
 * @author Administrator  
 * @date 2020年9月27日  
 *
 */
@Controller
@RequestMapping(value="plugin")
public class PluginsController {

	/**
	 * 
	 * @Title: icon  
	 * @Description: TODO  
	 * @param @param req
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/icon")
	public String icon(HttpServletRequest req) {
		return "plugins/icons";
	}
	
	/**
	 * 
	 * @Title: editor  
	 * @Description: TODO  
	 * @param @param req
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/editor")
	public String editor(HttpServletRequest req) {
		return "plugins/editor";
	}
	
	/**
	 * 
	 * @Title: chart  
	 * @Description: TODO  
	 * @param @param req
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/chart")
	public String chart(HttpServletRequest req) {
		return "plugins/chart";
	}
	
	/**
	 * 
	 * @Title: ymal  
	 * @Description: TODO  
	 * @param @param req
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/yaml")
	public String yaml(HttpServletRequest req) {
		return "plugins/yaml";
	}
}
