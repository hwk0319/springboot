package com.it.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.it.dao.SystemMenuDao;
import com.it.po.Permission;
import com.it.po.RolePermission;
import com.it.po.Tree;
import com.it.po.UserInfo;

@Service
public class SystemMenuService {
	@Resource
	private SystemMenuDao menudao;

	public List<Permission> findMenuByid(int id) {
		List<Permission> list = menudao.findMenuByid(id);
		return list;

	}

	public List<Permission> search(Permission po) {
		List<Permission> list = menudao.search(po);
		return list;
	};

	public List<Tree> getMenuList() {
		return menudao.getMenuList();
	}
	
	public List<Tree> getChildernList(String id) {
		return menudao.getChildernList(id);
	}
	
	public int addMenu(Permission po) {
		return menudao.addMenu(po);
	}

	public int updateMenu(Permission po) {
		return menudao.updateMenu(po);
	}

	public int deleteMenu(Permission po) {
		return menudao.deleteMenu(po);
	}

	public List<Permission> findMenuByUser(UserInfo user) {
		return menudao.findMenuByUser(user);
	}

	public List<RolePermission> searchRolePermission(RolePermission rp) {
		return menudao.searchRolePermission(rp);
	}

	public List<Permission> searchMenuById(Permission po) {
		return menudao.searchMenuById(po);
	}

	public List<Permission> searchMenuTree(Permission po) {
		return menudao.searchMenuTree(po);
	}

	public List<Permission> searchMenuTree1(Permission po) {
		return menudao.searchMenuTree1(po);
	};
}
