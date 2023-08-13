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
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionGroupCheckedModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionGroupModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionGroupService;
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

@Api(tags = "数据权限：数据权限组维护")
@RestController
@RequestMapping("/authz/dbperms/group")
@Validated
public class DataPermissionGroupController extends BaseApiController {

    @Autowired
    private IDataPermissionGroupService dataPermissionGroupService;

    @ApiOperation(value = "分页查询数据权限组", notes = "分页查询数据权限组")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "DataPermissionGroupPaginationDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_PERMS_GROUP, business = "根据分组分页查询数据权限组", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms-group:list','*') ")
    @ResponseBody
    public Result<DataPermissionGroupDTO> list(@Valid @RequestBody DataPermissionGroupPaginationDTO paginationDTO) {

        DataPermissionGroupModel model = getBeanMapper().map(paginationDTO, DataPermissionGroupModel.class);
        Page<DataPermissionGroupModel> pageResult = getDataPermissionGroupService().getPagedList(model);
        List<DataPermissionGroupDTO> retList = Lists.newArrayList();
        for (DataPermissionGroupModel groupModel : pageResult.getRecords()) {
            DataPermissionGroupDTO groupDTO = getBeanMapper().map(groupModel, DataPermissionGroupDTO.class);
            for (DataPermissionDTO permsDTO : groupDTO.getPerms()) {
                if (CollectionUtils.isNotEmpty(permsDTO.getItems())) {
                    for (DataPermissionItemDTO itemDTO : permsDTO.getItems()) {
                        itemDTO.setConditionStr(itemDTO.getCondition().getPlaceholder());
                    }
                }
                permsDTO.setRelationStr(permsDTO.getRelation().getPlaceholder());
            }
            retList.add(groupDTO);
        }

        return new Result<DataPermissionGroupDTO>(pageResult, retList);
    }

    @ApiOperation(value = "数据权限组列表（键值对）", notes = "数据权限组列表（键值对）")
    @GetMapping("pairs")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms-group:list','*') ")
    public ApiRestResponse<List<PairModel>> pairs() {
        return ApiRestResponse.success(getDataPermissionGroupService().getPairList());
    }

    @ApiOperation(value = "角色数据权限组列表（键值对）", notes = "角色数据权限组列表（键值对）")
    @GetMapping("role/pairs")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms-group:list','*') ")
    public ApiRestResponse<List<DataPermissionGroupCheckedModel>> rolePairs(@Valid @NotNull(message = "角色ID不能为空") @RequestParam("rid") String rid) {
        return ApiRestResponse.success(getDataPermissionGroupService().getGroupRolePairs(rid));
    }

    @ApiOperation(value = "用户数据权限组列表（键值对）", notes = "用户数据权限组列表（键值对）")
    @GetMapping("user/pairs")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms-group:list','*') ")
    public ApiRestResponse<List<DataPermissionGroupCheckedModel>> userPairs(@Valid @NotNull(message = "用户ID不能为空") @RequestParam("uid") String uid) {
        return ApiRestResponse.success(getDataPermissionGroupService().getGroupUserPairs(uid));
    }

    @ApiOperation(value = "创建数据权限组", notes = "增加一个新的数据权限组")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "vo", value = "数据权限组传输对象", dataType = "DataPermissionGroupNewDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_PERMS_GROUP, business = Constants.Biz.EXTRAS_PERMS_GROUP_NEW, opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms-group:new','*') ")
    @ResponseBody
    public ApiRestResponse<String> newTable(@Valid @RequestBody DataPermissionGroupNewDTO vo) throws Exception {
        Long ct = getDataPermissionGroupService().getCountByName(vo.getName(), null);
        if (ct > 0) {
            return fail("authz.dbperms.group.name.conflict");
        }
        // 新增一条数据库配置记录
        DataPermissionGroupModel model = getBeanMapper().map(vo, DataPermissionGroupModel.class);
        boolean result = getDataPermissionGroupService().save(model);
        if (result) {
            return success("authz.dbperms.group.new.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.dbperms.group.new.fail", result);
    }

    @ApiOperation(value = "删除数据权限组", notes = "删除数据权限组")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "数据权限组ID,多个用,拼接", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_PERMS_GROUP, business = Constants.Biz.EXTRAS_PERMS_GROUP_DELETE, opt = BusinessType.UPDATE)
    @GetMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms-group:delete','*') ")
    @ResponseBody
    public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
        // 执行数据权限组删除操作
        List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
        boolean result = getDataPermissionGroupService().removeBatchByIds(idList);
        if (result) {
            return success("authz.dbperms.group.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.dbperms.group.delete.fail", result);
    }

    @ApiOperation(value = "更新数据权限组", notes = "更新数据权限组")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "vo", value = "数据权限组", required = true, dataType = "DataPermissionGroupRenewDTO"),
    })
    @ApiOperationLog(module = Constants.EXTRAS_PERMS_GROUP, business = Constants.Biz.EXTRAS_PERMS_GROUP_RENEW, opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms-group:renew','*') ")
    @ResponseBody
    public ApiRestResponse<String> renew(@Valid @RequestBody DataPermissionGroupRenewDTO vo) throws Exception {
        Long ct = getDataPermissionGroupService().getCountByName(vo.getName(), vo.getId());
        if (ct > 0) {
            return fail("authz.dbperms.group.name.conflict");
        }
        DataPermissionGroupModel model = getBeanMapper().map(vo, DataPermissionGroupModel.class);
        boolean result = getDataPermissionGroupService().updateById(model);
        if (result) {
            return success("authz.dbperms.group.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.dbperms.group.renew.fail", result);
    }

    @ApiOperation(value = "更新数据权限组状态", notes = "更新数据权限组状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "数据权限组ID", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "status", required = true, value = "数据权限组状态", dataType = "String", allowableValues = "1,0")
    })
    @ApiOperationLog(module = Constants.EXTRAS_PERMS_GROUP, business = Constants.Biz.EXTRAS_PERMS_GROUP_RENEW, opt = BusinessType.UPDATE)
    @GetMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms-group:status','*') ")
    @ResponseBody
    public ApiRestResponse<String> status(@Valid @NotNull(message = "数据权限组ID不能为空") @RequestParam("id") String id,
                                          @NotNull(message = "数据权限组状态不能为空") @RequestParam("status") String status) throws Exception {
        boolean result = getDataPermissionGroupService().setStatus(id, status);
        if (result) {
            return success("authz.dbperms.group.status.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.dbperms.group.status.fail", result);
    }

    @ApiOperation(value = "查询数据权限组", notes = "根据ID查询数据权限组")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "数据权限组ID", dataType = "String")
    })
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms-group:detail','*') ")
    @ResponseBody
    public ApiRestResponse<DataPermissionGroupDTO> detail(@Valid @NotNull(message = "数据权限组ID不能为空") @RequestParam("id") String id) throws Exception {
        DataPermissionGroupModel model = getDataPermissionGroupService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("authz.dbperms.group.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, DataPermissionGroupDTO.class));
    }

    public IDataPermissionGroupService getDataPermissionGroupService() {
        return dataPermissionGroupService;
    }

}