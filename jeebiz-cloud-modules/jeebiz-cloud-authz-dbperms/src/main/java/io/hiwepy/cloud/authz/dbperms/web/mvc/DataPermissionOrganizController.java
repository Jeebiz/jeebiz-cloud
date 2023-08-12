/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.web.mvc;

import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionOrganizModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionOrganizService;
import io.hiwepy.cloud.authz.dbperms.web.dto.DataPermissionOrganizDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "数据权限：数据权限数据树")
@RestController
@RequestMapping("/authz/dbperms/tree")
@Validated
public class DataPermissionOrganizController extends BaseApiController {

    @Autowired
    private IDataPermissionOrganizService dataPermissionOrganizService;

    @ApiOperation(value = "查询角色授权数据权限树", notes = "查询角色授权数据权限树")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "rid", required = true, value = "角色ID", dataType = "String")
    })
    @GetMapping("role")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms:list','*') ")
    @ResponseBody
    public ApiRestResponse<List<DataPermissionOrganizDTO>> tree1(@Valid @NotNull(message = "角色ID不能为空") @RequestParam("rid") String rid) {
        List<DataPermissionOrganizModel> organizList = getDataPermissionOrganizService().getOrganizRoleTree(rid);
        List<DataPermissionOrganizDTO> retList = new ArrayList<>();
        for (DataPermissionOrganizModel organizModel : organizList) {
            retList.add(getBeanMapper().map(organizModel, DataPermissionOrganizDTO.class));
        }
        return ApiRestResponse.success(retList);
    }

    @ApiOperation(value = "查询用户授权数据权限树", notes = "查询用户授权数据权限树")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", required = true, value = "用户ID", dataType = "String")
    })
    @GetMapping("user")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms:list','*') ")
    @ResponseBody
    public ApiRestResponse<List<DataPermissionOrganizDTO>> tree2(@Valid @NotNull(message = "用户ID不能为空") @RequestParam("uid") String uid) {
        List<DataPermissionOrganizModel> organizList = getDataPermissionOrganizService().getOrganizUserTree(uid);
        List<DataPermissionOrganizDTO> retList = new ArrayList<>();
        for (DataPermissionOrganizModel organizModel : organizList) {
            retList.add(getBeanMapper().map(organizModel, DataPermissionOrganizDTO.class));
        }
        return ApiRestResponse.success(retList);
    }

    @ApiOperation(value = "查询数据权限规则授权数据权限树", notes = "查询数据权限规则授权数据权限树")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pid", required = true, value = "数据权限规则ID", dataType = "String")
    })
    @GetMapping("rule")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms:list','*') ")
    @ResponseBody
    public ApiRestResponse<List<DataPermissionOrganizDTO>> tree3(@Valid @NotNull(message = "数据权限规则ID不能为空") @RequestParam("pid") String pid) {
        List<DataPermissionOrganizModel> organizList = getDataPermissionOrganizService().getOrganizRuleTree(pid);
        List<DataPermissionOrganizDTO> retList = new ArrayList<>();
        for (DataPermissionOrganizModel organizModel : organizList) {
            retList.add(getBeanMapper().map(organizModel, DataPermissionOrganizDTO.class));
        }
        return ApiRestResponse.success(retList);
    }

    public IDataPermissionOrganizService getDataPermissionOrganizService() {
        return dataPermissionOrganizService;
    }

}