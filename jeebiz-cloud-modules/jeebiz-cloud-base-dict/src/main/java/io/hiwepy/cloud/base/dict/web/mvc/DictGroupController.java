/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.AllowableValues;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.base.dict.dao.entities.DictGroupEntity;
import io.hiwepy.cloud.base.dict.service.IDictGroupService;
import io.hiwepy.cloud.base.dict.setup.Constants;
import io.hiwepy.cloud.base.dict.web.dto.DictGroupDTO;
import io.hiwepy.cloud.base.dict.web.dto.DictGroupNewDTO;
import io.hiwepy.cloud.base.dict.web.dto.DictGroupPaginationDTO;
import io.hiwepy.cloud.base.dict.web.dto.DictGroupRenewDTO;
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
import java.util.List;

@Api(tags = "数据字典：数据字典")
@RestController
@RequestMapping("/dict/group/")
@Validated
public class DictGroupController extends BaseApiController {

    @Autowired
    private IDictGroupService keyGroupService;

    @ApiOperation(value = "分页查询基础数据字典", notes = "分页查询基础数据字典")
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('keygroup:list','*') ")
    @ResponseBody
    public Result<DictGroupDTO> list(@Valid @RequestBody DictGroupPaginationDTO paginationDTO) {

        DictGroupEntity model = getBeanMapper().map(paginationDTO, DictGroupEntity.class);
        Page<DictGroupEntity> pageResult = getKeyGroupService().getPagedList(model);
        List<DictGroupDTO> retList = Lists.newArrayList();
        for (DictGroupEntity keygroupModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(keygroupModel, DictGroupDTO.class));
        }

        return new Result<DictGroupDTO>(pageResult, retList);

    }

    @ApiOperation(value = "数据字典（下拉数据）", notes = "查询基础数据字典集合")
    @GetMapping("pairs")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<DictGroupDTO>> groups() throws Exception {
        List<DictGroupEntity> records = getKeyGroupService().getKeyGroupList();
        List<DictGroupDTO> retList = Lists.newArrayList();
        for (DictGroupEntity groupModel : records) {
            retList.add(getBeanMapper().map(groupModel, DictGroupDTO.class));
        }
        return ApiRestResponse.success(retList);
    }

    @ApiOperation(value = "创建基础数据字典", notes = "增加一个新的基础数据字典")
    @ApiOperationLog(module = Constants.EXTRAS_BASEDATA, business = "创建基础数据字典", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('keygroup:new','*') ")
    @ResponseBody
    public ApiRestResponse<String> keygroup(@Valid @RequestBody DictGroupNewDTO dto) throws Exception {

        // 检查编码是否存在
        Long ct = getKeyGroupService().getCountByCode(dto.getKey(), null);
        if (ct > 0) {
            return fail("keygroup.new.key.conflict");
        }
        // 检查名称是否存在
        ct = getKeyGroupService().getCountByName(dto.getValue(), null);
        if (ct > 0) {
            return fail("keygroup.new.value.conflict");
        }

        // 新增一条数据库配置记录
        DictGroupEntity model = getBeanMapper().map(dto, DictGroupEntity.class);
        boolean result = getKeyGroupService().save(model);
        if (result) {
            return success("keygroup.new.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("keygroup.new.fail", result);
    }

    @ApiOperation(value = "删除基础数据字典", notes = "删除基础数据字典")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "基础数据字典id,多个用,拼接", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_BASEDATA, business = "删除基础数据字典", opt = BusinessType.UPDATE)
    @GetMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('keygroup:delete','*') ")
    @ResponseBody
    public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
        // 执行基础数据字典删除操作
        List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
        boolean result = getKeyGroupService().removeByIds(idList);
        if (result) {
            return success("keygroup.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("keygroup.delete.fail", result);
    }

    @ApiOperation(value = "更新基础数据字典", notes = "更新基础数据字典")
    @ApiOperationLog(module = Constants.EXTRAS_BASEDATA, business = "更新基础数据字典", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('keygroup:renew','*') ")
    @ResponseBody
    public ApiRestResponse<String> renew(@Valid @RequestBody DictGroupRenewDTO dto) throws Exception {

        // 检查编码是否存在
        Long ct = getKeyGroupService().getCountByCode(dto.getKey(), dto.getId());
        if (ct > 0) {
            return fail("keygroup.renew.key.conflict");
        }
        // 检查名称是否存在
        ct = getKeyGroupService().getCountByName(dto.getValue(), dto.getId());
        if (ct > 0) {
            return fail("keygroup.renew.value.conflict");
        }

        DictGroupEntity model = getBeanMapper().map(dto, DictGroupEntity.class);
        boolean result = getKeyGroupService().updateById(model);
        if (result) {
            return success("keygroup.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("keygroup.renew.fail", result);
    }

    @ApiOperation(value = "更新基础数据字典状态", notes = "更新基础数据字典状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "基础数据字典id", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "status", required = true, value = "基础数据字典状态", dataType = "String", allowableValues = "1,0")
    })
    @ApiOperationLog(module = Constants.EXTRAS_BASEDATA, business = "更新基础数据字典状态", opt = BusinessType.UPDATE)
    @GetMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('keygroup:status','*') ")
    @ResponseBody
    public ApiRestResponse<String> status(@RequestParam String id, @AllowableValues(allows = "0,1", message = "数据状态错误") @RequestParam String status) throws Exception {
        boolean result = getKeyGroupService().setStatus(id, status);
        if (result) {
            return success("keygroup.status.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("keygroup.status.fail", result);
    }

    @ApiOperation(value = "查询基础数据字典信息", notes = "根据id查询基础数据字典信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "基础数据字典id", dataType = "String")
    })
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('keygroup:detail','*') ")
    @ResponseBody
    public ApiRestResponse<DictGroupDTO> detail(@RequestParam("id") String id) throws Exception {
        DictGroupEntity model = getKeyGroupService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("keygroup.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, DictGroupDTO.class));
    }

    public IDictGroupService getKeyGroupService() {
        return keyGroupService;
    }

}
