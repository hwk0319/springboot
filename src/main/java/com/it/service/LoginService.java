package com.it.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.it.dao.UsersDao;
import com.it.po.UserInfo;



/**
 * 
 * @ClassName: LoginService  
 * @Description: TODO  用户登录service
 * @author Administrator  
 * @date 2019年7月11日  
 *
 */
@Service(value="loginService")
public class LoginService {
	
	@Resource
	private UsersDao dao;
	
	/**
	 * 
	 * @Title: login  
	 * @Description: TODO  用户登录
	 * @param @param name
	 * @param @param pwd
	 * @param @param request
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	/*public String login(String name, String pwd, HttpServletRequest request) {
		//rsa解密用户名
		String userName = RSAUtils.decryptStringByJs(name);
		//根据用户名查找是否存在此用户
		UserInfo userInfo = dao.findUserByName(userName);
		if (userInfo != null) {
			//判断用户是否被注销
			UserInfo userInfo1 = dao.findUserByStatus(userName);
			if(userInfo1 != null){
				return "用户被注销";
			}
			//解密传输密码
			String passW = RSAUtils.decryptStringByJs(pwd);
			//数据库密码解密
			String dbPass = RSAUtils.decryptStringByJs(userInfo.getPassword());
			
			if (passW.equals(dbPass)) {
					request.getSession().setAttribute("user", userInfo);
					//返回是用户id、用户名
					return userInfo.getId()+","+userInfo.getName();
			} else {
				return "密码错误";
			}
		} else {
			return "用户不存在";
		}
	}*/

	/**
	 * 
	 * @Title: logout  
	 * @Description: TODO  用户注销
	 * @param @param request
	 * @param @return
	 * @param @throws Exception    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	public String logout(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
//		User user =  (User) session.getAttribute("user");
		session.invalidate();
		return "success";
	}

}

