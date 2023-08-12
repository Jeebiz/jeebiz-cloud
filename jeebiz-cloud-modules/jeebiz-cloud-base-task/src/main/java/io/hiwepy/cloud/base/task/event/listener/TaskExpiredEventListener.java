package io.hiwepy.cloud.base.task.event.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.hiwepy.cloud.base.task.TaskRedisKey;
import io.hiwepy.cloud.base.task.TaskStatusEnum;
import io.hiwepy.cloud.base.task.dto.TaskStatusDTO;
import io.hiwepy.cloud.base.task.entity.TaskInfoEntity;
import io.hiwepy.cloud.base.task.event.TaskExpiredEvent;
import io.hiwepy.cloud.base.task.service.ITaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskExpiredEventListener implements ApplicationListener<TaskExpiredEvent> {

    @Autowired
    private ITaskInfoService taskInfoService;
    @Autowired
    private RedisOperationTemplate redisOperation;

    @Override
    public void onApplicationEvent(TaskExpiredEvent event) {
        // 1、从事件对象获取任务状态信息
        TaskStatusDTO statusDTO = event.getBind();
        // 2、删除任务状态信息缓存
        redisOperation.del(TaskRedisKey.TASK_STATUS_KEY.getKey(statusDTO.getId()));
        // 3、查询任务是否存在
        Long taskCount = getTaskInfoService().count(new LambdaQueryWrapper<TaskInfoEntity>()
                .eq(TaskInfoEntity::getId, statusDTO.getId())
                .eq(TaskInfoEntity::getIsDelete, 0));
        if (taskCount == 0) {
            return;
        }
        // 4、更新任务状态
        TaskInfoEntity entity = new TaskInfoEntity().setId(statusDTO.getId()).setStatus(TaskStatusEnum.EXPIRED.getStatus());
        getTaskInfoService().updateById(entity);
    }

    public ITaskInfoService getTaskInfoService() {
        return taskInfoService;
    }

}