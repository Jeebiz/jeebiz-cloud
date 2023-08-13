/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hitool.core.lang3.RandomString;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.authz.rbac.dao.entities.RoleEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserAccountEntity;
import io.hiwepy.cloud.authz.rbac.service.IRoleService;
import io.hiwepy.cloud.authz.rbac.service.IUserAccountService;
import io.hiwepy.cloud.authz.rbac.setup.Constants;
import io.hiwepy.cloud.authz.rbac.web.dto.*;
import io.hiwepy.cloud.authz.rbac.web.vo.RoleUserVO;
import io.hiwepy.cloud.authz.rbac.web.vo.RoleVO;
import io.hiwepy.cloud.authz.rbac.web.vo.UserAccountVO;
import io.hiwepy.cloud.authz.rbac.web.vo.UserAllocatedVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限管理：角色管理
 */
@Api(tags = "权限管理：角色管理（Ok）")
@Validated
@RestController
@RequestMapping(value = "/authz/role/")
public class RoleController extends BaseApiController {

    protected RandomString randomString = new RandomString(20);

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserAccountService userAccountService;

    @ApiOperation(value = "可用角色信息列表（键值对）", notes = "获取当前所有可用的角色信息")
    @ApiOperationLog(opt = BusinessType.SELECT)
    @GetMapping("pairs")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<PairModel>> list() throws Exception {
        List<PairModel> roleList = getRoleService().getPairList();
        if (roleList == null) {
            return ApiRestResponse.fail(getMessage("role.get.empty"));
        }
        return ApiRestResponse.success(roleList);
    }

    @ApiOperation(value = "可用角色信息列表（对象属性）", notes = "查询全部可用角色信息")
    @GetMapping("roles")
    @PreAuthorize("authenticated and hasAnyAuthority('role:list','*') ")
    public ApiRestResponse<List<RoleVO>> roles() {
        List<RoleEntity> roles = getRoleService().getRoleList();
        List<RoleVO> retList = new ArrayList<RoleVO>();
        for (RoleEntity roleModel : roles) {
            RoleVO roleVO = getBeanMapper().map(roleModel, RoleVO.class);
            retList.add(roleVO);
        }
        return ApiRestResponse.success(retList);
    }

    @ApiOperation(value = "分页查询角色信息", notes = "分页查询角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "角色信息筛选条件", dataType = "RolePaginationDTO")
    })
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('role:list','*') ")
    public Result<RoleVO> list(@Valid @RequestBody RolePaginationDTO paginationDTO) {

        RoleEntity model = getBeanMapper().map(paginationDTO, RoleEntity.class);
        Page<RoleEntity> pageResult = getRoleService().getPagedList(model);
        List<RoleVO> retList = new ArrayList<RoleVO>();
        for (RoleEntity roleModel : pageResult.getRecords()) {
            RoleVO roleVO = getBeanMapper().map(roleModel, RoleVO.class);
            retList.add(roleVO);
        }

        return new Result<>(pageResult, retList);
    }

    @ApiOperation(value = "增加角色信息", notes = "增加角色信息")
    @ApiOperationLog(module = Constants.AUTHZ_ROLE, business = "新增角色-名称：${name}", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('role:new','*') ")
    public ApiRestResponse<String> newRole(@Valid @RequestBody RoleNewDTO roleDTO) throws Exception {
		/*
		if(CollectionUtils.isEmpty(roleDTO.getPerms())) {
			return fail("role.new.need-perms");
		}*/
        Long total = getRoleService().getCountByName(roleDTO.getName(), null);
        if (total > 0) {
            return fail("role.new.exists");
        }
        RoleEntity model = getBeanMapper().map(roleDTO, RoleEntity.class);
        // 角色编码
        model.setKey(RandomStringUtils.random(10, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
        // 角色类型（1:原生|2:继承|3:复制|4:自定义）
        model.setType("4");
        boolean result = getRoleService().save(model);
        if (result) {
            return success("role.new.success", result);
        }
        return fail("role.new.fail");
    }

    @ApiOperation(value = "修改角色信息", notes = "修改角色信息")
    @ApiOperationLog(module = Constants.AUTHZ_ROLE, business = "修改角色-名称：${name}", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('role:renew','*') ")
    public ApiRestResponse<String> renew(@Valid @RequestBody RoleRenewDTO roleDTO) throws Exception {
		/*
		if(CollectionUtils.isEmpty(roleDTO.getPerms())) {
			return fail("role.renew.need-perms");
		}*/
        Long total = getRoleService().getCountByName(roleDTO.getName(), roleDTO.getId());
        if (total > 0) {
            return fail("role.renew.exists");
        }
        RoleEntity model = getBeanMapper().map(roleDTO, RoleEntity.class);
        boolean result = getRoleService().updateById(model);
        if (result) {
            return success("role.renew.success");
        }
        return fail("role.renew.fail");
    }

    @ApiOperation(value = "更新角色状态", notes = "更新角色状态")
    @ApiOperationLog(module = Constants.AUTHZ_ROLE, business = "更新角色状态", opt = BusinessType.UPDATE)
    @PostMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('role:status','*') ")
    public ApiRestResponse<String> status(@Valid @RequestBody RoleStatusRenewDTO renewDTO) throws Exception {
        boolean result = getRoleService().setStatus(renewDTO.getId(), renewDTO.getStatus());
        if (result) {
            return success("role.status.success");
        }
        return fail("role.status.fail");
    }

    @ApiOperation(value = "角色信息详情", notes = "根据角色id查询角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "角色id", dataType = "String")
    })
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('role:detail','*') ")
    public ApiRestResponse<RoleVO> detail(@RequestParam("id") String id) throws Exception {
        RoleEntity model = getRoleService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("role.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, RoleVO.class));
    }

    @ApiOperation(value = "删除角色信息", notes = "删除角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "角色id", dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_ROLE, business = "删除角色-名称：${roleid}", opt = BusinessType.DELETE)
    @GetMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('role:delete','*') ")
    public ApiRestResponse<String> delRole(@RequestParam("id") String id) throws Exception {
        boolean result = getRoleService().removeById(id);
        if (result) {
            return success("role.delete.success");
        }
        return fail("role.delete.fail");
    }

    @ApiOperation(value = "批量删除角色信息", notes = "批量删除角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "ids", required = true, value = "角色id", dataType = "java.util.List<String>")
    })
    @ApiOperationLog(module = Constants.AUTHZ_ROLE, business = "批量删除角色信息", opt = BusinessType.DELETE)
    @PostMapping("deleteByIds")
    @PreAuthorize("authenticated and hasAnyAuthority('role:delete','*') ")
    public ApiRestResponse<String> deleteByIds(@RequestBody List<String> ids) throws Exception {
        if (!CollectionUtils.isEmpty(ids)) {
            boolean total = getRoleService().removeByIds(ids);
            if (total) {
                return success("role.delete.success", total);
            }
            return fail("role.delete.fail", total);
        }
        return success("role.delete.success");
    }

    @ApiOperation(value = "角色已分配用户查询", notes = "分页查询角色已分配用户信息")
    @ApiOperationLog(module = Constants.AUTHZ_ROLE, business = "分页查询角色已分配用户信息,角色Id：${roleid}", opt = BusinessType.DELETE)
    @PostMapping("allocated")
    @PreAuthorize("authenticated and hasAnyAuthority('role:allocated','*') ")
    public Result<UserAllocatedVO> allocated(@Valid @RequestBody RoleAllotUserPaginationDTO paginationDTO) {
        Page<UserAllocatedVO> pageResult = getRoleService().getPagedAllocatedList(paginationDTO);
        return new Result<>(pageResult, pageResult.getRecords());
    }

    @ApiOperation(value = "角色未分配用户查询", notes = "分页查询角色未分配用户信息")
    @ApiOperationLog(module = Constants.AUTHZ_ROLE, business = "分页查询角色未分配用户信息,角色Id：${roleid}", opt = BusinessType.DELETE)
    @PostMapping("unallocated")
    @PreAuthorize("authenticated and hasAnyAuthority('role:unallocated','*') ")
    public Result<UserAllocatedVO> unallocated(@Valid @RequestBody RoleAllotUserPaginationDTO paginationDTO) {
        Page<UserAllocatedVO> pageResult = getRoleService().getPagedUnAllocatedList(paginationDTO);
        return new Result<>(pageResult, pageResult.getRecords());
    }

    @ApiOperation(value = "给指定角色分配用户", notes = "给指定角色分配用户")
    @ApiOperationLog(module = Constants.AUTHZ_ROLE, business = "给指定角色分配用户，角色Id：${roleid}", opt = BusinessType.DELETE)
    @PostMapping("allot")
    @PreAuthorize("authenticated and hasAnyAuthority('role:allot','*') ")
    public ApiRestResponse<String> allot(@Valid @RequestBody RoleAllotUserDTO allotDTO) throws Exception {
        boolean result = getRoleService().doAllot(allotDTO);
        if (result) {
            return success("role.allot.success");
        }
        return fail("role.allot.fail");
    }

    @ApiOperation(value = "取消已分配给指定角色的用户", notes = "取消已分配给指定角色的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "allotDTO", value = "角色取消分配的用户信息", dataType = "RoleAllotUserDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_ROLE, business = "取消已分配给指定角色的用户，角色Id：${roleid}", opt = BusinessType.DELETE)
    @PostMapping("unallot")
    @PreAuthorize("authenticated and hasAnyAuthority('role:unallot','*') ")
    public ApiRestResponse<String> unallot(@Valid @RequestBody RoleAllotUserDTO allotDTO) throws Exception {
        boolean result = getRoleService().doUnAllot(allotDTO);
        if (result) {
            return success("role.unallot.success");
        }
        return fail("role.unallot.fail");
    }

    @ApiOperation(value = "角色人员-树形结构数据", notes = "角色与人员组成的树形结构数据")
    @GetMapping("utree")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<RoleUserVO>> userTree() throws Exception {

        // 所有角色信息
        List<RoleEntity> roles = getRoleService().getRoleList();
        List<UserAccountEntity> users = getUserAccountService().getAccountList();

        List<RoleUserVO> retList = new ArrayList<>();
        for (RoleEntity roleEntity : roles) {
            RoleUserVO roleUserVO = getBeanMapper().map(roleEntity, RoleUserVO.class);
            if (users != null) {
                List<UserAccountVO> children = users.stream().filter(source -> roleEntity.getId().equals(source.getRoleId())).map(source -> {
                    return getBeanMapper().map(source, UserAccountVO.class);
                }).collect(Collectors.toList());
                if (children != null) {
                    roleUserVO.setChildren(children);
                }
            }
            retList.add(roleUserVO);
        }
        return ApiRestResponse.success(retList);

    }

    public IRoleService getRoleService() {
        return roleService;
    }

    public IUserAccountService getUserAccountService() {
        return userAccountService;
    }
}
