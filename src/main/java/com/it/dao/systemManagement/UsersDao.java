package com.it.dao.systemManagement;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.po.Permission;
import com.it.po.UserInfo;
import com.it.po.UserRoleInfo;

@Repository(value="usersDao")
public interface UsersDao{
	//用户列表查询
	public List<UserInfo> search(@Param("po") UserInfo po);
	public UserInfo findUserByName(String name);
	public UserInfo findUserByStatus(String name);
	public UserInfo findUserById(int id);
	public List<String> getPermissions(@Param("menucode") String menucode);

	public int delete(@Param("po") UserInfo po);
	public int insert(@Param("po") UserInfo po);
	public int update(@Param("po") UserInfo po);
	public int updatePass(@Param("po") UserInfo po);
	public int updateStatus(String name);
	public int accredit(@Param("po") UserRoleInfo po);
	public int deleteUser(@Param("po") UserInfo po);
	//查询用户是否授权
	public List<UserRoleInfo> searchUserRoleInfo(@Param("po") UserRoleInfo po);
	//修改用户授权
	public int updateAccredit(@Param("po") UserRoleInfo po);
	//根据role查询权限
	public Set<Permission> findPermissionByRoleid(@Param("roleId") Integer roleId);
	public int bacthDeleteUser(@Param("array") String[] array);
}
