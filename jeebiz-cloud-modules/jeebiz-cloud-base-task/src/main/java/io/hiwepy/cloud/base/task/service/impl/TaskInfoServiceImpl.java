package io.hiwepy.cloud.base.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.sequence.Sequence;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.base.task.TaskInfoProvider;
import io.hiwepy.cloud.base.task.TaskProperties;
import io.hiwepy.cloud.base.task.TaskRedisKey;
import io.hiwepy.cloud.base.task.TaskStatusEnum;
import io.hiwepy.cloud.base.task.dto.TaskCreateDTO;
import io.hiwepy.cloud.base.task.dto.TaskInfoDTO;
import io.hiwepy.cloud.base.task.dto.TaskPaginationDTO;
import io.hiwepy.cloud.base.task.entity.TaskInfoEntity;
import io.hiwepy.cloud.base.task.event.TaskCancelEvent;
import io.hiwepy.cloud.base.task.event.TaskCleanEvent;
import io.hiwepy.cloud.base.task.event.TaskCreateEvent;
import io.hiwepy.cloud.base.task.event.TaskDeleteEvent;
import io.hiwepy.cloud.base.task.mapper.TaskInfoMapper;
import io.hiwepy.cloud.base.task.param.TaskExpiredCleanParam;
import io.hiwepy.cloud.base.task.param.TaskQueryParam;
import io.hiwepy.cloud.base.task.service.ITaskInfoService;
import io.hiwepy.cloud.base.task.vo.TaskInfoVO;
import io.hiwepy.cloud.base.task.vo.TaskResourceVO;
import io.hiwepy.cloud.base.task.vo.TaskStatusVO;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 任务信息表 服务实现类
 * </p>
 *
 * @author wandl
 * @since 2023-07-18
 */
@Service
public class TaskInfoServiceImpl extends BaseServiceImpl<TaskInfoMapper, TaskInfoEntity> implements ITaskInfoService {

    private static final TaskStatusVO NOT_STARTED_TASK_STATUS = new TaskStatusVO().setStatus(TaskStatusEnum.NOT_STARTED.getStatus());
    private static final String STATUS_FIELD = "status";
    private static final String PATH_DELIMITER = "/";
    @Autowired
    private Sequence sequence;
    @Autowired
    private RedisOperationTemplate redisOperation;
    @Autowired
    private TaskInfoProvider taskInfoProvider;
    @Autowired
    private TaskProperties taskProperties;

    @Override
    public TaskStatusVO createTask(TaskCreateDTO createDTO) {
        // 1、创建任务
        TaskInfoEntity entity = getBeanMapper().map(createDTO, TaskInfoEntity.class);
        entity.setId(sequence.nextId());
        entity.setName(taskInfoProvider.getTaskName(entity, createDTO.getParams()));
        entity.setDesc(taskInfoProvider.getTaskDesc(entity, createDTO.getParams()));
        entity.setBizData(Objects.nonNull(createDTO.getParams()) ? createDTO.getParams().toJSONString() : null);
        entity.setStatus(TaskStatusEnum.NOT_STARTED.getStatus());
        boolean flag = save(entity);
        // 2、如果创建成功，创建任务状态信息
        if (flag) {
            TaskStatusVO statusVO = getBeanMapper().map(entity, TaskStatusVO.class);
            // 2.1、设置任务状态信息缓存
            String rdsKey = TaskRedisKey.TASK_STATUS_KEY.getKey(statusVO.getId());
            BeanMap statusMap = new BeanMap(statusVO);
            redisOperation.hmSet(rdsKey, statusMap, taskProperties.getTaskStatusExpireTime());
            // 2.2、发布任务创建事件
            TaskInfoDTO infoDTO = getBeanMapper().map(entity, TaskInfoDTO.class);
            getEventPublisher().publishEvent(new TaskCreateEvent(this, infoDTO));
            // 2.3、返回任务状态信息
            return getBeanMapper().map(entity, TaskStatusVO.class);
        }
        return null;
    }

    @Override
    public List<TaskInfoVO> getTaskList(TaskQueryParam param) {
        // 1、查询任务列表
        List<TaskInfoEntity> entityList = getBaseMapper().selectList(new LambdaQueryWrapper<TaskInfoEntity>()
                .eq(TaskInfoEntity::getSchoolCode, param.getSchoolCode())
                .eq(TaskInfoEntity::getUserId, param.getUserId())
                .eq(TaskInfoEntity::getBizType, param.getBizType())
                .eq(TaskInfoEntity::getStatus, param.getStatus()));
        // 2、如果为空，返回空列表
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        // 3、转换为VO
        return entityList.stream().map(entity -> {
            TaskInfoVO vo = getBeanMapper().map(entity, TaskInfoVO.class);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public Result<TaskInfoVO> getPagedTaskList(TaskPaginationDTO paginationDTO) {
        Page<TaskInfoVO> page = new Page<>(paginationDTO.getPageNo(), paginationDTO.getLimit());
        Page<TaskInfoVO> pageResult = getBaseMapper().getPagedTaskList(page, paginationDTO);
        if (Objects.isNull(pageResult) || CollectionUtils.isEmpty(pageResult.getRecords())) {
            return new Result<>(Collections.emptyList());
        }
        return new Result<>(pageResult, pageResult.getRecords());
    }

    @Override
    public boolean cleanExpiredTask(TaskExpiredCleanParam param) {
        // 1、查询条件
        LambdaQueryWrapper<TaskInfoEntity> queryWrapper = new LambdaQueryWrapper<TaskInfoEntity>()
                .eq(TaskInfoEntity::getSchoolCode, param.getSchoolCode())
                .eq(Objects.nonNull(param.getBizType()), TaskInfoEntity::getBizType, param.getBizType())
                .eq(Objects.nonNull(param.getUserId()), TaskInfoEntity::getUserId, param.getUserId());
        // 2、查询任务列表
        List<TaskInfoEntity> entityList = getBaseMapper().selectList(queryWrapper);
        if(CollectionUtils.isEmpty(entityList)){
            return Boolean.TRUE;
        }
        // 3、更新任务为删除
        TaskInfoEntity entity = new TaskInfoEntity().setIsDelete(1);
        getBaseMapper().update(entity, queryWrapper);
        // 4、删除任务状态信息缓存
        redisOperation.del(entityList.stream().map(entityItem -> TaskRedisKey.TASK_STATUS_KEY.getKey(entityItem.getId()))
                .collect(Collectors.toList()).toArray(new String[entityList.size()]));
        // 5、发布任务清理事件
        List<TaskInfoDTO> infoList = entityList.stream().map(item -> getBeanMapper().map(item, TaskInfoDTO.class)).collect(Collectors.toList());
        getEventPublisher().publishEvent(new TaskCleanEvent(this, infoList));
        return Boolean.TRUE;
    }

    @Override
    public TaskResourceVO getTaskResource(Long taskId) {
        // 1、查询任务信息
        TaskInfoEntity entity = getBaseMapper().selectById(taskId);
        // 2、转换为VO
        TaskResourceVO resourceVO = new TaskResourceVO();
        if (Objects.nonNull(entity)) {
            StringJoiner joiner = new StringJoiner(PATH_DELIMITER).add(taskInfoProvider.getUrlPrefix(entity)).add(entity.getFilePath());
            resourceVO.setFileUrl(joiner.toString());
        }
        return resourceVO;
    }

    @Override
    public TaskStatusVO getTaskStatus(Long taskId) {
        // 1、查询任务状态信息缓存
        String rdsKey = TaskRedisKey.TASK_STATUS_KEY.getKey(taskId);
        Map<Object, Object> statusMap = redisOperation.hmGet(rdsKey);
        // 2、如果缓存为空，查询数据库
        if (CollectionUtils.isEmpty(statusMap)) {
            return getTaskStatusFromDB(taskId);
        }
        // 3、返回任务状态信息
        return getBeanMapper().map(statusMap, TaskStatusVO.class);
    }

    protected TaskStatusVO getTaskStatusFromDB(Long taskId){
        // 1、查询任务信息
        TaskInfoEntity entity = getBaseMapper().selectById(taskId);
        // 2、转换为VO
        if (Objects.nonNull(entity)) {
            // 2.1、设置任务状态信息缓存
            String rdsKey = TaskRedisKey.TASK_STATUS_KEY.getKey(entity.getId());
            BeanMap statusMap = new BeanMap(entity);
            redisOperation.hmSet(rdsKey, statusMap, taskProperties.getTaskStatusExpireTime());
            // 2.2、返回任务状态信息
            return getBeanMapper().map(entity, TaskStatusVO.class);
        }
        return NOT_STARTED_TASK_STATUS;
    }

    @Override
    public boolean cancelTask(Long taskId) {
        // 1、查询任务信息
        TaskInfoEntity entity = getBaseMapper().selectById(taskId);
        // 2、判断任务是否存在
        if (Objects.nonNull(entity)) {
            // 2.1、更新任务状态为取消
            entity.setStatus(TaskStatusEnum.CANCEL.getStatus());
            getBaseMapper().updateById(entity);
            // 2.2、更新任务状态信息缓存
            String rdsKey = TaskRedisKey.TASK_STATUS_KEY.getKey(entity.getId());
            redisOperation.hSet(rdsKey, STATUS_FIELD, TaskStatusEnum.CANCEL.getStatus());
            // 2.3、发布任务取消事件
            TaskInfoDTO infoDTO = getBeanMapper().map(entity, TaskInfoDTO.class);
            getEventPublisher().publishEvent(new TaskCancelEvent(this, infoDTO));
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean deleteTask(Long taskId) {
        // 1、查询任务信息
        TaskInfoEntity entity = getBaseMapper().selectById(taskId);
        // 2、判断任务是否存在
        if (Objects.nonNull(entity)) {
            // 2.1、更新任务逻辑删除为1
            entity.setIsDelete(1);
            getBaseMapper().updateById(entity);
            // 2.2、删除任务状态信息缓存
            String rdsKey = TaskRedisKey.TASK_STATUS_KEY.getKey(entity.getId());
            redisOperation.del(rdsKey);
            // 2.3、发布任务取消事件
            TaskInfoDTO infoDTO = getBeanMapper().map(entity, TaskInfoDTO.class);
            getEventPublisher().publishEvent(new TaskDeleteEvent(this, infoDTO));
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
