package com.it.quartz;

import java.util.concurrent.TimeUnit;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MyScheduler {

	public static void main(String[] args) throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler s = sf.getScheduler();
		
		JobDetail j = JobBuilder.newJob(TestJob.class).withIdentity("job1", "group1").build();
		Trigger t = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
				.startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule()
		                .withIntervalInSeconds(1)//每隔1s执行一次
		                .repeatForever()).build();
		
		//执行
		s.scheduleJob(j, t);
		System.out.println("--------scheduler start ! ------------");
		s.start();
		
		//睡眠
        TimeUnit.MINUTES.sleep(1);
        s.shutdown();
        System.out.println("--------scheduler shutdown ! ------------");
	}
}
