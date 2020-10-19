package com.it.service.operations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.it.dao.operations.DatabaseMapper;
import com.it.po.Database;

@Service
public class DatabaseService {

	@Resource
	private DatabaseMapper mapper;

	public List<Database> select(Database po) {
		return mapper.select(po);
	}

	public int insert(Database po) {
		return mapper.insert(po);
	}

	public int updateByPrimaryKeySelective(Database po) {
		return mapper.updateByPrimaryKeySelective(po);
	}

	public Database selectById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	public int deleteById(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	public int batchDelete(String[] array) {
		return mapper.batchDelete(array);
	}
}
