package com.it.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.po.LoginInfo;
import com.it.service.LoginLogsService;
import com.it.util.JsonDateValueProcessor;
import com.it.util.JsonDefaultValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 
 * @ClassName: LoginLogsController  
 * @Description: TODO  登录日志
 * @author Administrator  
 * @date 2019年8月25日  
 *
 */
@Controller
@RequestMapping(value="loginLogs")
public class LoginLogsController {
	
	@Resource(name = "loginLogsService")
	private LoginLogsService service;
	
	/**
	 * 跳转到角色管理页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/loginLogsList")
	public ModelAndView list(Model model) {
		return new ModelAndView("logs/loginLogs/loginLogsList");
	}
	
	/**
	 * 操作查询，使用PageHelper分页插件分页查询
	 * @param request
	 * @param po
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/search")
	public JSONObject sysRoleSearch2(LoginInfo po, HttpServletRequest request){
		PageHelper.startPage(po.getPageNo(), po.getLimit());
		List<LoginInfo> list = service.search(po);
		PageInfo<LoginInfo> list1 = new PageInfo<LoginInfo>(list);

		//过滤掉对象 不转成JSONArray
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());//时间格式转换
		config.registerDefaultValueProcessor(Integer.class, new JsonDefaultValueProcessor());//数据格式转换
		JSONArray json = JSONArray.fromObject(list,config); 
		
		JSONObject jsonObject = new JSONObject();  //创建Json对象
		jsonObject.put("total", list1.getTotal());//总记录数
		jsonObject.put("rows", json);//json数据
		
		return jsonObject;
	}
}
