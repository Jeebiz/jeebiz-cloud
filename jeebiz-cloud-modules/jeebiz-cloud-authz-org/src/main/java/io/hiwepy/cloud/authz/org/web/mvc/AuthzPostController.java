package io.hiwepy.cloud.authz.org.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import hitool.core.collections.CollectionUtils;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzPostEntity;
import io.hiwepy.cloud.authz.org.service.IAuthzPostService;
import io.hiwepy.cloud.authz.org.setup.Constants;
import io.hiwepy.cloud.authz.org.web.dto.AuthzPostDTO;
import io.hiwepy.cloud.authz.org.web.dto.AuthzPostNewDTO;
import io.hiwepy.cloud.authz.org.web.dto.AuthzPostPaginationDTO;
import io.hiwepy.cloud.authz.org.web.dto.AuthzPostRenewDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "组织机构：岗位信息维护")
@RestController
@RequestMapping(value = "/authz/org/post/")
public class AuthzPostController extends BaseApiController {

    @Autowired
    private IAuthzPostService authzPostService;

    @ApiOperation(value = "分页查询岗位信息", notes = "分页查询岗位信息")
    @ApiOperationLog(module = Constants.AUTHZ_POST, business = "分页查询岗位信息", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:post:list','*') ")
    public Result<AuthzPostDTO> list(@Valid @RequestBody AuthzPostPaginationDTO paginationDTO) throws Exception {

        AuthzPostEntity model = getBeanMapper().map(paginationDTO, AuthzPostEntity.class);
        Page<AuthzPostEntity> pageResult = getAuthzPostService().getPagedList(model);
        List<AuthzPostDTO> retList = Lists.newArrayList();
        for (AuthzPostEntity postModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(postModel, AuthzPostDTO.class));
        }

        return new Result<>(pageResult, retList);

    }

    @ApiOperation(value = "岗位信息：键值对集合", notes = "根据部门id编码查询岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门id编码", dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_POST, business = "查询岗位信息", opt = BusinessType.SELECT)
    @GetMapping("pairs")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<PairModel>> pairs(@RequestParam(required = false) String deptId) throws Exception {
        return ApiRestResponse.success(getAuthzPostService().getPairValues(deptId));
    }

    @ApiOperation(value = "岗位信息：列表集合", notes = "根据部门id编码查询岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门id编码", dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_POST, business = "查询岗位信息", opt = BusinessType.SELECT)
    @GetMapping("list")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<AuthzPostDTO>> list(@RequestParam(required = false) String deptId) throws Exception {

        List<AuthzPostEntity> resultList = getAuthzPostService().getEntityListByDeptId(deptId);
        if (CollectionUtils.isEmpty(resultList)) {
            return ApiRestResponse.fail(getMessage("authz.post.not-found"));
        }
        List<AuthzPostDTO> retList = Lists.newArrayList();
        for (AuthzPostEntity model : resultList) {
            retList.add(getBeanMapper().map(model, AuthzPostDTO.class));
        }
        return ApiRestResponse.success(retList);

    }

    @ApiOperation(value = "创建岗位信息", notes = "增加一个新的岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "postDTO", value = "岗位信息", required = true, dataType = "AuthzPostNewDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_POST, business = "创建岗位信息", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:post:new','*') ")
    public ApiRestResponse<String> post(@Valid @RequestBody AuthzPostNewDTO postDTO) throws Exception {
        AuthzPostEntity model = getBeanMapper().map(postDTO, AuthzPostEntity.class);
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        model.setCreator(principal.getUid());
        // 新增一条数据库配置记录
        boolean result = getAuthzPostService().save(model);
        if (result) {
            return success("authz.post.new.success", result);
        }
        return fail("authz.post.new.fail", result);
    }

    @ApiOperation(value = "更新岗位信息", notes = "更新岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "postDTO", value = "岗位信息", required = true, dataType = "AuthzPostRenewDTO"),
    })
    @ApiOperationLog(module = Constants.AUTHZ_POST, business = "更新岗位信息", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:post:renew','*') ")
    public ApiRestResponse<String> renew(@Valid @RequestBody AuthzPostRenewDTO postDTO) throws Exception {
        AuthzPostEntity model = getBeanMapper().map(postDTO, AuthzPostEntity.class);
        boolean result = getAuthzPostService().updateById(model);
        if (result) {
            return success("authz.post.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.post.renew.fail", result);
    }

    @ApiOperation(value = "更新岗位信息状态", notes = "更新岗位信息状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "岗位信息id", dataType = "String"),
            @ApiImplicitParam(name = "status", required = true, value = "岗位信息状态", dataType = "String", allowableValues = "1,0")
    })
    @ApiOperationLog(module = Constants.AUTHZ_POST, business = "更新岗位信息状态", opt = BusinessType.UPDATE)
    @PostMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:post:status','*') ")
    public ApiRestResponse<String> status(@RequestParam String id, @RequestParam String status) throws Exception {
        boolean result = getAuthzPostService().setStatus(id, status);
        if (result) {
            return success("authz.post.status.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.post.status.fail", result);
    }

    @ApiOperation(value = "删除岗位信息", notes = "删除岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "岗位信息id", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_POST, business = "删除岗位信息", opt = BusinessType.UPDATE)
    @GetMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:post:delete','*') ")
    public ApiRestResponse<String> delete(@RequestParam("id") String id) throws Exception {
        // 执行岗位信息删除操作
        boolean result = getAuthzPostService().removeById(id);
        if (result) {
            return success("authz.post.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.post.delete.fail", result);
    }

    @ApiOperation(value = "查询岗位信息", notes = "根据id查询岗位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "岗位信息id", dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_POST, business = "查询岗位信息", opt = BusinessType.SELECT)
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:post:detail','*') ")
    public ApiRestResponse<AuthzPostDTO> detail(@RequestParam("id") String id) throws Exception {
        AuthzPostEntity model = getAuthzPostService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("authz.post.not-found"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, AuthzPostDTO.class));
    }

    public IAuthzPostService getAuthzPostService() {
        return authzPostService;
    }

    public void setAuthzPostService(IAuthzPostService authzPostService) {
        this.authzPostService = authzPostService;
    }

}
