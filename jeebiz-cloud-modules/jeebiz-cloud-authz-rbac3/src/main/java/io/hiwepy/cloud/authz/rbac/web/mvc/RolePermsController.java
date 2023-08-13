/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.mvc;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.cloud.authz.rbac.service.IRolePermsService;
import io.hiwepy.cloud.authz.rbac.setup.Constants;
import io.hiwepy.cloud.authz.rbac.web.dto.RoleAllotPermsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 权限管理：角色功能权限
 */
@Api(tags = "权限管理：角色功能权限（Ok）")
@RestController
@RequestMapping(value = "/authz/role/perms/")
public class RolePermsController extends BaseMapperController {

    /**
     * 角色权限管理SERVICE
     */
    @Autowired
    private IRolePermsService rolePermsService;

    @ApiOperation(value = "指定角色已授权功能列表（键值对）", notes = "查询指定角色已授权功能列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", required = true, value = "角色id", dataType = "String")
    })
    @GetMapping("pairs")
    @PreAuthorize("authenticated and hasAnyAuthority('role:perms','*') ")
    public ApiRestResponse<List<PairModel>> list(@RequestParam String roleId) throws Exception {
        List<PairModel> roleList = getRolePermsService().getPairValues(roleId);
        if (roleList == null) {
            roleList = Lists.newArrayList();
        }
        return ApiRestResponse.success(roleList);
    }

    @ApiOperation(value = "给指定角色分配功能权限", notes = "给指定角色分配功能权限")
    @ApiOperationLog(module = Constants.AUTHZ_ROLE_PERMS, business = "给指定角色分配权限，角色Id：${roleid}", opt = BusinessType.DELETE)
    @PostMapping("perms")
    @PreAuthorize("authenticated and hasAnyAuthority('role:perms','*') ")
    public ApiRestResponse<String> perms(@Valid @RequestBody RoleAllotPermsDTO permsDTO) throws Exception {
        boolean result = getRolePermsService().doPerms(permsDTO.getRoleId(), permsDTO.getPerms());
        if (result) {
            return success("role.perms.success");
        }
        return fail("role.perms.fail");
    }

    @ApiOperation(value = "取消已分配给指定角色的权限", notes = "取消已分配给指定角色的权限")
    @ApiOperationLog(module = Constants.AUTHZ_ROLE_PERMS, business = "取消已分配给指定角色的权限", opt = BusinessType.DELETE)
    @PostMapping("unperms")
    @PreAuthorize("authenticated and hasAnyAuthority('role:unperms','*') ")
    public ApiRestResponse<String> unperms(@Valid @RequestBody RoleAllotPermsDTO permsDTO) throws Exception {
        boolean result = getRolePermsService().unPerms(permsDTO.getRoleId(), permsDTO.getPerms());
        if (result) {
            return success("role.unperms.success");
        }
        return fail("role.unperms.fail");
    }

    public IRolePermsService getRolePermsService() {
        return rolePermsService;
    }
}
