/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.web.mvc;

import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionSpecialCheckedModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionSpecialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "数据权限专项：特殊数据权限数据查询")
@RestController
@RequestMapping("/authz/dbperms/special")
@Validated
public class DataPermissionSpecialController extends BaseApiController {

    @Autowired
    private IDataPermissionSpecialService dataPermissionSpecialService;

    @ApiOperation(value = "数据权限专项列表（键值对）", notes = "数据权限专项列表（键值对）")
    @GetMapping("pairs")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<PairModel>> pairs() {
        return ApiRestResponse.success(getDataPermissionSpecialService().getPairList());
    }

    @ApiOperation(value = "角色特殊权限列表（键值对）", notes = "角色特殊权限组列表（键值对）")
    @GetMapping("role/pairs")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<DataPermissionSpecialCheckedModel>> rolePairs(@Valid @NotNull(message = "角色ID不能为空") @RequestParam("rid") String rid) {
        return ApiRestResponse.success(getDataPermissionSpecialService().getSpecialRolePairs(rid));
    }

    @ApiOperation(value = "用户特殊权限组列表（键值对）", notes = "用户特殊权限组列表（键值对）")
    @GetMapping("user/pairs")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<DataPermissionSpecialCheckedModel>> userPairs(@Valid @NotNull(message = "用户ID不能为空") @RequestParam("uid") String uid) {
        return ApiRestResponse.success(getDataPermissionSpecialService().getSpecialUserPairs(uid));
    }

    public IDataPermissionSpecialService getDataPermissionSpecialService() {
        return dataPermissionSpecialService;
    }
}