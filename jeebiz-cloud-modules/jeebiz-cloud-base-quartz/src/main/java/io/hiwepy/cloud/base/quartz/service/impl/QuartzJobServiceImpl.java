package io.hiwepy.cloud.base.quartz.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.base.quartz.dao.IQuartzJobDao;
import io.hiwepy.cloud.base.quartz.dao.entities.QuartzJobModel;
import io.hiwepy.cloud.base.quartz.service.IQuartzJobService;
import io.hiwepy.cloud.base.quartz.setup.JobStatus;
import io.hiwepy.cloud.base.quartz.setup.quartz.QuartzManager;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuartzJobServiceImpl extends BaseServiceImpl<IQuartzJobDao, QuartzJobModel> implements IQuartzJobService {

    @Autowired
    private QuartzManager quartzManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initJob() {
        // 	这里获取任务信息数据
        List<QuartzJobModel> jobList = getBaseMapper().getJobList();
        for (QuartzJobModel jobModel : jobList) {
            if (JobStatus.RUNNING.getCode().equals(jobModel.getStatus())) {
                getQuartzManager().addJob(jobModel);
            }
        }
    }

    @Override
    public QuartzJobModel getQuartzJobByBiz(String bizId) {
        return getBaseMapper().getJobByBizId(bizId);
    }

    /**
     * 添加任务
     *
     * @param job
     * @throws SchedulerException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addJob(QuartzJobModel job) {
        getQuartzManager().addJob(job);
        return getBaseMapper().insert(job);
    }

    /**
     * 暂停一个job
     *
     * @param job
     * @throws SchedulerException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int pauseJob(QuartzJobModel job) throws SchedulerException {
        getQuartzManager().pauseJob(job);
        // 任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败|6:删除）
        QuartzJobModel entity = new QuartzJobModel();
        entity.setStatus("2");
        entity.setId(job.getId());
        return getBaseMapper().updateById(entity);
    }

    /**
     * 恢复一个job
     *
     * @param job
     * @throws SchedulerException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resumeJob(QuartzJobModel job) throws SchedulerException {
        getQuartzManager().resumeJob(job);
        // 任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败|6:删除）
        QuartzJobModel entity = new QuartzJobModel();
        entity.setStatus("0");
        entity.setId(job.getId());
        return getBaseMapper().updateById(entity);
    }

    /**
     * 删除一个job
     *
     * @param job
     * @throws SchedulerException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteJob(QuartzJobModel job) throws SchedulerException {
        getQuartzManager().deleteJob(job);
        // 任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败|6:删除）
        QuartzJobModel entity = new QuartzJobModel();
        entity.setStatus("6");
        entity.setId(job.getId());
        return getBaseMapper().updateById(entity);
    }

    /**
     * 立即执行job
     *
     * @param job
     * @throws SchedulerException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int runJobNow(QuartzJobModel job) throws SchedulerException {
        getQuartzManager().runJobNow(job);
        return 1;
    }

    /**
     * 更新job时间表达式
     *
     * @param job
     * @throws SchedulerException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateJob(QuartzJobModel job) throws SchedulerException {
        getQuartzManager().updateJobCron(job);
        return getBaseMapper().updateById(job);
    }

    public QuartzManager getQuartzManager() {
        return quartzManager;
    }

}
