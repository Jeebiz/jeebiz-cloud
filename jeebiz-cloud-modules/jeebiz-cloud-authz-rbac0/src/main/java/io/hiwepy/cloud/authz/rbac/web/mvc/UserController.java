/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.XHeaders;
import io.hiwepy.boot.api.annotation.AllowableValues;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.api.dto.UserProfileDTO;
import io.hiwepy.cloud.authz.rbac.dao.entities.RoleEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserAccountEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserProfileEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserRoleEntity;
import io.hiwepy.cloud.authz.rbac.service.IUserAccountService;
import io.hiwepy.cloud.authz.rbac.service.IUserProfileService;
import io.hiwepy.cloud.authz.rbac.service.IUserRoleService;
import io.hiwepy.cloud.authz.rbac.setup.Constants;
import io.hiwepy.cloud.authz.rbac.web.dto.*;
import io.hiwepy.cloud.authz.rbac.web.vo.RoleVO;
import io.hiwepy.cloud.authz.rbac.web.vo.UserAccountVO;
import io.hiwepy.cloud.authz.rbac.web.vo.UserProfileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 权限管理：用户管理
 */
@Api(tags = "权限管理：用户管理（Ok）")
@RestController
@RequestMapping(value = "/authz/user/")
@Slf4j
public class UserController extends BaseMapperController {

    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IUserProfileService userProfileService;
    @Autowired
    private IUserRoleService userRoleService;

    @ApiOperation(value = "分页查询用户信息", notes = "分页查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "UserPaginationDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_USER, business = "分页查询用户信息", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('user:list','*') ")
    public Result<UserAccountVO> list(@Valid @RequestBody UserPaginationDTO paginationDTO) {

        UserAccountEntity model = getBeanMapper().map(paginationDTO, UserAccountEntity.class);
        Page<UserAccountEntity> pageResult = getUserAccountService().getPagedList(model);
        List<UserAccountVO> retList = new ArrayList<UserAccountVO>();
        for (UserAccountEntity userModel : pageResult.getRecords()) {
            UserAccountVO userAccountVO = getBeanMapper().map(userModel, UserAccountVO.class);
            retList.add(userAccountVO);
        }

        return new Result<UserAccountVO>(pageResult, retList);
    }

    @ApiOperation(value = "指定用户详情", notes = "根据用户id查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "用户id", dataType = "String")
    })
    @GetMapping("detailById")
    @PreAuthorize("authenticated and hasAnyAuthority('user:detail','*') ")
    public ApiRestResponse<UserAccountVO> detailById(@RequestParam String id) throws Exception {
        UserAccountEntity model = getUserAccountService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("user.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, UserAccountVO.class));
    }

    @ApiOperation(value = "登录用户详情", notes = "根据认证信息中的用户id查询用户详情")
    @GetMapping("detail")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<UserAccountVO> detail() throws Exception {
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        UserAccountEntity model = getUserAccountService().getById(principal.getUid());
        if (model == null) {
            return ApiRestResponse.fail(getMessage("user.get.empty"));
        }
        UserAccountVO userAccountVO = getBeanMapper().map(model, UserAccountVO.class);
        UserProfileEntity profileModel = getUserProfileService().getById(principal.getUid());
        if (Objects.nonNull(profileModel)) {
            userAccountVO.setProfile(getBeanMapper().map(profileModel, UserProfileVO.class));
        }
        return ApiRestResponse.success(userAccountVO);
    }

    @ApiOperation(value = "登录用户信息", notes = "根据认证信息中的用户id查询用户详情")
    @GetMapping("profile")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<UserProfileDTO> profile() throws Exception {
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        UserProfileEntity model = getUserProfileService().getById(principal.getUid());
        if (model == null) {
            return ApiRestResponse.fail(getMessage("user.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, UserProfileDTO.class));
    }

    @ApiOperation(value = "增加用户信息", notes = "增加用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "userDTO", value = "用户信息", required = true, dataType = "UserNewDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_USER, business = "新增用户-名称：${name}", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('user:new','*') ")
    public ApiRestResponse<String> newUser(@Valid @RequestBody UserNewDTO userDTO, @ApiIgnore HttpServletRequest request) throws Exception {

        Long total = getUserAccountService().getCountByName(userDTO.getAccount(), null);
        if (total > 0) {
            return fail("user.new.exists");
        }
        Long total2 = getUserAccountService().getCountByCode(userDTO.getUcode(), null);
        if (total2 > 0) {
            return fail("user.new.ucode.exists");
        }

        UserAccountEntity model = getBeanMapper().map(userDTO, UserAccountEntity.class);

        String appId = request.getHeader(XHeaders.X_APP_ID);
        String appChannel = request.getHeader(XHeaders.X_APP_CHANNEL);
        String appVersion = request.getHeader(XHeaders.X_APP_VERSION);

        log.info(XHeaders.X_APP_ID + "：{}", appId);
        log.info(XHeaders.X_APP_CHANNEL + "：{}", appChannel);
        log.info(XHeaders.X_APP_VERSION + "：{}", appVersion);

        model.setAppId(appId);
        model.setAppChannel(appChannel);
        model.setAppVer(appVersion);

        boolean result = getUserAccountService().save(model);
        if (result) {
            return success("user.new.success", result);
        }
        return fail("user.new.fail", result);
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "userDTO", value = "用户信息", required = true, dataType = "UserRenewDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_USER, business = "修改用户-名称：${name}", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('user:renew','*') ")
    public ApiRestResponse<String> renew(@Valid @RequestBody UserRenewDTO userDTO) throws Exception {
        Long total2 = getUserAccountService().getCountByCode(userDTO.getUcode(), userDTO.getId());
        if (total2 > 0) {
            return fail("user.new.ucode.exists");
        }
        UserAccountEntity entity = getBeanMapper().map(userDTO, UserAccountEntity.class);
        boolean result = getUserAccountService().updateById(entity);
        if (result) {
            return success("user.renew.success");
        }
        return fail("user.renew.fail");
    }

    @ApiOperation(value = "更新用户状态", notes = "更新用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "用户id", dataType = "String"),
            @ApiImplicitParam(name = "status", required = true, value = "用户状态", dataType = "String", allowableValues = "1,0")
    })
    @ApiOperationLog(module = Constants.AUTHZ_USER, business = "更新用户状态", opt = BusinessType.UPDATE)
    @GetMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('user:status','*') ")
    public ApiRestResponse<String> status(@RequestParam String id, @AllowableValues(allows = "0,1", message = "用户状态错误") @RequestParam String status) throws Exception {
        boolean result = getUserAccountService().setStatus(id, status);
        if (result) {
            return success("user.status.success", result);
        }
        return fail("user.status.fail", result);
    }

    @ApiOperation(value = "删除用户信息", notes = "删除用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "用户id", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_USER, business = "删除用户-名称：${userid}", opt = BusinessType.DELETE)
    @GetMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('user:delete','*') ")
    public ApiRestResponse<String> delUser(@RequestParam("id") String id) throws Exception {
        boolean result = getUserAccountService().removeById(id);
        if (result) {
            return success("user.delete.success");
        }
        return fail("user.delete.fail");
    }

    @ApiOperation(value = "批量删除用户信息", notes = "批量删除用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户id,多个用,拼接", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_USER, business = "删除用户-名称：${userid} ", opt = BusinessType.DELETE)
    @GetMapping("deleteByIds")
    @PreAuthorize("authenticated and hasAnyAuthority('user:delete','*') ")
    public ApiRestResponse<String> delUsersByIds(@RequestParam String ids) throws Exception {
        // 执行用户id删除操作
        List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
        boolean result = getUserAccountService().removeByIds(idList);
        if (result) {
            return success("user.delete.success");
        }
        return fail("user.delete.fail");
    }

    @ApiOperation(value = "分页查询用户已分配角色信息", notes = "分页查询用户已分配角色信息")
    @ApiOperationLog(module = Constants.AUTHZ_USER, business = "分页查询用户已分配角色信息,用户Id：${userid}", opt = BusinessType.DELETE)
    @PostMapping("allocated")
    @PreAuthorize("authenticated and hasAnyAuthority('user:allocated','*') ")
    @ResponseBody
    public Result<RoleVO> allocated(@Valid @RequestBody UserPaginationDTO paginationDTO) {

        UserAccountEntity model = getBeanMapper().map(paginationDTO, UserAccountEntity.class);
        Page<RoleEntity> pageResult = getUserRoleService().getPagedAllocatedList(model);
        List<RoleVO> retList = new ArrayList<>();
        for (RoleEntity userModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(userModel, RoleVO.class));
        }
        return new Result<>(pageResult, retList);
    }

    @ApiOperation(value = "分页查询用户未分配角色信息", notes = "分页查询用户未分配角色信息")
    @ApiOperationLog(module = Constants.AUTHZ_USER, business = "分页查询用户未分配角色信息,用户Id：${userid}", opt = BusinessType.DELETE)
    @PostMapping("unallocated")
    @PreAuthorize("authenticated and hasAnyAuthority('user:unallocated','*') ")
    @ResponseBody
    public Result<RoleVO> unallocated(@Valid @RequestBody UserPaginationDTO paginationDTO) {

        UserAccountEntity model = getBeanMapper().map(paginationDTO, UserAccountEntity.class);
        Page<RoleEntity> pageResult = getUserRoleService().getPagedUnAllocatedList(model);
        List<RoleVO> retList = new ArrayList<>();
        for (RoleEntity userModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(userModel, RoleVO.class));
        }
        return new Result<>(pageResult, retList);
    }

    @ApiOperation(value = "给指定用户分配角色", notes = "给指定用户分配角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "allotDTO", value = "用户分配的角色信息", dataType = "UserAllotRoleDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_USER, business = "给指定用户分配角色，用户Id：${userid}", opt = BusinessType.DELETE)
    @PostMapping("allot")
    @PreAuthorize("authenticated and hasAnyAuthority('user:allot','*') ")
    @ResponseBody
    public ApiRestResponse<String> allot(@Valid @RequestBody UserAllotRoleDTO allotDTO) throws Exception {
        UserRoleEntity model = getBeanMapper().map(allotDTO, UserRoleEntity.class);
        boolean result = getUserRoleService().doAllot(model);
        return success("user.allot.success");
    }

    @ApiOperation(value = "取消已分配给指定用户的角色", notes = "取消已分配给指定用户的角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "allotDTO", value = "用户取消分配的角色信息", dataType = "UserAllotRoleDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_USER, business = "取消已分配给指定用户的角色，用户Id：${userid}", opt = BusinessType.DELETE)
    @PostMapping("unallot")
    @PreAuthorize("authenticated and hasAnyAuthority('user:unallot','*') ")
    @ResponseBody
    public ApiRestResponse<String> unallot(@Valid @RequestBody UserAllotRoleDTO allotDTO) throws Exception {
        UserRoleEntity model = getBeanMapper().map(allotDTO, UserRoleEntity.class);
        boolean total = getUserRoleService().doUnAllot(model);
        return success("user.unallot.success", total);
    }

    @ApiOperation(value = "重置信息：当前登录用户", notes = "重置当前登录用户信息")
    @PostMapping("reset/info")
    @PreAuthorize("authenticated")
    public ApiRestResponse<String> resetInfo(@Valid @RequestBody UserResetDTO infoDTO) throws Exception {
        // 密码加密
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        boolean result = getUserProfileService().resetProfile(Long.parseLong(principal.getUid()), infoDTO);
        if (result) {
            return success("user.reset.info.success");
        }
        return fail("user.reset.info.fail");
    }

    @ApiOperation(value = "重置密码：当前登录用户", notes = "重置当前登录用户密码")
    @ApiOperationLog(module = Constants.AUTHZ_USER, business = "设置密码", opt = BusinessType.UPDATE)
    @PostMapping("reset/pwd")
    @PreAuthorize("authenticated")
    public ApiRestResponse<String> resetPwdSelf(@Valid @RequestBody UserPwdResetDTO pwdDTO) throws Exception {
        // 密码加密
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        boolean result = getUserAccountService().resetPwd(Long.parseLong(principal.getUid()), pwdDTO.getOldPassword(), pwdDTO.getPassword());
        if (result) {
            return success("user.reset.pwd.success");
        }
        return fail("user.reset.pwd.fail");
    }

    @ApiOperation(value = "角色权限标记：当前登录用户", notes = "查询已分配给当前用户所属角色的权限")
    @GetMapping("perms")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<String>> perms() throws Exception {
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        return ApiRestResponse.success(getUserRoleService().getPermissionsByUserId(Long.parseLong(principal.getUid())));
    }

    @ApiOperation(value = "角色信息列表：当前登录用户", notes = "查询当前用户全部可用角色信息")
    @GetMapping("roles")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<RoleVO>> roles() {
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        // 	用户角色信息集合
        List<RoleEntity> roles = getUserRoleService().getRoleListByUserId(Long.parseLong(principal.getUid()));
        List<RoleVO> retList = new ArrayList<>();
        for (RoleEntity roleModel : roles) {
            retList.add(getBeanMapper().map(roleModel, RoleVO.class));
        }
        return ApiRestResponse.success(retList);
    }

    public IUserAccountService getUserAccountService() {
        return userAccountService;
    }

    public IUserProfileService getUserProfileService() {
        return userProfileService;
    }

    public IUserRoleService getUserRoleService() {
        return userRoleService;
    }

}
