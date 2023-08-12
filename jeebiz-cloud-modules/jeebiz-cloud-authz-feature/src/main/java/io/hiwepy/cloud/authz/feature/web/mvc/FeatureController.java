/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.feature.web.mvc;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureEntity;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.cloud.authz.feature.enums.FeatureNodeType;
import io.hiwepy.cloud.authz.feature.service.IFeatureOptService;
import io.hiwepy.cloud.authz.feature.service.IFeatureService;
import io.hiwepy.cloud.authz.feature.setup.Constants;
import io.hiwepy.cloud.authz.feature.strategy.FeatureStrategyRouter;
import io.hiwepy.cloud.authz.feature.web.dto.FeatureDTO;
import io.hiwepy.cloud.authz.feature.web.dto.FeatureNewDTO;
import io.hiwepy.cloud.authz.feature.web.dto.FeatureRenewDTO;
import io.hiwepy.cloud.authz.feature.web.dto.FeatureTreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "功能菜单：数据维护（Ok）")
@RestController
@RequestMapping("feature")
public class FeatureController extends BaseMapperController {

    @Autowired
    private IFeatureService featureService;
    @Autowired
    private IFeatureOptService featureOptService;
    @Autowired
    private FeatureStrategyRouter featureStrategyRouter;
    @Autowired
    private RedisOperationTemplate redisOperation;

    @ApiOperation(value = "功能菜单（全部数据）", notes = "查询功能菜单列表")
    @GetMapping("list")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<FeatureDTO>> list() {
        // 所有的功能菜单
        List<FeatureEntity> featureList = getFeatureService().getFeatureList();
        List<FeatureDTO> featureDTOList = Lists.newArrayList();
        for (FeatureEntity model : featureList) {
            featureDTOList.add(getBeanMapper().map(model, FeatureDTO.class));
        }
        return ApiRestResponse.success(featureDTOList);
    }

    @ApiOperation(value = "功能菜单-树形结构数据（全部菜单数据）", notes = "查询功能菜单树形结构数据")
    @ApiOperationLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
    @GetMapping("nav")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<FeatureTreeNode>> nav() {
        // 所有的功能菜单
        List<FeatureEntity> featureList = getFeatureService().getFeatureList();
        // 返回各级菜单 + 对应的功能权限数据
        return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.TREE).handle(featureList));
    }

    @ApiOperation(value = "功能菜单-树形结构数据（包含功能操作按钮）", notes = "查询功能菜单树形结构数据")
    @ApiOperationLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单树形结构数据", opt = BusinessType.SELECT)
    @GetMapping("tree")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<FeatureTreeNode>> tree() {
        // 所有的功能菜单
        List<FeatureEntity> featureList = getFeatureService().getFeatureList();
        // 所有的功能操作按钮
        List<FeatureOptEntity> featureOptList = getFeatureOptService().getFeatureOptList();
        // 返回各级菜单 + 对应的功能权限数据
        return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.TREE).handle(featureList, featureOptList));
        //return ResultUtils.dataMap(FeatureDataHandlerFactory.getTreeHandler().handle(featureList, featureOptList));
    }

    @ApiOperation(value = "功能菜单-扁平结构数据（包含功能操作按钮）", notes = "查询功能菜单扁平结构数据")
    @ApiOperationLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单扁平结构数据", opt = BusinessType.SELECT)
    @PostMapping("flat")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<FeatureTreeNode>> flat() {
        // 所有的功能菜单
        List<FeatureEntity> featureList = getFeatureService().getFeatureList();
        // 所有的功能操作按钮
        List<FeatureOptEntity> featureOptList = getFeatureOptService().getFeatureOptList();

        // 返回各级菜单 + 对应的功能权限数据
        return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.FLAT).handle(featureList, featureOptList));
    }

    @ApiOperation(value = "增加功能菜单信息", notes = "增加功能菜单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "featureDTO", value = "功能菜单信息", dataType = "FeatureNewDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_FEATURE, business = "新增功能菜单-名称：${name}", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('feature:new','*') ")
    public ApiRestResponse<String> feature(@Valid @RequestBody FeatureNewDTO featureDTO) throws Exception {
        Long count = getFeatureService().getCountByCode(featureDTO.getCode(), null);
        if (count > 0) {
            return fail("feature.new.code-exists");
        }
        FeatureEntity model = getBeanMapper().map(featureDTO, FeatureEntity.class);
        /**
         * 菜单类型(1:原生|2:自定义)
         */
        model.setType("2");
        boolean total = getFeatureService().save(model);
        if (total) {
            // 删除菜单缓存
            redisOperation.del(Constants.AUTHZ_FEATURE_CACHE);
            return success("feature.new.success", total);
        }
        return fail("feature.new.fail", total);
    }

    @ApiOperation(value = "修改功能菜单信息", notes = "修改功能菜单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "featureDTO", value = "功能菜单信息", dataType = "FeatureRenewDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_FEATURE, business = "修改功能菜单-名称：${name}", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('feature:renew','*') ")
    public ApiRestResponse<String> renew(@Valid @RequestBody FeatureRenewDTO featureDTO) throws Exception {
        FeatureEntity model = getBeanMapper().map(featureDTO, FeatureEntity.class);
        boolean total = getFeatureService().updateById(model);
        if (total) {
            // 删除菜单缓存
            redisOperation.del(Constants.AUTHZ_FEATURE_CACHE);
            return success("feature.renew.success", total);
        }
        return fail("feature.renew.fail", total);
    }

    @ApiOperation(value = "查询功能菜单信息", notes = "根据功能菜单id查询功能菜单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "功能菜单id", dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_FEATURE, business = "查询功能菜单-id：${featureid}", opt = BusinessType.SELECT)
    @PostMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('feature:detail','*') ")
    public ApiRestResponse<FeatureDTO> detail(@RequestParam("id") String id) throws Exception {
        FeatureEntity model = getFeatureService().getFeature(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("feature.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, FeatureDTO.class));
    }

    @ApiOperation(value = "删除功能菜单信息", notes = "删除功能菜单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", required = true, value = "功能菜单id", dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_FEATURE, business = "删除功能菜单-名称：${featureid}", opt = BusinessType.DELETE)
    @PostMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('feature:delete','*') ")
    public ApiRestResponse<String> delFeature(@RequestParam String id) throws Exception {

        Long count = getFeatureService().getCountByParent(id);
        if (count > 0) {
            return fail("feature.delete.child-exists");
        }

        boolean total = getFeatureService().removeById(id);
        if (total) {
            // 删除菜单缓存
            redisOperation.del(Constants.AUTHZ_FEATURE_CACHE);
            return success("feature.delete.success", total);
        }
        return fail("feature.delete.fail", total);
    }

    public IFeatureService getFeatureService() {
        return featureService;
    }

    public void setFeatureService(IFeatureService featureService) {
        this.featureService = featureService;
    }

    public IFeatureOptService getFeatureOptService() {
        return featureOptService;
    }

}
