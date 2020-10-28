package com.it.controller.userMenu;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.it.po.UserInfo;
import com.it.service.systemManagement.UserService;

/**
 * 
 * @ClassName: UserMenuController  
 * @Description: TODO  用户下拉菜单
 * @author Administrator  
 * @date 2020年10月21日  
 *
 */
@Controller
@RequestMapping("/userMenu")
public class UserMenuController {
	private static final Logger log = LoggerFactory.getLogger(UserMenuController.class);
	@Resource
	private UserService userService;
	
	@RequestMapping("/userCenter")
	public ModelAndView initPage(Model model) {
		UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
		user = userService.search(user).get(0);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("userMenu/userCenter");
		modelAndView.addObject("id", user.getId());
		modelAndView.addObject("name", user.getName());
		modelAndView.addObject("role", user.getRole());
		modelAndView.addObject("departmentName", user.getDepartmentName());
		modelAndView.addObject("phone", user.getPhone());
		modelAndView.addObject("email", user.getEmail());
		modelAndView.addObject("sex", user.getSex());
//		modelAndView.addObject("sex", user.getSex() == 0 ? "男" : "女");
		return modelAndView;
	}
}
