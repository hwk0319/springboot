package com.it.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.aspect.OperLogs;
import com.it.po.MailTools;
import com.it.po.UserInfo;
import com.it.po.UserRoleInfo;
import com.it.service.MailToolsService;
import com.it.util.SendEmailUtil;

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
	
	/**
	 * 
	 * @Title: sendEmail  
	 * @Description: TODO  发送邮件
	 * @param @param emailTitle 邮件标题
	 * @param @param inbox 收件人邮箱
	 * @param @param emailContent 邮件内容
	 * @param @return    参数  
	 * @return boolean    返回类型  
	 * @throws
	 */
	@RequestMapping(value="/sendEmail")
	@ResponseBody
	@OperLogs(value="发送邮件")
	public boolean sendEmail(@RequestParam(name="emailTitle") String emailTitle, 
			@RequestParam(name="inbox") String inbox, @RequestParam(name="emailContent") String emailContent) {
		
		List<MailTools> list = service.search();
		if(list.size() > 0 && list != null) {
			String username = list.get(0).getSenderUsername();
			String password = list.get(0).getEmailPassword();
			String senderEmail = list.get(0).getSenderEmail();
			String smtpAddress = list.get(0).getSmtpAddress();
			int smtpPort = list.get(0).getSmtpPort();
			
			try {
				SendEmailUtil.sendEmail(username, password, senderEmail, inbox, smtpAddress, smtpPort, emailTitle, emailContent);
			} catch (AddressException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				return false;
			} catch (MessagingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
