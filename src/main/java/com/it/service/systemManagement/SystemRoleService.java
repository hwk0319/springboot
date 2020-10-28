package com.it.service.systemManagement;

import java.util.List;

import javax.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.it.dao.systemManagement.SystemRoleDao;
import com.it.po.RoleInfo;
import com.it.po.RolePermission;

@Service(value = "systemRoleService")
public class SystemRoleService {
	@Resource(name = "systemRoleDao")
	private SystemRoleDao systemRoleDao;

	public List<RoleInfo> sysRoleSearch(RoleInfo po) {
		List<RoleInfo> list = systemRoleDao.sysRoleSearch(po);
		return list;
	}

	// 增加角色
	public int addRole(RoleInfo po) {
		int res = systemRoleDao.addRole(po);
		return res;
	}

	// 编辑角色
	public int updateRole(@Param("po") RoleInfo po) {
		int res = systemRoleDao.updateRole(po);
		return res;
	}

	// 删除角色
	public int deleteRole(@Param("po") RoleInfo po) {
		int res = systemRoleDao.deleteRole(po);
		return res;
	}

	public int addRolePermission(RolePermission rp) {
		return systemRoleDao.addRolePermission(rp);
	}

	public int deleteRolePermission(RoleInfo po) {
		return systemRoleDao.deleteRolePermission(po);
	}

	public int batchDeleteRole(String[] array) {
		return systemRoleDao.batchDeleteRole(array);
	}

}
