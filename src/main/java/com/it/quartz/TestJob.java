package com.it.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub

		 String printTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date());
	     System.out.println("PrintWordsJob start at:" + printTime + ", prints: Hello Job-" + new Random().nextInt(100));
	}

}
