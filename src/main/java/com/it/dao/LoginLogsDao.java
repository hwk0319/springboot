package com.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.po.LoginInfo;

/**
 * 
 * @ClassName: LoginLogsDao  
 * @Description: TODO  登录日志
 * @author Administrator  
 * @date 2019年8月25日  
 *
 */
@Repository(value="loginLogsDao")
public interface LoginLogsDao {
	//查询操作日志
	public List<LoginInfo> search(@Param("po") LoginInfo po);
	//添加登录日志
	public int addLoginInfo(@Param("po") LoginInfo loginInfo);
}
