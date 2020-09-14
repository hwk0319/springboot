package com.it.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.it.dao.ScheduleJobLogDao;
import com.it.po.ScheduleJobLog;

@Service(value="scheduleJobLogService")
public class ScheduleJobLogService {
	
	@Resource(name="scheduleJobLogDao")
	private ScheduleJobLogDao dao;

	public List<ScheduleJobLog> selectList(ScheduleJobLog po) {
		return dao.selectList(po);
	}

	public int deleteAll() {
		return dao.deleteAll();
	}

}
