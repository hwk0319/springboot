package com.it.service.taskManagement;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.dao.taskManagement.ScheduleJobDao;
import com.it.po.ScheduleJob;
import com.it.quartz.ScheduleUtils;

@Service(value="scheduleJobService")
public class ScheduleJobService {
	@Resource(name="scheduleJobDao")
	private ScheduleJobDao dao;
	
	@Autowired
    private Scheduler scheduler;
	
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		ScheduleJob scheduleJob = new ScheduleJob();
		List<ScheduleJob> scheduleJobList = dao.selectList(scheduleJob);
		for(ScheduleJob scheduleJob1 : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob1.getId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob1);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob1);
            }
		}
	}
	
	//查询集合
	public List<ScheduleJob> selectList(ScheduleJob po) {
		return dao.selectList(po);
	}
	
	//根据id查询
	public ScheduleJob selectById(int id) {
		return dao.selectById(id);
	}

	// 删除
	public int delete(int id) {
		int res = dao.deleteById(id);
		if(res > 0) {
			ScheduleUtils.deleteScheduleJob(scheduler, id);
		}
		return res;
	}

	// 新增
	public int insert(ScheduleJob po) {
		int res = dao.insert(po);
		if(res > 0) {
			ScheduleUtils.createScheduleJob(scheduler, po);
		}
		return res;
	}

	//更新
	public int update(ScheduleJob po) {
		int res = dao.updateById(po);
		ScheduleJob scheduleJob = dao.selectById(po.getId());
		if(res > 0) {
			ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
		}
		return res;
	}
	
	//暂停
	public int pause(ScheduleJob po) {
		int res = dao.updateStatusById(po);
		if(res > 0) {
			ScheduleUtils.pauseJob(scheduler, po.getId());
		}
		return res;
	}

	//执行
	public int execute(ScheduleJob po) {
		int res = dao.updateStatusById(po);
		ScheduleJob scheduleJob = dao.selectById(po.getId());
		if(scheduleJob != null) {
			ScheduleUtils.run(scheduler, scheduleJob);
		}
		return res;
	}

	public int bacthDelete(String[] array) {
		return dao.bacthDelete(array);
	}

}
