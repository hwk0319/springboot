package com.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.it.po.Department;

@Repository(value="departmentDao")
public interface DepartmentDao{
	//用户列表查询
	public List<Department> search(@Param("po") Department po);
	public Department findById(@Param("id") int id);
	public int delete(@Param("po") Department po);
	public int insert(@Param("po") Department po);
	public int update(@Param("po") Department po);
	public Department findParentById(@Param("id") int id);
}
