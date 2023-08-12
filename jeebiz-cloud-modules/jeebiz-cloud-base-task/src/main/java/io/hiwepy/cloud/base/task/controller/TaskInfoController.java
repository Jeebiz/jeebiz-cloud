package io.hiwepy.cloud.base.task.controller;

import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.web.BaseController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.base.task.param.TaskExpiredCleanParam;
import io.hiwepy.cloud.base.task.param.TaskQueryParam;
import io.hiwepy.cloud.base.task.service.ITaskInfoService;
import io.hiwepy.cloud.base.task.dto.TaskCreateDTO;
import io.hiwepy.cloud.base.task.dto.TaskPaginationDTO;
import io.hiwepy.cloud.base.task.vo.TaskInfoVO;
import io.hiwepy.cloud.base.task.vo.TaskResourceVO;
import io.hiwepy.cloud.base.task.vo.TaskStatusVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 任务管理 前端控制器
 * </p>
 *
 * @author wandl
 * @since 2023-07-25
 */
@Api(tags = "任务管理：创建、查询、删除、取消、下载、清理")
@RestController
@RequestMapping("/task/info")
@Slf4j
public class TaskInfoController extends BaseController {

    @Autowired
    private ITaskInfoService taskInfoService;

    @ApiOperation(value = "创建任务", notes = "创建指定类型的任务")
    @PreAuthorize("authenticated and hasAnyAuthority('task:info:create','*') ")
    @PostMapping("create")
    public ApiRestResponse<TaskStatusVO> createTask(@RequestBody @Valid TaskCreateDTO createDTO) throws Exception {
        // 1、保存激励体系
        TaskStatusVO returnVO = getTaskInfoService().createTask(createDTO);
        if (Objects.nonNull(returnVO)) {
            return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("task.info.create.success"), returnVO);
        }
        return success("task.info.create.fail");
    }

    /**
     * 查询下载任务列表
     */
    @ApiOperation(value = "任务列表", notes = "查询查询符合条件的任务列表")
    @GetMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('task:info:list','*') ")
    public ApiRestResponse<List<TaskInfoVO>> getTaskList(@Valid TaskQueryParam param) {
        try {
            List<TaskInfoVO> pageResult = getTaskInfoService().getTaskList(param);
            return ApiRestResponse.success(pageResult);
        } catch (Exception e) {
            logException(this, e);
            return ApiRestResponse.success(Collections.emptyList());
        }
    }

    /**
     * 分页查询下载任务列表
     */
    @ApiOperation(value = "任务列表-分页", notes = "分页查询查询符合条件的任务列表")
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('task:info:list','*') ")
    public ApiRestResponse<Result<TaskInfoVO>> getPagedTaskList(@RequestBody @Valid TaskPaginationDTO param) {
        try {
            Result<TaskInfoVO> pageResult = getTaskInfoService().getPagedTaskList(param);
            return ApiRestResponse.success(pageResult);
        } catch (Exception e) {
            logException(this, e);
            return ApiRestResponse.success(new Result<>());
        }
    }

    /**
     * 一键清除过期任务
     */
    @ApiOperation(value = "一键清除过期任务")
    @PostMapping("cleanExpired")
    @PreAuthorize("authenticated and hasAnyAuthority('task:expired:clean','*')")
    public ApiRestResponse<String> cleanExpiredTask(@RequestBody @Valid TaskExpiredCleanParam param) {
        boolean flag = getTaskInfoService().cleanExpiredTask(param);
        if (flag) {
            return success("task.expired.clean.success");
        }
        return success("task.expired.clean.fail");
    }

    /**
     * 资源下载接口
     */
    @ApiOperation(value = "资源下载接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "taskId", value = "任务Id", required = true)
    })
    @GetMapping("download")
    @PreAuthorize("authenticated and hasAnyAuthority('task:info:status','*')")
    public ApiRestResponse<TaskResourceVO> getTaskResource(@Valid @RequestParam("taskId") @NotNull(message = "任务ID不能为空") Long taskId) {
        try {
            TaskResourceVO resourceVO = getTaskInfoService().getTaskResource(taskId);
            return ApiRestResponse.success(resourceVO);
        } catch (Exception e) {
            logException(this, e);
            return ApiRestResponse.success(new TaskResourceVO());
        }
    }

    /**
     * 获取任务状态
     */
    @ApiOperation(value = "获取任务状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "taskId", value = "任务Id", required = true)
    })
    @GetMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('task:info:status','*')")
    public ApiRestResponse<TaskStatusVO> getTaskStatus(@Valid @RequestParam("taskId") @NotNull(message = "任务ID不能为空") Long taskId) {
        try {
            TaskStatusVO taskStatusVO = getTaskInfoService().getTaskStatus(taskId);
            return ApiRestResponse.success(taskStatusVO);
        } catch (Exception e) {
            logException(this, e);
            return ApiRestResponse.success(new TaskStatusVO());
        }
    }

    /**
     * 取消任务接口
     */
    @ApiOperation(value = "取消任务接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "taskId", value = "任务Id", required = true)
    })
    @GetMapping("cancel")
    @PreAuthorize("authenticated and hasAnyAuthority('task:info:cancel','*')")
    public ApiRestResponse<String> cancelTask(@Valid @RequestParam("taskId") @NotNull(message = "任务ID不能为空") Long taskId) {
        boolean flag = getTaskInfoService().cancelTask(taskId);
        if (flag) {
            return success("task.info.cancel.success");
        }
        return success("task.info.cancel.fail");
    }

    /**
     * 删除任务接口
     */
    @ApiOperation(value = "删除任务接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "taskId", value = "任务Id", required = true)
    })
    @GetMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('task:info:delete','*')")
    public ApiRestResponse<String> deleteTask(@Valid @RequestParam("taskId") @NotNull(message = "任务ID不能为空") Long taskId) {
        boolean flag = getTaskInfoService().deleteTask(taskId);
        if (flag) {
            return success("task.info.delete.success");
        }
        return success("task.info.delete.fail");
    }

    public ITaskInfoService getTaskInfoService() {
        return taskInfoService;
    }

}
