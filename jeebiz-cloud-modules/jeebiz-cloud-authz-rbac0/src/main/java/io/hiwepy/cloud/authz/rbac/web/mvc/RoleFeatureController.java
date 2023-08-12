/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.mvc;

import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiModule;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureEntity;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.cloud.authz.feature.enums.FeatureNodeType;
import io.hiwepy.cloud.authz.feature.service.IFeatureService;
import io.hiwepy.cloud.authz.feature.setup.Constants;
import io.hiwepy.cloud.authz.feature.strategy.FeatureStrategyRouter;
import io.hiwepy.cloud.authz.feature.web.dto.FeatureTreeNode;
import io.hiwepy.cloud.authz.rbac.service.IRoleFeatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "功能菜单：数据维护（Ok）")
@ApiModule(module = Constants.AUTHZ_FEATURE, business = "功能菜单")
@RestController
@RequestMapping(value = "/authorized/feature/")
public class RoleFeatureController extends BaseApiController {

    @Autowired
    private IRoleFeatureService roleFeatureService;
    @Autowired
    private IFeatureService featureService;
    @Autowired
    private FeatureStrategyRouter featureStrategyRouter;

    @ApiOperation(value = "功能菜单-树形结构数据（全部菜单数据）", notes = "查询功能菜单树形结构数据")
    @ApiOperationLog(opt = BusinessType.SELECT)
    @GetMapping("tree")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<FeatureTreeNode>> nav() {
        // 登录账号信息
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        // 所有的功能菜单
        Long roleId = Long.parseLong(principal.getRid());
        List<FeatureEntity> featureList = getRoleFeatureService().getFeatures(roleId);
        // 返回各级菜单 + 对应的功能权限数据
        return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.TREE).handle(featureList));
    }

    @ApiOperation(value = "功能菜单-树形结构数据（当前登录用户）", notes = "根据服务id及等登录人信息查询该服务的功能菜单-树形结构数据")
    @ApiOperationLog(opt = BusinessType.SELECT)
    @GetMapping("tree-opts")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<FeatureTreeNode>> tree() {
        // 登录账号信息
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        // 所有的功能菜单
        Long roleId = Long.parseLong(principal.getRid());
        List<FeatureEntity> featureList = getRoleFeatureService().getFeatures(roleId);
        // 所有的功能菜单
        List<FeatureOptEntity> featureOptList = getRoleFeatureService().getFeatureOpts(roleId);
        // 返回各级菜单 + 对应的功能权限数据
        return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.TREE).handle(featureList, featureOptList));
    }

    @ApiOperation(value = "功能菜单-树形结构数据（指定角色）", notes = "根据角色id查询角色拥有的功能菜单-树形结构数据")
    @GetMapping("treeForRole")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<FeatureTreeNode>> tree(@RequestParam("roleId") Long roleId) {
        // 所有的功能菜单
        List<FeatureEntity> featureList = getFeatureService().getFeatureList();
        // 所有的功能操作按钮：标记按钮选中状态
        List<FeatureOptEntity> featureOptList = getRoleFeatureService().getFeatureOpts(roleId);
        // 返回各级菜单 + 对应的功能权限数据
        return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.TREE).handle(featureList, featureOptList));
    }

    @ApiOperation(value = "指定功能菜单-树形结构数据（当前登录用户）", notes = "根据功能菜单id及等登录人信息查询该功能菜单的子功能菜单-树形结构数据")
    @GetMapping("children")
    @PreAuthorize("authenticated")
    public ApiRestResponse<FeatureTreeNode> children(@RequestParam Long featureId) {
        // 登录账号信息
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        // 所有的功能菜单
        FeatureTreeNode featureTree = getRoleFeatureService().getChildFeatures(Long.parseLong(principal.getRid()), featureId);
        // 返回各级菜单 + 对应的功能权限数据
        return ApiRestResponse.success(featureTree);
    }

    @ApiOperation(value = "功能菜单-扁平结构数据（当前登录用户）", notes = "根据服务id及等登录人信息查询该服务的功能菜单-树形结构数据")
    @GetMapping("flat")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<FeatureTreeNode>> flat() {
        // 登录账号信息
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        // 所有的功能菜单
        List<FeatureEntity> featureList = getRoleFeatureService().getFeatures(Long.parseLong(principal.getRid()));
        // 返回各级菜单 + 对应的功能权限数据
        return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.FLAT).handle(featureList));
    }

    @ApiOperation(value = "功能菜单-扁平结构数据（指定角色）", notes = "根据角色id查询角色拥有的功能菜单-扁平结构数据")
    @GetMapping("flatForRole")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<FeatureTreeNode>> flat(@RequestParam("roleId") Long roleId) {
        // 所有的功能菜单
        List<FeatureEntity> featureList = getFeatureService().getFeatureList();
        // 所有的功能操作按钮：标记按钮选中状态
        List<FeatureOptEntity> featureOptList = getRoleFeatureService().getFeatureOpts(roleId);
        // 返回叶子节点菜单 + 对应的功能权限数据
        return ApiRestResponse.success(featureStrategyRouter.routeFor(FeatureNodeType.FLAT).handle(featureList, featureOptList));
    }

    public IRoleFeatureService getRoleFeatureService() {
        return roleFeatureService;
    }

    public IFeatureService getFeatureService() {
        return featureService;
    }

}
