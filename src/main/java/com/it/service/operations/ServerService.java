package com.it.service.operations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.it.dao.operations.ServerMapper;
import com.it.po.Server;

@Service
public class ServerService {

	@Resource
	private ServerMapper mapper;

	public List<Server> select(Server po) {
		return mapper.select(po);
	}

	public int insert(Server po) {
		return mapper.insert(po);
	}

	public int updateByPrimaryKeySelective(Server po) {
		return mapper.updateByPrimaryKeySelective(po);
	}

	public Server selectById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	public int deleteById(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	public int batchDelete(String[] array) {
		return mapper.batchDelete(array);
	}
}
