/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.quartz.setup.quartz;


import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@DisallowConcurrentExecution //作业不并发
@Component
public class QuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {

        // System.out.println("欢迎使用yyblog,这是一个定时任务  --小卖铺的老爷爷!"+ DateUtils.fullTime(new Date()));


    }

}
