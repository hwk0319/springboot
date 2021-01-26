package com.it.service.systemManagement;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.it.dao.systemManagement.UsersDao;
import com.it.po.Permission;
import com.it.po.UserInfo;
import com.it.po.UserRoleInfo;

@Service(value="userService")
public class UserService {
	@Resource(name="usersDao")
	private UsersDao dao;
	
	public List<UserInfo> search(@Param("po") UserInfo po) {
		return dao.search(po);
	}

	// 注销用户
	public int delete(@Param("po") UserInfo po) {
		int res = dao.delete(po);
		return res;
	}

	// 新增用户
	public int insert(@Param("po") UserInfo user) {
		int res = dao.insert(user);
		return res;
	}

	public int update(@Param("po") UserInfo user) {
		int res = dao.update(user);
		return res;
	}

	// 角色授权
	public int accredit(@Param("po") UserRoleInfo user) {
		int res = dao.accredit(user);
		return res;
	}

	// 修改密码
	public int updatePass(@Param("po") UserInfo user) {
		int res = dao.updatePass(user);
		return res;
	}

	public int deleteUser(UserInfo po) {
		return dao.deleteUser(po);
	}
	
	//根据名称查询
	public UserInfo findUserByName(String username) {
		return dao.findUserByName(username);
	}

	public List<UserRoleInfo> searchUserRoleInfo(UserRoleInfo po) {
		return dao.searchUserRoleInfo(po);
	}

	public int updateAccredit(UserRoleInfo po) {
		return dao.updateAccredit(po);
	}

	public Set<Permission> findPermissionByRoleid(Integer roleId) {
		return dao.findPermissionByRoleid(roleId);
	}

	public int bacthDeleteUser(String[] array) {
		return dao.bacthDeleteUser(array);
	}
	
	public UserInfo findUserById(int id) {
		return dao.findUserById(id);
	}

}
