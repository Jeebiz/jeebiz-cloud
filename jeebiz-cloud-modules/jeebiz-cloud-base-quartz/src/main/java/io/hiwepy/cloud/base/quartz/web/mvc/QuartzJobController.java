/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.quartz.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.base.quartz.dao.entities.QuartzJobModel;
import io.hiwepy.cloud.base.quartz.service.IQuartzJobService;
import io.hiwepy.cloud.base.quartz.setup.Constants;
import io.hiwepy.cloud.base.quartz.web.dto.QuartzJobDTO;
import io.hiwepy.cloud.base.quartz.web.dto.QuartzJobPaginationDTO;
import io.hiwepy.cloud.base.quartz.web.dto.QuartzJobRenewDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "任务调度：任务管理")
@Validated
@RestController
@RequestMapping("/quartz/")
public class QuartzJobController extends BaseApiController {

    @Autowired
    private IQuartzJobService quartzJobService;

    @ApiOperation(value = "分页查询任务信息", notes = "分页查询定时任务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "分页筛选条件", dataType = "QuartzJobPaginationDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_JOB, business = "分页查询定时任务信息", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated")
    @ResponseBody
    public Object list(@Valid @RequestBody QuartzJobPaginationDTO paginationDTO) throws Exception {

        QuartzJobModel model = getBeanMapper().map(paginationDTO, QuartzJobModel.class);
        Page<QuartzJobModel> pageResult = getQuartzJobService().getPagedList(model);
        List<QuartzJobDTO> retList = new ArrayList<QuartzJobDTO>();
        for (QuartzJobModel registryModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(registryModel, QuartzJobDTO.class));
        }

        return new Result<QuartzJobDTO>(pageResult, retList);

    }

    @ApiOperation(value = "任务详情", notes = "任务详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "任务记录ID编号", dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_JOB, business = "任务详情", opt = BusinessType.SELECT)
    @PreAuthorize("authenticated and hasAnyAuthority('quzrtz:detail','*') ")
    @GetMapping("detail/{id}")
    public Object detail(@PathVariable(value = "id") String id) throws Exception {

        QuartzJobModel jobModel = getQuartzJobService().getById(id);
        if (jobModel == null) {
            return ApiRestResponse.fail(getMessage("quzrtz.job.not-found"));
        }
        return ApiRestResponse.success(jobModel);

    }

    /**
     @ApiOperation(value = "新建任务", notes = "新建定时任务")
     @ApiImplicitParams({
     @ApiImplicitParam(paramType = "body", name = "jobDTO", required = true, value = "定时任务", dataType = "QuartzJobNewDTO")
     })
     @ApiOperationLog(module = Constants.EXTRAS_JOB, business = "新建定时任务：${jobName}", opt = BusinessType.INSERT)
     @PostMapping("new")
     @PreAuthorize("authenticated and hasAnyAuthority('quzrtz:new','*') ")
     @ResponseBody public Object newJob(@Valid @RequestBody QuartzJobNewDTO jobDTO) throws Exception {
     int total = getQuartzJobService().getCountByUid(jobDTO.getBizId());
     if(total > 0) {
     return fail("quzrtz.job.new.exists");
     }
     QuartzJobModel model = getBeanMapper().map(jobDTO, QuartzJobModel.class);
     int result = getQuartzJobService().addJob(model);
     if(result == 1) {
     return success("quzrtz.job.new.success", result);
     }
     return fail("quzrtz.job.new.fail", result);
     }*/

    @ApiOperation(value = "更新任务", notes = "更新定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "jobDTO", required = true, value = "定时任务", dataType = "QuartzJobRenewDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_JOB, business = "修改用户-名称：${name}", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('quzrtz:renew','*') ")
    @ResponseBody
    public Object renew(@Valid @RequestBody QuartzJobRenewDTO jobDTO) throws Exception {
        QuartzJobModel model = getBeanMapper().map(jobDTO, QuartzJobModel.class);
        int result = getQuartzJobService().updateJob(model);
        if (result == 1) {
            return success("quzrtz.job.renew.success", result);
        }
        return fail("quzrtz.job.renew.fail", result);
    }

    @ApiOperation(value = "任务暂停", notes = "暂停执行中的定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", required = true, value = "任务记录ID编号", dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_JOB, business = Constants.Biz.JOB_PRE_PAUSE, opt = BusinessType.UPDATE)
    @PostMapping("pause/{id}")
    @PreAuthorize("authenticated and hasAnyAuthority('quzrtz:pause','*') ")
    @ResponseBody
    public Object pause(@PathVariable String id) throws Exception {
        //	任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败）
        QuartzJobModel jobModel = getQuartzJobService().getById(id);
        if (jobModel == null) {
            return ApiRestResponse.fail(getMessage("quzrtz.job.not-found"));
        }
        if (StringUtils.equalsIgnoreCase(jobModel.getStatus(), "0")) {
            return fail("quzrtz.job.pause.done");
        }
        if (!StringUtils.equalsIgnoreCase(jobModel.getStatus(), "1")) {
            return fail("quzrtz.job.pause.complete");
        }
        int result = getQuartzJobService().pauseJob(jobModel);
        if (result == 1) {
            return success("quzrtz.job.pause.success", result);
        }
        return fail("quzrtz.job.pause.fail", result);
    }

    @ApiOperation(value = "恢复任务", notes = "恢复任务的执行状态", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", required = true, value = "任务记录ID编号", dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_JOB, business = Constants.Biz.JOB_PRE_RESUME, opt = BusinessType.UPDATE)
    @PostMapping("resume/{id}")
    @PreAuthorize("authenticated and hasAnyAuthority('quzrtz:resume','*') ")
    @ResponseBody
    public Object resume(@PathVariable String id) throws Exception {

        //	任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败）
        QuartzJobModel jobModel = getQuartzJobService().getById(id);
        if (jobModel == null) {
            return ApiRestResponse.fail(getMessage("quzrtz.job.not-found"));
        }
        if (StringUtils.equalsIgnoreCase(jobModel.getStatus(), "1")) {
            return fail("quzrtz.job.resume.done");
        }
        if (!StringUtils.equalsIgnoreCase(jobModel.getStatus(), "0")) {
            return fail("quartz.job.resume.complete");
        }
        int result = getQuartzJobService().resumeJob(jobModel);
        if (result == 1) {
            return success("quzrtz.job.resume.success", result);
        }
        return fail("quzrtz.job.resume.fail", result);
    }

    @ApiOperation(value = "移除任务", notes = "移除任务：移除任务管理器中的任务，同时标记任务状态为删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", required = true, value = "任务记录ID编号", dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_JOB, business = "删除用户-名称：${quzrtzid}", opt = BusinessType.DELETE)
    @GetMapping("delete/{id}")
    @PreAuthorize("authenticated and hasAnyAuthority('quzrtz:delete','*') ")
    @ResponseBody
    public Object delete(@PathVariable String id) throws Exception {
        //	任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败）
        QuartzJobModel jobModel = getQuartzJobService().getById(id);
        if (jobModel == null) {
            return ApiRestResponse.fail(getMessage("quzrtz.job.not-found"));
        }
        int total = getQuartzJobService().deleteJob(jobModel);
        if (total > 0) {
            return success("quzrtz.job.delete.success", total);
        }
        return fail("quzrtz.job.delete.fail", total);
    }


    public IQuartzJobService getQuartzJobService() {
        return quartzJobService;
    }

    public void setQuartzJobService(IQuartzJobService quartzJobService) {
        this.quartzJobService = quartzJobService;
    }

}
