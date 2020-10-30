package com.it.controller.userMenu;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.it.po.UserInfo;
import com.it.service.systemManagement.UserService;
import com.it.util.Result;

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
	//静态资源访问位置
	@Value("${file.staticAccessPath}")
	private String staticAccessPath;
	//图像上传位置
	@Value("${file.uploadUserImagePath}")
	private String uploadUserImagePath;
	
	
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
		modelAndView.addObject("image", user.getImage());
		return modelAndView;
	}
	
	/**
	 * 
	 * @Title: uploadImage  
	 * @Description: TODO  用户上传图像
	 * @param @param file
	 * @param @return    参数  
	 * @return Result    返回类型  
	 * @throws
	 */
	@RequestMapping("/uploadImage")
	@ResponseBody
	public Result uploadImage(@RequestParam("file_data") MultipartFile file) {
		if(file.isEmpty()) {
			log.error("上传文件不存在！");
			return Result.error();
		}
		UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
		String fileName = file.getOriginalFilename();
		String newFileName = user.getName() + fileName.substring(fileName.indexOf("."));
		
		File f = new File(uploadUserImagePath + newFileName);
		if(!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		try {
			file.transferTo(f);
		} catch (IllegalStateException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		//更新数据库用户头像
		UserInfo newUser = new UserInfo();
		newUser.setId(user.getId());
		newUser.setImage("/static/uploadfile/userImage/"+newFileName);
		userService.update(newUser);
		return Result.success();
	}
}
