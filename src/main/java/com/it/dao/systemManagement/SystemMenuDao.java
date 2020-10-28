package com.it.dao.systemManagement;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.po.Permission;
import com.it.po.RolePermission;
import com.it.po.UserInfo;

@Repository(value = "systemMenuDao")
public interface SystemMenuDao {
	// 根据父级菜单id查询 子菜单
	public List<Permission> findMenuByid(@Param("id") int id);
	
//	public List<Tree> getMenu(String id);
//
//	public List<Tree> getMenuList();
//
//	public List<Tree> getChildernList(@Param("id") String id);

	//增加菜单
	public int addMenu(@Param("po") Permission po);
	//编辑菜单
	public int updateMenu(@Param("po") Permission po);
	//删除菜单
	public int deleteMenu(@Param("po") Permission po);
	//查询所有菜单
	public List<Permission> search(@Param("po") Permission po);
	//根据用户查询菜单
	public List<Permission> findMenuByUser(@Param("user") UserInfo user);
	//根据角色id查询角色权限关联信息
	public List<RolePermission> searchRolePermission(@Param("po") RolePermission rp);
	//根据id查询菜单
	public List<Permission> searchMenuById(@Param("po") Permission po);
	//查询菜单树
	public List<Permission> searchMenuTree(@Param("po") Permission po);

	public List<Permission> searchMenuTree1(@Param("po") Permission po);
}
