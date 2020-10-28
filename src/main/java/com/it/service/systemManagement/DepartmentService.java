package com.it.service.systemManagement;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.it.dao.systemManagement.DepartmentDao;
import com.it.po.Department;

@Service(value="departmentService")
public class DepartmentService {
	@Resource(name="departmentDao")
	private DepartmentDao dao;
	
	public List<Department> search(Department po) {
		return dao.search(po);
	}
	
	public Department findById(int id) {
		return dao.findById(id);
	}

	public int delete(Department po) {
		int res = dao.delete(po);
		return res;
	}

	public int insert(Department po) {
		int res = dao.insert(po);
		return res;
	}

	public int update(Department po) {
		int res = dao.update(po);
		return res;
	}

	public Department findParentById(int id) {
		return dao.findParentById(id);
	}


}
