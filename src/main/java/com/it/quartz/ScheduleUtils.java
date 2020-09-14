package com.it.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.it.po.ScheduleJob;
import com.it.util.GenericException;

/**
 * 
 * @ClassName: ScheduleUtils  
 * @Description: TODO  定时任务工具类
 * @author Administrator  
 * @date 2019年11月7日  
 *
 */
public class ScheduleUtils {

	private final static String JOB_NAME = "TASK_";

	/**
	 * 
	 * @Title: getTriggerKey  
	 * @Description: TODO  获取触发器key
	 * @param @param jobId
	 * @param @return    参数  
	 * @return TriggerKey    返回类型  
	 * @throws
	 */
    public static TriggerKey getTriggerKey(Integer jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }

    /**
     * 
     * @Title: getJobKey  
     * @Description: TODO  获取jobKey
     * @param @param jobId
     * @param @return    参数  
     * @return JobKey    返回类型  
     * @throws
     */
    public static JobKey getJobKey(Integer jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 
     * @Title: getCronTrigger  
     * @Description: TODO  获取表达式触发器
     * @param @param scheduler
     * @param @param jobId
     * @param @return    参数  
     * @return CronTrigger    返回类型  
     * @throws
     */

    public static CronTrigger getCronTrigger(Scheduler scheduler, Integer jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new GenericException("获取定时任务CronTrigger出现异常", e);
        }
    }

    /**
     * 
     * @Title: createScheduleJob  
     * @Description: TODO  创建定时任务
     * @param @param scheduler
     * @param @param scheduleJob    参数  
     * @return void    返回类型  
     * @throws
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
        try {
        	//构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJobUtils.class).withIdentity(getJobKey(scheduleJob.getId())).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getId())).withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(Constant.JOB_PARAM_KEY, scheduleJob);

            scheduler.scheduleJob(jobDetail, trigger);

            //暂停任务
            if(scheduleJob.getStatus() == Constant.PAUSE){
            	pauseJob(scheduler, scheduleJob.getId());
            }
        } catch (SchedulerException e) {
            throw new GenericException("创建定时任务失败", e);
        }
    }

    /**
     * 
     * @Title: updateScheduleJob  
     * @Description: TODO  更新定时任务
     * @param @param scheduler
     * @param @param scheduleJob    参数  
     * @return void    返回类型  
     * @throws
     */
    public static void updateScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getId());

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getId());

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            //参数
            trigger.getJobDataMap().put(Constant.JOB_PARAM_KEY, scheduleJob);

            scheduler.rescheduleJob(triggerKey, trigger);

            //暂停任务
            if(scheduleJob.getStatus() == Constant.PAUSE){
            	pauseJob(scheduler, scheduleJob.getId());
            }

        } catch (SchedulerException e) {
            throw new GenericException("更新定时任务失败", e);
        }
    }

    /**
     * 
     * @Title: run  
     * @Description: TODO  立即执行任务
     * @param @param scheduler
     * @param @param scheduleJob    参数  
     * @return void    返回类型  
     * @throws
     */
    public static void run(Scheduler scheduler, ScheduleJob scheduleJob) {
        try {
        	//参数
        	JobDataMap dataMap = new JobDataMap();
        	dataMap.put(Constant.JOB_PARAM_KEY, scheduleJob);

            scheduler.triggerJob(getJobKey(scheduleJob.getId()), dataMap);
        } catch (SchedulerException e) {
            throw new GenericException("立即执行定时任务失败", e);
        }
    }

    /**
     * 
     * @Title: pauseJob  
     * @Description: TODO  暂停任务
     * @param @param scheduler
     * @param @param integer    参数  
     * @return void    返回类型  
     * @throws
     */
    public static void pauseJob(Scheduler scheduler, Integer integer) {
        try {
            scheduler.pauseJob(getJobKey(integer));
        } catch (SchedulerException e) {
            throw new GenericException("暂停定时任务失败", e);
        }
    }

    /**
     * 
     * @Title: resumeJob  
     * @Description: TODO  恢复任务
     * @param @param scheduler
     * @param @param jobId    参数  
     * @return void    返回类型  
     * @throws
     */
    public static void resumeJob(Scheduler scheduler, Integer jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new GenericException("恢复定时任务失败", e);
        }
    }

    /**
     * 
     * @Title: deleteScheduleJob  
     * @Description: TODO  删除定时任务
     * @param @param scheduler
     * @param @param jobId    参数  
     * @return void    返回类型  
     * @throws
     */
    public static void deleteScheduleJob(Scheduler scheduler, Integer jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new GenericException("删除定时任务失败", e);
        }
    }
}
