package io.hiwepy.cloud.base.task.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.base.task.entity.TaskInfoEntity;
import io.hiwepy.cloud.base.task.param.TaskExpiredCleanParam;
import io.hiwepy.cloud.base.task.param.TaskQueryParam;
import io.hiwepy.cloud.base.task.dto.TaskCreateDTO;
import io.hiwepy.cloud.base.task.dto.TaskPaginationDTO;
import io.hiwepy.cloud.base.task.vo.TaskInfoVO;
import io.hiwepy.cloud.base.task.vo.TaskResourceVO;
import io.hiwepy.cloud.base.task.vo.TaskStatusVO;

import java.util.List;

/**
 * <p>
 * 任务信息表 服务类
 * </p>
 *
 * @author wandl
 * @since 2023-07-18
 */
public interface ITaskInfoService extends IBaseService<TaskInfoEntity> {

    /**
     * 创建任务
     *
     * @param createDTO 任务创建参数
     * @return 任务状态信息
     */
    TaskStatusVO createTask(TaskCreateDTO createDTO);

    /**
     * 根据条件查询任务信息列表
     *
     * @param param 查询参数
     * @return 任务信息列表
     */
    List<TaskInfoVO> getTaskList(TaskQueryParam param);

    /**
     * 分页查询任务信息列表
     * @param param
     * @return
     */
    Result<TaskInfoVO> getPagedTaskList(TaskPaginationDTO param);

    /**
     * 清理过期任务
     * @param param
     * @return
     */
    boolean cleanExpiredTask(TaskExpiredCleanParam param);

    /**
     * 获取任务资源信息
     * @param taskId
     * @return
     */
    TaskResourceVO getTaskResource(Long taskId);

    /**
     * 获取任务状态信息
     * @param taskId
     * @return
     */
    TaskStatusVO getTaskStatus(Long taskId);

    /**
     * 取消任务
     * @param taskId
     * @return
     */
    boolean cancelTask(Long taskId);

    /**
     * 删除任务
     * @param taskId
     * @return
     */
    boolean deleteTask(Long taskId);

}
