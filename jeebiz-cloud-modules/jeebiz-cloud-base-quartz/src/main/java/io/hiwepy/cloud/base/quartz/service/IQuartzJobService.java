package io.hiwepy.cloud.base.quartz.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.base.quartz.dao.entities.QuartzJobModel;
import org.quartz.SchedulerException;

public interface IQuartzJobService extends IBaseService<QuartzJobModel> {

    void initJob();

    QuartzJobModel getQuartzJobByBiz(String bizId);

    int addJob(QuartzJobModel task);

    int pauseJob(QuartzJobModel task) throws SchedulerException;

    int resumeJob(QuartzJobModel task) throws SchedulerException;

    int deleteJob(QuartzJobModel task) throws SchedulerException;

    int runJobNow(QuartzJobModel task) throws SchedulerException;

    int updateJob(QuartzJobModel task) throws SchedulerException;

}
