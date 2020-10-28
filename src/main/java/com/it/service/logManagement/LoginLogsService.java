package com.it.service.logManagement;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.it.dao.logManagement.LoginLogsDao;
import com.it.po.LoginInfo;
/**
 * 
 * @ClassName: LoginLogsService  
 * @Description: TODO  登录日志
 * @author Administrator  
 * @date 2019年8月25日  
 *
 */
@Service(value = "loginLogsService")
public class LoginLogsService {
	
	@Resource(name = "loginLogsDao")
	private LoginLogsDao dao;
	
	public List<LoginInfo> search(LoginInfo po){
		return dao.search(po);
	}
	
	public int addLoginInfo(LoginInfo loginInfo) {
		return dao.addLoginInfo(loginInfo);
	}
}
