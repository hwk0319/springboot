package com.it.service.systemManagement;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.it.dao.systemManagement.NoticeMapper;
import com.it.po.Notice;

@Service
public class NoticeService {

	@Resource
	private NoticeMapper dao;

	public List<Notice> search(Notice po) {
		return dao.search(po);
	}

	public int insert(Notice po) {
		return dao.insert(po);
	}

	public int update(Notice po) {
		return dao.updateByPrimaryKeySelective(po);
	}

	public Notice selectById(Integer id) {
		return dao.selectByPrimaryKey(id);
	}

	public int deleteById(Integer id) {
		return dao.deleteByPrimaryKey(id);
	}

	public int batchDelete(String[] array) {
		return dao.batchDelete(array);
	}
	
}
