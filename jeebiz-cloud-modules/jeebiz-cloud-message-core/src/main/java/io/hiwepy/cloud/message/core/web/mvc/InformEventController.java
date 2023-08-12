/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.message.core.dao.entities.MessageEventEntity;
import io.hiwepy.cloud.message.core.emums.InformEventType;
import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import io.hiwepy.cloud.message.core.emums.InformTarget;
import io.hiwepy.cloud.message.core.service.IMessageEventService;
import io.hiwepy.cloud.message.core.setup.Constants;
import io.hiwepy.cloud.message.core.web.dto.*;
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
import java.util.Objects;

@Api(tags = "消息通知：消息事件")
@RestController
@RequestMapping("/inform/evnet/")
public class InformEventController extends BaseMapperController {

    @Autowired
    private IMessageEventService informEventService;

    @ApiOperation(value = "查询消息通知事件", notes = "分页查询消息通知事件")
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "分页查询消息通知事件", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<Result<InformEventDTO>> list(@Valid @RequestBody InformEventPaginationDTO paginationDTO) throws Exception {

        MessageEventEntity entity = getBeanMapper().map(paginationDTO, MessageEventEntity.class);
        Page<MessageEventEntity> pageResult = getInformEventService().getPagedList(entity);
        List<InformEventDTO> retList = new ArrayList<InformEventDTO>();
        for (MessageEventEntity registryModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(registryModel, InformEventDTO.class));
        }

        return ApiRestResponse.success(new Result<InformEventDTO>(pageResult, retList));

    }

    @ApiOperation(value = "事件类型", notes = "事件类型列表（示例，应该去查询字典数据）")
    @GetMapping("types")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<PairModel>> types() throws Exception {
        return ApiRestResponse.success(InformEventType.toList());
    }

    @ApiOperation(value = "事件行为", notes = "事件行为列表")
    @GetMapping("actions")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<PairModel>> actions() throws Exception {
        return ApiRestResponse.success(InformSendChannel.toList());
    }

    @ApiOperation(value = "消息通知事件统计信息", notes = "查询消息通知事件统计信息")
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "查询消息通知事件统计信息", opt = BusinessType.SELECT)
    @GetMapping("stats")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<InformEventStatsDTO>> stats() throws Exception {
        return ApiRestResponse.success(getInformEventService().getStats());
    }

    @ApiOperation(value = "创建消息通知事件", notes = "增加一个新的消息通知事件")
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "创建消息通知事件", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('inform-event:new','*') ")
    @ResponseBody
    public ApiRestResponse<String> newEvent(@Valid @RequestBody InformEventNewDTO newDTO) throws Exception {
        MessageEventEntity entity = getBeanMapper().map(newDTO, MessageEventEntity.class);
        Long ct = getInformEventService().getCount(entity);
        if (ct > 0) {
            return fail("inform.event.new.conflict");
        }
        String userId = SubjectUtils.getUserId();
        entity.setCreator(userId);
        entity.setTarget(InformTarget.ALL);
        // 新增一条数据库配置记录
        boolean result = getInformEventService().save(entity);
        if (result) {
            return success("inform.event.new.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("inform.event.new.fail", result);
    }

    @ApiOperation(value = "删除消息通知事件", notes = "删除消息通知事件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "消息通知事件id,多个用,拼接", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "删除消息通知事件", opt = BusinessType.DELETE)
    @DeleteMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('inform-event:delete','*') ")
    @ResponseBody
    public ApiRestResponse<String> delete(@RequestParam("ids") String ids) throws Exception {
        // 执行消息通知事件删除操作
        List<String> idList = Lists.newArrayList(StringUtils.split(ids, ","));
        boolean result = getInformEventService().removeByIds(idList);
        if (result) {
            return success("inform.event.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("inform.event.delete.fail", result);
    }

    @ApiOperation(value = "更新消息通知事件", notes = "更新消息通知事件")
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "更新消息通知事件", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('inform-event:renew','*') ")
    @ResponseBody
    public ApiRestResponse<String> renew(@Valid @RequestBody InformEventRenewDTO renewDTO) throws Exception {
        MessageEventEntity entity = getBeanMapper().map(renewDTO, MessageEventEntity.class);
        Long ct = getInformEventService().getCount(entity);
        if (ct > 0) {
            return fail("inform.event.renew.conflict");
        }
        String userId = SubjectUtils.getUserId();
        entity.setModifyer(userId);
        boolean result = getInformEventService().updateById(entity);
        if (result) {
            return success("inform.event.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("inform.event.renew.fail", result);
    }

    @ApiOperation(value = "消息通知事件信息", notes = "查询指定id的消息通知事件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "消息通知事件id", required = true, dataType = "String"),
    })
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "查询指定id的消息通知事件信息", opt = BusinessType.SELECT)
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('inform-event:detail','*') ")
    @ResponseBody
    public ApiRestResponse<InformEventDTO> detail(@RequestParam("id") String id) throws Exception {

        MessageEventEntity model = getInformEventService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("inform.event.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, InformEventDTO.class));
    }

    @ApiOperation(value = "设置消息通知事件接收对象", notes = "设置指定消息通知事件设置的接收对象")
    @PostMapping("target")
    @PreAuthorize("authenticated and hasAnyAuthority('inform-event:target','*') ")
    @ResponseBody
    public ApiRestResponse<String> setTargets(@Valid @RequestBody InformEventTargetsDTO targetsDto) throws Exception {
        // 新增一条数据库配置记录
        boolean result = getInformEventService().setTargets(targetsDto);
        if (result) {
            return success("inform.event.target.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("inform.event.target.fail", result);
    }

    @ApiOperation(value = "查询消息通知事件接收对象", notes = "查询指定消息通知事件设置的接收对象")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "消息通知事件id", required = true, dataType = "String"),
    })
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "查询指定id的消息通知事件信息", opt = BusinessType.SELECT)
    @GetMapping("target")
    @PreAuthorize("authenticated and hasAnyAuthority('inform-event:target','*') ")
    @ResponseBody
    public ApiRestResponse<InformEventTargetsDTO> getTargets(@RequestParam("eventId") String eventId) throws Exception {
        InformEventTargetsDTO targetsDto = getInformEventService().getTargets(eventId);
        if (Objects.isNull(targetsDto)) {
            return ApiRestResponse.fail(null);
        }
        return ApiRestResponse.success(targetsDto);
    }

    public IMessageEventService getInformEventService() {
        return informEventService;
    }

}
