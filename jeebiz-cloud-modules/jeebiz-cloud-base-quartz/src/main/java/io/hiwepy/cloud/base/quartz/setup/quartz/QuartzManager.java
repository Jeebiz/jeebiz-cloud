/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.quartz.setup.quartz;

import io.hiwepy.boot.api.exception.BizRuntimeException;
import io.hiwepy.cloud.base.quartz.dao.entities.QuartzJobModel;
import org.quartz.*;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 计划任务管理器
 */
@Component
public class QuartzManager implements ApplicationContextAware {

    public final Logger log = LoggerFactory.getLogger(this.getClass());
    private ApplicationContext context;

    @Autowired
    private Scheduler scheduler;

    /**
     * 添加任务
     * @param scheduleJob
     * @throws SchedulerException
     */
    @SuppressWarnings("unchecked")
    public void addJob(QuartzJobModel job) {
        try {

            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类

            Class<? extends Job> jobClass = (Class<? extends Job>) ClassUtils.forName(job.getClazz(), getContext().getClassLoader());

            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getName(), job.getGroup())// 任务名称和组构成任务key
                    .usingJobData("bizId", job.getBizId())
                    .build();
            // 定义调度触发规则

            DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule().onEveryDay();
            CalendarIntervalScheduleBuilder.calendarIntervalSchedule();
            SimpleScheduleBuilder.repeatHourlyForTotalCount(1);

            // 使用cornTrigger规则
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), job.getGroup())// 触发器key
                    .startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron())).startNow().build();
            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new BizRuntimeException("任务添加失败：" + e.getMessage());
        }
    }

    /**
     * 添加任务
     * @param scheduleJob
     * @throws SchedulerException
     */
    @SuppressWarnings("unchecked")
    public void addJob(QuartzJobModel job, JobDataMap jobData) {
        try {

            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类

            Class<? extends Job> jobClass = (Class<? extends Job>) ClassUtils.forName(job.getClazz(), getContext().getClassLoader());

            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getName(), job.getGroup())// 任务名称和组构成任务key
                    .usingJobData(jobData)
                    .build();

            // 使用cornTrigger规则
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), job.getGroup())// 触发器key
                    .startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron())).startNow().build();
            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new BizRuntimeException("任务添加失败：" + e.getMessage());
        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @throws SchedulerException
     */
    public List<QuartzJobModel> gets() throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<QuartzJobModel> jobList = new ArrayList<QuartzJobModel>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                QuartzJobModel job = new QuartzJobModel();
                job.setName(jobKey.getName());
                job.setGroup(jobKey.getGroup());
                job.setIntro("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCron(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public List<QuartzJobModel> getRunningJobs() throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<QuartzJobModel> jobList = new ArrayList<QuartzJobModel>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            QuartzJobModel job = new QuartzJobModel();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setName(jobKey.getName());
            job.setGroup(jobKey.getGroup());
            job.setIntro("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCron(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 暂停一个job
     *
     * @param job
     * @throws SchedulerException
     */
    public void pauseJob(QuartzJobModel job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param job
     * @throws SchedulerException
     */
    public void resumeJob(QuartzJobModel job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     *
     * @param job
     * @throws SchedulerException
     */
    public void deleteJob(QuartzJobModel job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
        scheduler.deleteJob(jobKey);

    }

    /**
     * 立即执行job
     *
     * @param job
     * @throws SchedulerException
     */
    public void runJobNow(QuartzJobModel job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     *
     * @param job
     * @throws SchedulerException
     */
    public void updateJobCron(QuartzJobModel job) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        if (trigger != null) {


            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());

            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            scheduler.rescheduleJob(triggerKey, trigger);

        }


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public ApplicationContext getContext() {
        return context;
    }


}
