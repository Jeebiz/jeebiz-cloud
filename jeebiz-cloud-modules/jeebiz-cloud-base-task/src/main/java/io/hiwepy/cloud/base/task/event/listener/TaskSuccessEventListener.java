package io.hiwepy.cloud.base.task.event.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.hiwepy.cloud.base.task.TaskProperties;
import io.hiwepy.cloud.base.task.TaskRedisKey;
import io.hiwepy.cloud.base.task.TaskStatusEnum;
import io.hiwepy.cloud.base.task.dto.TaskStatusDTO;
import io.hiwepy.cloud.base.task.entity.TaskInfoEntity;
import io.hiwepy.cloud.base.task.event.TaskSuccessEvent;
import io.hiwepy.cloud.base.task.service.ITaskInfoService;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskSuccessEventListener implements ApplicationListener<TaskSuccessEvent> {

    @Autowired
    private ITaskInfoService taskInfoService;
    @Autowired
    private RedisOperationTemplate redisOperation;
    @Autowired
    private TaskProperties taskProperties;

    @Override
    public void onApplicationEvent(TaskSuccessEvent event) {
        // 1、从事件对象获取任务状态信息
        TaskStatusDTO statusDTO = event.getBind();
        statusDTO.setStatus(TaskStatusEnum.SUCCESS.getStatus());
        // 2、更新任务状态信息缓存
        String rdsKey = TaskRedisKey.TASK_STATUS_KEY.getKey(statusDTO.getId());
        BeanMap statusMap = new BeanMap(statusDTO);
        redisOperation.hmSet(rdsKey, statusMap, taskProperties.getTaskStatusExpireTime());
        // 3、查询任务是否存在
        Long taskCount = getTaskInfoService().count(new LambdaQueryWrapper<TaskInfoEntity>()
                .eq(TaskInfoEntity::getId, statusDTO.getId())
                .eq(TaskInfoEntity::getIsDelete, 0));
        if (taskCount == 0) {
            return;
        }
        // 4、更新任务状态
        TaskInfoEntity entity = new TaskInfoEntity().setId(statusDTO.getId()).setStatus(TaskStatusEnum.SUCCESS.getStatus());
        getTaskInfoService().updateById(entity);
    }

    public ITaskInfoService getTaskInfoService() {
        return taskInfoService;
    }

}