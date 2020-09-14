package com.it.test;

import java.util.concurrent.TimeUnit;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import ch.qos.logback.core.util.TimeUtil;

public class testJob {

	public static void main(String[] args) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		System.out.println("scheduler name="+scheduler.getSchedulerName());
		
		JobDetail jobDetail = JobBuilder.newJob(helloJob.class).withIdentity("myjob", "jobgroup").build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("mytrigger", "triggergroup")
				.startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever()).build();
		
		
		scheduler.scheduleJob(jobDetail, trigger);
		System.out.println("scheduler start");
		scheduler.start();
		try {
			TimeUnit.MINUTES.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scheduler.shutdown();
		System.out.println("schedler shutdown");
	}
}
