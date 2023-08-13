/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.web.mvc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.message.core.dao.entities.MessageRecordEntity;
import io.hiwepy.cloud.message.core.service.IMessageRecordService;
import io.hiwepy.cloud.message.core.setup.Constants;
import io.hiwepy.cloud.message.core.web.dto.InformRecordDTO;
import io.hiwepy.cloud.message.core.web.dto.InformRecordPaginationDTO;
import io.hiwepy.cloud.message.core.web.dto.InformRecordStatsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "消息通知：我的消息")
@RestController
@RequestMapping("/inform/")
public class InformRecordController extends BaseMapperController {

    @Autowired
    private IMessageRecordService informService;

    @ApiOperation(value = "待处理通知总数", notes = "查询待处理通知总数")
    @GetMapping("pending")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<Long> pending() throws Exception {
        MessageRecordEntity entity = new MessageRecordEntity();
        entity.setReceiverId(SubjectUtils.getUserId());
        return ApiRestResponse.success(getInformService().getCount(entity));
    }

    @ApiOperation(value = "查询消息通知", notes = "分页查询消息通知")
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "分页查询消息通知", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated")
    @ResponseBody
    public Result<InformRecordDTO> list(@Valid @RequestBody InformRecordPaginationDTO paginationDTO) throws Exception {

        MessageRecordEntity model = getBeanMapper().map(paginationDTO, MessageRecordEntity.class);
        model.setReceiverId(SubjectUtils.getUserId());

        Page<MessageRecordEntity> pageResult = getInformService().getPagedList(model);
        List<InformRecordDTO> retList = new ArrayList<InformRecordDTO>();
        for (MessageRecordEntity registryModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(registryModel, InformRecordDTO.class));
        }

        return new Result<InformRecordDTO>(pageResult, retList);

    }

    @ApiOperation(value = "消息通知统计信息", notes = "查询消息通知统计信息")
    @GetMapping("stats")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<InformRecordStatsDTO>> stats() throws Exception {
        return ApiRestResponse.success(getInformService().getStats(SubjectUtils.getUserId()));
    }

    @ApiOperation(value = "消息通知信息", notes = "查询指定id的消息通知信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "消息通知id", required = true, dataType = "String"),
    })
    @GetMapping("detail")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<InformRecordDTO> detail(@RequestParam("id") String id) throws Exception {

        MessageRecordEntity model = getInformService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("inform.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, InformRecordDTO.class));

    }

    @ApiOperation(value = "阅读消息通知", notes = "阅读消息通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "消息通知id,多个用,拼接", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "阅读消息通知", opt = BusinessType.UPDATE)
    @GetMapping("read")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<String> read(@RequestParam("ids") String ids) throws Exception {

        MessageRecordEntity entity = new MessageRecordEntity();
        entity.setStatus(Constants.Normal.IS_READ_YES);

        // 执行消息通知阅读操作
        boolean result = getInformService().update(entity, new LambdaQueryWrapper<MessageRecordEntity>()
                .eq(MessageRecordEntity::getReceiverId, SubjectUtils.getUserId())
                .eq(MessageRecordEntity::getStatus, Constants.Normal.IS_READ_NO)
                .in(MessageRecordEntity::getId, Lists.newArrayList(StringUtils.split(ids, ","))));
        if (result) {
            return success("inform.read.success");
        }
        // 逻辑代码，如果发生异常将不会被执行
        return success("inform.read.error");
    }

    @ApiOperation(value = "阅读消息通知", notes = "阅读消息通知")
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "阅读消息通知", opt = BusinessType.UPDATE)
    @PostMapping("readall")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<String> readall() throws Exception {
        MessageRecordEntity entity = new MessageRecordEntity();
        entity.setStatus(Constants.Normal.IS_READ_YES);
        // 执行消息通知阅读操作
        boolean result = getInformService().update(entity, new LambdaQueryWrapper<MessageRecordEntity>()
                .eq(MessageRecordEntity::getReceiverId, SubjectUtils.getUserId())
                .eq(MessageRecordEntity::getStatus, Constants.Normal.IS_READ_NO));
        if (result) {
            return success("inform.readall.success");
        }
        // 逻辑代码，如果发生异常将不会被执行
        return success("inform.readall.error");
    }

    @ApiOperation(value = "删除消息通知", notes = "删除消息通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "消息通知id,多个用,拼接", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "删除消息通知", opt = BusinessType.UPDATE)
    @GetMapping("delete")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
        // 执行消息通知删除操作
        boolean result = getInformService().removeBatchByIds(Lists.newArrayList(StringUtils.split(ids, ",")));
        if (result) {
            return success("inform.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return success("inform.delete.error", result);
    }

    public IMessageRecordService getInformService() {
        return informService;
    }

}
