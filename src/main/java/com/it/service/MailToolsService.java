package com.it.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.it.dao.MailToolsMapper;
import com.it.po.MailTools;

@Service(value="mailToolsService")
public class MailToolsService {
	@Resource(name="mailToolsMapper")
	private MailToolsMapper mailToolsMapper;

	public int insert(MailTools po) {
		return mailToolsMapper.insert(po);
	}

	public int update(MailTools po) {
		return mailToolsMapper.updateByPrimaryKey(po);
	}

	public List<MailTools> search() {
		return mailToolsMapper.search();
	}
	
}
