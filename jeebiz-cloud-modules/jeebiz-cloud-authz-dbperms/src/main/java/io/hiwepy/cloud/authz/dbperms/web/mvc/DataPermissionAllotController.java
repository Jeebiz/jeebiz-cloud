/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.web.mvc;

import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionAllotModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionAllotService;
import io.hiwepy.cloud.authz.dbperms.setup.Constants;
import io.hiwepy.cloud.authz.dbperms.web.dto.DataPermissionAllotRoleDTO;
import io.hiwepy.cloud.authz.dbperms.web.dto.DataPermissionAllotUserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "数据权限：角色数据授权,用户数据授权")
@RestController
@RequestMapping("/authz/dbperms/allot")
public class DataPermissionAllotController extends BaseApiController {

    @Autowired
    private IDataPermissionAllotService dataPermissionAllotService;

    @ApiOperation(value = "角色数据授权", notes = "给角色指定数据权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "allotDTO", value = "角色数据授权参数传输对象", dataType = "DataPermissionAllotRoleDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_ALLOT, business = Constants.Biz.EXTRAS_ALLOT_ROLE, opt = BusinessType.INSERT)
    @PostMapping("role")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms:role-allot','*') ")
    @ResponseBody
    public ApiRestResponse<String> role(@Valid @RequestBody DataPermissionAllotRoleDTO allotDTO) throws Exception {
        DataPermissionAllotModel model = getBeanMapper().map(allotDTO, DataPermissionAllotModel.class);
        int result = getDataPermissionAllotService().allot(model);
        if (result > 0) {
            return success("authz.dbperms.allot.role.success", result);
        }
        return fail("authz.dbperms.allot.role.fail", result);
    }

    @ApiOperation(value = "用户数据授权", notes = "给用户指定数据权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "allotDTO", value = "用户数据授权参数传输对象", required = true, dataType = "DataPermissionAllotUserDTO"),
    })
    @ApiOperationLog(module = Constants.EXTRAS_ALLOT, business = Constants.Biz.EXTRAS_ALLOT_USER, opt = BusinessType.UPDATE)
    @PostMapping("user")
    @PreAuthorize("authenticated and hasAnyAuthority('authz-dbperms:user-allot','*') ")
    @ResponseBody
    public ApiRestResponse<String> user(@Valid @RequestBody DataPermissionAllotUserDTO allotDTO) throws Exception {
        DataPermissionAllotModel model = getBeanMapper().map(allotDTO, DataPermissionAllotModel.class);
        int result = getDataPermissionAllotService().allot(model);
        if (result > 0) {
            return success("authz.dbperms.allot.user.success", result);
        }
        return fail("authz.dbperms.allot.user.fail", result);
    }

    public IDataPermissionAllotService getDataPermissionAllotService() {
        return dataPermissionAllotService;
    }
}