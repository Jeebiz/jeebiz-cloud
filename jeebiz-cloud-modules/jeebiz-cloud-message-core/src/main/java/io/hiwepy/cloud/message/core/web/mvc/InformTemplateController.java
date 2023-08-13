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
import io.hiwepy.cloud.message.core.dao.entities.MessageTemplateEntity;
import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import io.hiwepy.cloud.message.core.service.IMessageTemplateService;
import io.hiwepy.cloud.message.core.setup.Constants;
import io.hiwepy.cloud.message.core.web.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "消息通知模板：消息模板管理")
@RestController
@RequestMapping("/inform/tmpl/")
public class InformTemplateController extends BaseMapperController {

    @Autowired
    private IMessageTemplateService informTemplateService;

    @ApiOperation(value = "查询消息通知模板", notes = "分页查询消息通知模板")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "消息筛选条件", dataType = "InformTemplatePaginationDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "分页查询消息通知模板", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated")
    @ResponseBody
    public Result<InformTemplateDTO> list(@Valid @RequestBody InformTemplatePaginationDTO paginationDTO) throws Exception {

        MessageTemplateEntity model = getBeanMapper().map(paginationDTO, MessageTemplateEntity.class);
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        if (!principal.isAdmin()) {
            model.setUserId(principal.getUid());
        }
        Page<MessageTemplateEntity> pageResult = getInformTemplateService().getPagedList(model);
        List<InformTemplateDTO> retList = new ArrayList<InformTemplateDTO>();
        for (MessageTemplateEntity registryModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(registryModel, InformTemplateDTO.class));
        }

        return new Result<InformTemplateDTO>(pageResult, retList);

    }

    @ApiOperation(value = "消息通知模板下拉列表", notes = "消息通知模板下拉列表")
    @GetMapping("pairs")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<InformTemplatePairDTO>> list() throws Exception {
        List<InformTemplatePairDTO> retList = getInformTemplateService().getPairs();
        return ApiRestResponse.success(retList);
    }

    @ApiOperation(value = "模板类型", notes = "模板类型列表")
    @GetMapping("types")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<PairModel>> types() throws Exception {
        return ApiRestResponse.success(InformSendChannel.toList());
    }

    @ApiOperation(value = "消息通知模板统计信息", notes = "查询消息通知模板统计信息")
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "查询消息通知模板统计信息", opt = BusinessType.SELECT)
    @GetMapping("stats")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<InformTemplateStatsDTO>> stats() throws Exception {
        return ApiRestResponse.success(getInformTemplateService().getStats());
    }

    @ApiOperation(value = "创建消息通知模板", notes = "增加一个新的消息通知模板")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "newDTO", value = "消息通知模板传输对象", dataType = "InformTemplateNewDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "创建消息通知模板", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('inform-tmpl:new','*') ")
    @ResponseBody
    public ApiRestResponse<String> newTmpl(@Valid @RequestBody InformTemplateNewDTO newDTO) throws Exception {
        MessageTemplateEntity entity = getBeanMapper().map(newDTO, MessageTemplateEntity.class);
        entity.setCreator(SubjectUtils.getUserId());
        // 新增一条数据库配置记录
        boolean result = getInformTemplateService().save(entity);
        if (result) {
            return success("inform.template.new.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("inform.template.new.fail", result);
    }

    @ApiOperation(value = "删除消息通知模板", notes = "删除消息通知模板")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "消息通知模板id,多个用,拼接", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "删除消息通知模板", opt = BusinessType.DELETE)
    @DeleteMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('inform-tmpl:delete','*') ")
    @ResponseBody
    public ApiRestResponse<String> delete(@RequestParam("ids") String ids) throws Exception {
        // 执行消息通知模板删除操作
        List<String> idList = Lists.newArrayList(StringUtils.split(ids, ","));
        boolean result = getInformTemplateService().removeByIds(idList);
        if (result) {
            return success("inform.template.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("inform.template.delete.fail", result);
    }

    @ApiOperation(value = "更新消息通知模板", notes = "更新消息通知模板")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "renewDTO", value = "消息通知模板", required = true, dataType = "InformTemplateRenewDTO"),
    })
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "更新消息通知模板", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('inform-tmpl:renew','*') ")
    @ResponseBody
    public ApiRestResponse<String> renew(@Valid @RequestBody InformTemplateRenewDTO renewDTO) throws Exception {
        MessageTemplateEntity entity = getBeanMapper().map(renewDTO, MessageTemplateEntity.class);
        entity.setModifyer(SubjectUtils.getUserId());
        boolean result = getInformTemplateService().updateById(entity);
        if (result) {
            return success("inform.template.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("inform.template.renew.fail", result);
    }

    @ApiOperation(value = "消息通知模板信息", notes = "查询指定id的消息通知模板信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "消息通知模板id", required = true, dataType = "String"),
    })
    @ApiOperationLog(module = Constants.EXTRAS_INFORM, business = "查询指定id的消息通知模板信息", opt = BusinessType.SELECT)
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('inform-tmpl:detail','*') ")
    @ResponseBody
    public ApiRestResponse<InformTemplateDTO> detail(@RequestParam("id") String id) throws Exception {

        MessageTemplateEntity model = getInformTemplateService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("inform.template.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, InformTemplateDTO.class));
    }

    public IMessageTemplateService getInformTemplateService() {
        return informTemplateService;
    }

}
