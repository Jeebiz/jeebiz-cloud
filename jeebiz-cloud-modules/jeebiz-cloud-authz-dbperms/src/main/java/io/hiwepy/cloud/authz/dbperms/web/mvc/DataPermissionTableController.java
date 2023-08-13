/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.web.mvc;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionTableColumnModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionTableService;
import io.hiwepy.cloud.authz.dbperms.web.dto.DataPermissionTableDataPaginationDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.mybatis.dbperms.annotation.Condition;
import org.apache.mybatis.dbperms.annotation.Relational;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Api(tags = "数据权限对象：数据权限数据来源查询")
@RestController
@RequestMapping("/authz/dbperms/table")
@Validated
public class DataPermissionTableController extends BaseApiController {

    @Autowired
    private IDataPermissionTableService dataPermissionTargetService;

    @ApiOperation(value = "数据权限对象列表（键值对）", notes = "数据权限对象列表（键值对）")
    @GetMapping("tables")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<PairModel>> tables() {
        return ApiRestResponse.success(getDataPermissionTargetService().getPairList());
    }

    @ApiOperation(value = "数据权限对象列字段（键值对）", notes = "数据权限对象列字段（键值对）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", required = true, value = "数据权限对象ID", dataType = "String")
    })
    @GetMapping("columns")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<PairModel>> columns(@Valid @NotNull(message = "数据权限对象ID不能为空") @RequestParam("tid") String tid) throws Exception {
        List<PairModel> roleList = getDataPermissionTargetService().getPairValues(tid);
        if (roleList == null) {
            roleList = Lists.newArrayList();
        }
        return ApiRestResponse.success(roleList);
    }

    @ApiOperation(value = "数据权限限制条件（键值对）", notes = "数据权限限制条件（键值对）")
    @GetMapping("conditions")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<Map<String, String>>> conditions() {
        return ApiRestResponse.success(Condition.toList());
    }

    @ApiOperation(value = "数据权限关联条件（键值对）", notes = "数据权限关联条件（键值对）")
    @GetMapping("relations")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<Map<String, String>>> relations() {
        return ApiRestResponse.success(Relational.toList());
    }

    @ApiOperation(value = "分页查询数据权限对象可用权限数据", notes = "分页查询数据权限对象可用权限数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "筛选条件", dataType = "DataPermissionTargetPaginationDTO")
    })
    @PostMapping("list")
    @PreAuthorize("authenticated")
    @ResponseBody
    public Result<PairModel> list(@Valid @RequestBody DataPermissionTableDataPaginationDTO paginationDTO) {
        DataPermissionTableColumnModel model = getBeanMapper().map(paginationDTO, DataPermissionTableColumnModel.class);
        return new Result<PairModel>(getDataPermissionTargetService().getDataList(model));
    }

    public IDataPermissionTableService getDataPermissionTargetService() {
        return dataPermissionTargetService;
    }
}