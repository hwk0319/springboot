package com.it.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class helloJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String printTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date());
		System.out.println("任务执行：时间："+printTime);
		
	}

}
