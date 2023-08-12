/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.base.dict.dao.entities.DictPairEntity;
import io.hiwepy.cloud.base.dict.service.IDictPairService;
import io.hiwepy.cloud.base.dict.setup.Constants;
import io.hiwepy.cloud.base.dict.web.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Api(tags = "数据字典：基础数据")
@RestController
@RequestMapping("/dict/keyvalue/")
@Validated
public class DictPairController extends BaseApiController {

    @Autowired
    private IDictPairService keyValueService;

    @ApiOperation(value = "分页查询基础数据", notes = "分页查询基础数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "KeyValuePaginationDTO")
    })
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('keyvalue:list','*') ")
    @ResponseBody
    public Result<DictPairDTO> list(@Valid @RequestBody DictPairPaginationDTO paginationDTO) {

        DictPairEntity model = getBeanMapper().map(paginationDTO, DictPairEntity.class);
        Page<DictPairEntity> pageResult = getKeyValueService().getPagedList(model);
        List<DictPairDTO> retList = Lists.newArrayList();
        for (DictPairEntity keyvalueModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(keyvalueModel, DictPairDTO.class));
        }

        return new Result<DictPairDTO>(pageResult, retList);

    }

    @ApiOperation(value = "根据分组查询基础数据（完整）", notes = "根据分组查询基础数据（完整）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "gkey", value = "数据字典,多个分组用,分割", required = true, dataType = "String")
    })
    @GetMapping("groups")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<Map<String, List<DictPairDTO>>> groups(@Valid @NotNull(message = "基础数据字典编码不能为空") @RequestParam String gkey) {
        Map<String, List<DictPairEntity>> pairList = getKeyValueService().getGroupPairValues(StringUtils.tokenizeToStringArray(gkey));
        Map<String, List<DictPairDTO>> reMap = new HashMap<String, List<DictPairDTO>>();
        if (CollectionUtils.isEmpty(pairList)) {
            return ApiRestResponse.success(reMap);
        }
        for (Entry<String, List<DictPairEntity>> entry : pairList.entrySet()) {
            reMap.put(entry.getKey(), entry.getValue().stream().map(source -> {
                return getBeanMapper().map(source, DictPairDTO.class);
            }).collect(Collectors.toList()));
        }
        return ApiRestResponse.success(reMap);

    }

    @ApiOperation(value = "根据分组查询基础数据（键值对）", notes = "根据分组查询基础数据（键值对）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "gkey", value = "数据字典", required = true, dataType = "String")
    })
    @GetMapping("pairs")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<PairModel>> pairs(@Valid @RequestParam @NotNull(message = "基础数据字典编码不能为空") String gkey) throws Exception {
        return ApiRestResponse.success(getKeyValueService().getPairValues(gkey));
    }

    @ApiOperation(value = "创建基础数据", notes = "增加一个新的基础数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "DTO", value = "基础数据传输对象", dataType = "KeyValueNewDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_BASEDATA, business = "创建基础数据", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('keyvalue:new','*') ")
    @ResponseBody
    public ApiRestResponse<String> keyvalue(@Valid @RequestBody DictPairNewDTO DTO) throws Exception {
        DictPairEntity model = getBeanMapper().map(DTO, DictPairEntity.class);

        Long ct = getKeyValueService().getCount(model);
        if (ct > 0) {
            return fail("keyvalue.new.conflict");
        }
        // 新增一条数据库配置记录
        boolean result = getKeyValueService().save(model);
        if (result) {
            return success("keyvalue.new.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("keyvalue.new.fail", result);
    }

    @ApiOperation(value = "删除基础数据", notes = "删除基础数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "基础数据id,多个用,拼接", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_BASEDATA, business = "删除基础数据", opt = BusinessType.UPDATE)
    @PostMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('keyvalue:delete','*') ")
    @ResponseBody
    public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
        // 执行基础数据删除操作
        List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
        boolean result = getKeyValueService().removeByIds(idList);
        if (result) {
            return success("keyvalue.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("keyvalue.delete.fail", result);
    }


    @ApiOperation(value = "更新基础数据", notes = "更新基础数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "DTO", value = "基础数据", required = true, dataType = "KeyValueRenewDTO"),
    })
    @ApiOperationLog(module = Constants.EXTRAS_BASEDATA, business = "更新基础数据", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('keyvalue:renew','*') ")
    @ResponseBody
    public ApiRestResponse<String> renew(@Valid @RequestBody DictPairRenewDTO DTO) throws Exception {
        DictPairEntity model = getBeanMapper().map(DTO, DictPairEntity.class);
        Long ct = getKeyValueService().getCount(model);
        if (ct > 0) {
            return fail("keyvalue.renew.conflict");
        }
        boolean result = getKeyValueService().updateById(model);
        if (result) {
            return success("keyvalue.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("keyvalue.renew.fail", result);
    }

    @ApiOperation(value = "更新基础数据状态", notes = "更新基础数据状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "renewDTO", value = "角色信息", required = true, dataType = "KeyValueStatusRenewDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_BASEDATA, business = "更新基础数据状态", opt = BusinessType.UPDATE)
    @PostMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('keyvalue:status','*') ")
    @ResponseBody
    public ApiRestResponse<String> status(@Valid @RequestBody DictPairStatusRenewDTO renewDTO) throws Exception {
        boolean result = getKeyValueService().setStatus(renewDTO.getId(), renewDTO.getStatus());
        if (result) {
            return success("keyvalue.status.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("keyvalue.status.fail", result);
    }

    @ApiOperation(value = "批量更新分组内的基础数据", notes = "批量更新分组内的基础数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "renewDTO", value = "基础数据集合", required = true, dataType = "KeyValueGroupRenewDTO"),
    })
    @ApiOperationLog(module = Constants.EXTRAS_BASEDATA, business = "批量更新分组内的基础数据", opt = BusinessType.UPDATE)
    @PostMapping(value = "batch/renew")
    @PreAuthorize("authenticated and hasAnyAuthority('keyvalue:renew','*') ")
    @ResponseBody
    public ApiRestResponse<String> batchRenew(@Valid @RequestBody DictGroupPairRenewDTO renewDTO) throws Exception {

        try {

            List<DictPairEntity> list = Lists.newArrayList();
            for (DictPairRenewDTO keyvalueDTO : renewDTO.getDatas()) {
                DictPairEntity model = getBeanMapper().map(keyvalueDTO, DictPairEntity.class);
                model.setGkey(renewDTO.getGkey());
                list.add(model);
            }
            // 批量执行基础数据更新操作
            getKeyValueService().updateBatchById(list);
            return success("keyvalue.renew.success");
        } catch (Exception e) {
            return fail("keyvalue.renew.fail");
        }
    }

    @ApiOperation(value = "查询基础数据信息", notes = "根据id查询基础数据信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "基础数据id", dataType = "String")
    })
    @GetMapping("detail")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<DictPairDTO> detail(@RequestParam("id") String id) throws Exception {
        DictPairEntity model = getKeyValueService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("keyvalue.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, DictPairDTO.class));
    }

    public IDictPairService getKeyValueService() {
        return keyValueService;
    }

}
