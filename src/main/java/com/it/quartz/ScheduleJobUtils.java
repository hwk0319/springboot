package com.it.quartz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.it.dao.ScheduleJobLogDao;
import com.it.po.ScheduleJob;
import com.it.po.ScheduleJobLog;
import com.it.util.Util;

public class ScheduleJobUtils extends QuartzJobBean{
	
	private static Logger log = Logger.getLogger(ScheduleJobUtils.class);

	private ExecutorService service = Executors.newSingleThreadExecutor();

	@Autowired
	private ScheduleJobLogDao dao;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(Constant.JOB_PARAM_KEY);

        //数据库保存执行记录
        ScheduleJobLog jobLog = new ScheduleJobLog();
        jobLog.setJobId(scheduleJob.getId());
        jobLog.setName(scheduleJob.getName());
        jobLog.setBeanName(scheduleJob.getBeanName());
        jobLog.setMethodName(scheduleJob.getMethodName());
        jobLog.setParams(scheduleJob.getParams());
        jobLog.setCreateDate(Util.getFormatDate());

        //任务开始时间
        long startTime = System.currentTimeMillis();
		int zero = 0;
		int one=1;
        try {
            //执行任务
        	log.info("任务准备执行，任务ID：" + scheduleJob.getId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);

			future.get();

			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			jobLog.setTimes((int)times);
			//任务状态    0：成功    1：失败
			jobLog.setStatus(zero);

			log.info("任务执行完毕，任务ID：" + scheduleJob.getId() + "  总共耗时：" + times + "毫秒");
		} catch (Exception e) {
			log.error("任务执行失败，任务ID：" + scheduleJob.getId(), e);

			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			jobLog.setTimes((int)times);

			//任务状态    0：成功    1：失败
			jobLog.setStatus(one);
			jobLog.setError(StringUtils.substring(e.toString(), 0, 2000));
		}finally {
			dao.insert(jobLog);
		}
	}
}
