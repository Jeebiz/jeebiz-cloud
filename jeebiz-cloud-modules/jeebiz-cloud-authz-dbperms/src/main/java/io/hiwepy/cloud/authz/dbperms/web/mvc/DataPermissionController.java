/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.utils.CollectionUtils;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionService;
import io.hiwepy.cloud.authz.dbperms.setup.Constants;
import io.hiwepy.cloud.authz.dbperms.web.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "数据权限：数据权限维护")
@RestController
@RequestMapping("/authz/dbperms")
@Validated
public class DataPermissionController extends BaseApiController {

    @Autowired
    private IDataPermissionService dataPermissionService;

    @ApiOperation(value = "分页查询数据权限", notes = "分页查询数据权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "DataPermissionPaginationDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_PERMS, business = "根据分组分页查询数据权限", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms:list','*') ")
    @ResponseBody
    public Result<DataPermissionDTO> list(@Valid @RequestBody DataPermissionPaginationDTO paginationDTO) {

        DataPermissionModel model = getBeanMapper().map(paginationDTO, DataPermissionModel.class);
        Page<DataPermissionModel> pageResult = getDataPermissionService().getPagedList(model);
        List<DataPermissionDTO> retList = Lists.newArrayList();
        for (DataPermissionModel permsModel : pageResult.getRecords()) {
            DataPermissionDTO permsDTO = getBeanMapper().map(permsModel, DataPermissionDTO.class);
            if (CollectionUtils.isNotEmpty(permsDTO.getItems())) {
                for (DataPermissionItemDTO itemDTO : permsDTO.getItems()) {
                    itemDTO.setConditionStr(itemDTO.getCondition().getPlaceholder());
                }
            }
            permsDTO.setRelationStr(permsDTO.getRelation().getPlaceholder());
            retList.add(permsDTO);
        }

        return new Result<DataPermissionDTO>(pageResult, retList);
    }

    @ApiOperation(value = "数据权限列表（键值对）", notes = "数据权限列表（键值对）")
    @GetMapping("pairs")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<PairModel>> pairs() {
        return ApiRestResponse.success(getDataPermissionService().getPairList());
    }

    @ApiOperation(value = "创建数据权限", notes = "增加一个新的数据权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "vo", value = "数据权限传输对象", dataType = "DataPermissionNewDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_PERMS, business = Constants.Biz.EXTRAS_PERMS_NEW, opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms:new','*') ")
    @ResponseBody
    public ApiRestResponse<String> newTable(@Valid @RequestBody DataPermissionNewDTO vo) throws Exception {
        Long ct = getDataPermissionService().getCountByName(vo.getName(), null);
        if (ct > 0) {
            return fail("authz.dbperms.name.conflict");
        }
        // 新增一条数据库配置记录
        DataPermissionModel model = getBeanMapper().map(vo, DataPermissionModel.class);
        boolean result = getDataPermissionService().save(model);
        if (result) {
            return success("authz.dbperms.new.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.dbperms.new.fail", result);
    }

    @ApiOperation(value = "删除数据权限", notes = "删除数据权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "数据权限ID,多个用,拼接", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_PERMS, business = Constants.Biz.EXTRAS_PERMS_DELETE, opt = BusinessType.UPDATE)
    @GetMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms:delete','*') ")
    @ResponseBody
    public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
        // 执行数据权限删除操作
        List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
        boolean result = getDataPermissionService().removeBatchByIds(idList);
        if (result) {
            return success("authz.dbperms.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.dbperms.delete.fail", result);
    }


    @ApiOperation(value = "更新数据权限", notes = "更新数据权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "vo", value = "数据权限", required = true, dataType = "DataPermissionRenewDTO"),
    })
    @ApiOperationLog(module = Constants.EXTRAS_PERMS, business = Constants.Biz.EXTRAS_PERMS_RENEW, opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms:renew','*') ")
    @ResponseBody
    public ApiRestResponse<String> renew(@Valid @RequestBody DataPermissionRenewDTO vo) throws Exception {
        Long ct = getDataPermissionService().getCountByName(vo.getName(), vo.getId());
        if (ct > 0) {
            return fail("authz.dbperms.name.conflict");
        }
        DataPermissionModel model = getBeanMapper().map(vo, DataPermissionModel.class);
        boolean result = getDataPermissionService().updateById(model);
        if (result) {
            return success("authz.dbperms.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.dbperms.renew.fail", result);
    }

    @ApiOperation(value = "更新数据权限状态", notes = "更新数据权限状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "数据权限ID", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "status", required = true, value = "数据权限状态", dataType = "String", allowableValues = "1,0")
    })
    @ApiOperationLog(module = Constants.EXTRAS_PERMS, business = Constants.Biz.EXTRAS_PERMS_RENEW, opt = BusinessType.UPDATE)
    @GetMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms:status','*') ")
    @ResponseBody
    public ApiRestResponse<String> status(@Valid @NotNull(message = "表信息ID不能为空") @RequestParam("id") String id,
                                          @NotNull(message = "表信息状态不能为空") @RequestParam("status") String status) throws Exception {
        int result = getDataPermissionService().setStatus(id, status);
        if (result > 0) {
            return success("authz.dbperms.status.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.dbperms.status.fail", result);
    }

    @ApiOperation(value = "查询数据权限", notes = "根据ID查询数据权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "数据权限ID", dataType = "String")
    })
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms:detail','*') ")
    @ResponseBody
    public ApiRestResponse<DataPermissionDTO> detail(@Valid @NotNull(message = "表信息ID不能为空") @RequestParam("id") String id) throws Exception {
        DataPermissionModel model = getDataPermissionService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("authz.dbperms.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, DataPermissionDTO.class));
    }

    public IDataPermissionService getDataPermissionService() {
        return dataPermissionService;
    }

}