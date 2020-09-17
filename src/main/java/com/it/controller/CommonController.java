package com.it.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 通用访问方法
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/commom")
public class CommonController {
	
	/**
	 * 通用加载页面，不带参数
	 * @param path 跳转路径
	 * @return
	 */
	@RequestMapping(value="/loadPage")
	public ModelAndView loadPage(@RequestParam("path") String path, Model model) {
		return new ModelAndView(path);
	}
	
	/**
	 * 通用加载页面，带一个id参数
	 * @param path 跳转路径
	 * @param id 修改id
	 * @return
	 */
	@RequestMapping(value="/loadPageParam")
	public ModelAndView loadPageParam(@RequestParam("path") String path, @RequestParam("id") String id, Model model) {
		model.addAttribute("id", id);
		return new ModelAndView(path);
	}
	
	/**
	 * 
	 * @Title: errorPage  
	 * @Description: TODO  无权访问跳转403页面
	 * @param @param model
	 * @param @return    参数  
	 * @return ModelAndView    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/403")
	public ModelAndView errorPage(Model model) {
		return new ModelAndView("403");
	}
	
	/**
	 * 
	 * @Title: kickout  
	 * @Description: TODO  账号被踢出或在其它地方登陆跳转页面
	 * @param @param model
	 * @param @return    参数  
	 * @return ModelAndView    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/kickout")
	public ModelAndView kickout(Model model) {
		return new ModelAndView("kickout");
	}
	
}
