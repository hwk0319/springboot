package com.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.po.RoleInfo;
import com.it.po.RolePermission;

@Repository(value="systemRoleDao")
public interface SystemRoleDao {
	//查询角色列表
	public List<RoleInfo> sysRoleSearch(@Param("po") RoleInfo po);
	//增加角色
	public int addRole(@Param("po") RoleInfo po);
	//修改角色
	public int updateRole(@Param("po") RoleInfo po);
	//删除角色
	public int deleteRole(@Param("po") RoleInfo po);
	//删除角色权限关联信息
	public int deleteRolePermission(@Param("po") RoleInfo po);
	//增加角色权限关联表
	public int addRolePermission(@Param("po") RolePermission rp);
	//批量删除角色
	public int batchDeleteRole(@Param("array") String[] array);
	
}
