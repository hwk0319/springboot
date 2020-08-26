package com.it.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.aspect.OperLogs;
import com.it.po.MailTools;
import com.it.po.UserInfo;
import com.it.po.UserRoleInfo;
import com.it.service.MailToolsService;

@Controller
@RequestMapping(value="/mail")
public class MailController {
	private Logger logger = LoggerFactory.getLogger(MailController.class);
	
	@Resource(name="mailToolsService")
	private MailToolsService service;
	
	@RequestMapping(value="list")
	public String mailList() {
		return "systemTools/mail/mail";
	}
	
	/**
	 * 查询邮件工具配置
	 */
	@RequestMapping(value="/search")
	@ResponseBody
	public List<MailTools> search(MailTools po,HttpServletRequest request){
		List<MailTools> list = service.search();
		return list;
	}
	
	/**
	 * 
	 * @Title: insert  
	 * @Description: TODO  新增邮件工具配置
	 * @param @param po
	 * @param @param request
	 * @param @return    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/insert")
	@RequiresPermissions("mail:insert")
	@OperLogs(value = "邮件工具配置")
	@ResponseBody
	public int insert(MailTools po,HttpServletRequest request){		
		int res = 0;
		
		try {
			List<MailTools> list = service.search();
			if(list.size() > 0 && list != null) {
				res = service.update(po);
			}else {
				res = service.insert(po);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return res;
	}
	
	/**
	 * 
	 * @Title: update  
	 * @Description: TODO  修改邮件工具配置
	 * @param @param po
	 * @param @param request
	 * @param @return    参数  
	 * @return int    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/update")
	@RequiresPermissions("mail:update")
	@OperLogs(value="修改邮件工具配置")
	@ResponseBody
	public int update(MailTools po,HttpServletRequest request) {
		int res = 0;
		try {
			res = service.update(po);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return res;
	}
}
