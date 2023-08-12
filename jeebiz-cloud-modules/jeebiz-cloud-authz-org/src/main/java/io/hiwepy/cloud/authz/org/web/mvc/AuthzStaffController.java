package io.hiwepy.cloud.authz.org.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzStaffEntity;
import io.hiwepy.cloud.authz.org.service.IAuthzStaffService;
import io.hiwepy.cloud.authz.org.setup.Constants;
import io.hiwepy.cloud.authz.org.web.dto.AuthzStaffDTO;
import io.hiwepy.cloud.authz.org.web.dto.AuthzStaffNewDTO;
import io.hiwepy.cloud.authz.org.web.dto.AuthzStaffPaginationDTO;
import io.hiwepy.cloud.authz.org.web.dto.AuthzStaffRenewDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "组织机构：人员信息维护")
@RestController
@RequestMapping(value = "/authz/org/staff/")
public class AuthzStaffController extends BaseApiController {

    @Autowired
    private IAuthzStaffService authzStaffService;

    @ApiOperation(value = "分页查询员工信息", notes = "分页查询员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "分页查询参数", dataType = "AuthzStaffPaginationDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_STAFF, business = "分页查询员工信息", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:staff:list','*') ")
    public Result<AuthzStaffDTO> list(@Valid @RequestBody AuthzStaffPaginationDTO paginationDTO) throws Exception {

        AuthzStaffEntity model = getBeanMapper().map(paginationDTO, AuthzStaffEntity.class);
        Page<AuthzStaffEntity> pageResult = getAuthzStaffService().getPagedList(model);
        List<AuthzStaffDTO> retList = Lists.newArrayList();
        for (AuthzStaffEntity staffModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(staffModel, AuthzStaffDTO.class));
        }

        return new Result<AuthzStaffDTO>(pageResult, retList);

    }

    @ApiOperation(value = "创建员工信息", notes = "增加一个新的员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "staffDTO", value = "员工信息", required = true, dataType = "AuthzStaffNewDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_STAFF, business = "创建员工信息", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:staff:new','*') ")
    public ApiRestResponse<String> staff(@Valid @RequestBody AuthzStaffNewDTO staffDTO) throws Exception {
        AuthzStaffEntity model = getBeanMapper().map(staffDTO, AuthzStaffEntity.class);
        boolean result = getAuthzStaffService().save(model);
        if (result) {
            return success("authz.staff.new.success", result);
        }
        return fail("authz.staff.new.fail", result);
    }

    @ApiOperation(value = "更新员工信息", notes = "更新员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "staffDTO", value = "员工信息", required = true, dataType = "AuthzStaffRenewDTO"),
    })
    @ApiOperationLog(module = Constants.AUTHZ_STAFF, business = "更新员工信息", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:staff:renew','*') ")
    public ApiRestResponse<String> renew(@Valid @RequestBody AuthzStaffRenewDTO staffDTO) throws Exception {
        AuthzStaffEntity model = getBeanMapper().map(staffDTO, AuthzStaffEntity.class);
        boolean result = getAuthzStaffService().updateById(model);
        if (result) {
            return success("authz.staff.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.staff.renew.fail", result);
    }

    @ApiOperation(value = "更新员工信息状态", notes = "更新员工信息状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "员工id编码", dataType = "String"),
            @ApiImplicitParam(name = "status", required = true, value = "员工信息状态", dataType = "String", allowableValues = "1,0")
    })
    @ApiOperationLog(module = Constants.AUTHZ_STAFF, business = "更新员工信息状态", opt = BusinessType.UPDATE)
    @PostMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:staff:status','*') ")
    public ApiRestResponse<String> status(@RequestParam String id, @RequestParam String status) throws Exception {
        boolean result = getAuthzStaffService().setStatus(id, status);
        if (result) {
            return success("authz.staff.status.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.staff.status.fail", result);
    }

    @ApiOperation(value = "删除员工信息", notes = "删除员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "员工id编码", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_STAFF, business = "删除员工信息", opt = BusinessType.UPDATE)
    @GetMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:staff:delete','*') ")
    public ApiRestResponse<String> delete(@RequestParam("id") String id) throws Exception {
        // 执行员工信息删除操作
        boolean result = getAuthzStaffService().removeById(id);
        if (result) {
            return success("authz.staff.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.staff.delete.fail", result);
    }

    @ApiOperation(value = "查询员工信息", notes = "根据员工id编码查询员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "员工id编码", dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_STAFF, business = "查询员工信息", opt = BusinessType.SELECT)
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:staff:detail','*') ")
    public ApiRestResponse<AuthzStaffDTO> detail(@RequestParam("id") String id) throws Exception {
        AuthzStaffEntity model = getAuthzStaffService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("authz.staff.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, AuthzStaffDTO.class));
    }

    public IAuthzStaffService getAuthzStaffService() {
        return authzStaffService;
    }

    public void setAuthzStaffService(IAuthzStaffService authzStaffService) {
        this.authzStaffService = authzStaffService;
    }

}
