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
import io.hiwepy.cloud.authz.org.dao.entities.AuthzTeamEntity;
import io.hiwepy.cloud.authz.org.service.IAuthzTeamService;
import io.hiwepy.cloud.authz.org.setup.Constants;
import io.hiwepy.cloud.authz.org.web.dto.AuthzTeamDTO;
import io.hiwepy.cloud.authz.org.web.dto.AuthzTeamNewDTO;
import io.hiwepy.cloud.authz.org.web.dto.AuthzTeamPaginationDTO;
import io.hiwepy.cloud.authz.org.web.dto.AuthzTeamRenewDTO;
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

@Api(tags = "组织机构：团队信息维护")
@RestController
@RequestMapping(value = "/authz/org/team/")
public class AuthzTeamController extends BaseApiController {

    @Autowired
    private IAuthzTeamService authzTeamService;

    @ApiOperation(value = "分页查询团队信息", notes = "分页查询团队信息")
    @ApiOperationLog(module = Constants.AUTHZ_TEAM, business = "分页查询团队信息", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:team:list','*') ")
    public Result<AuthzTeamDTO> list(@Valid @RequestBody AuthzTeamPaginationDTO paginationDTO) throws Exception {

        AuthzTeamEntity model = getBeanMapper().map(paginationDTO, AuthzTeamEntity.class);
        Page<AuthzTeamEntity> pageResult = getAuthzTeamService().getPagedList(model);
        List<AuthzTeamDTO> retList = Lists.newArrayList();
        for (AuthzTeamEntity teamModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(teamModel, AuthzTeamDTO.class));
        }

        return new Result<AuthzTeamDTO>(pageResult, retList);

    }

    @ApiOperation(value = "团队信息：数据列表集合", notes = "根据部门id编码查询团队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门id编码", dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_TEAM, business = "查询团队信息", opt = BusinessType.SELECT)
    @GetMapping("list")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<AuthzTeamDTO>> list(@RequestParam(required = false) String deptId) throws Exception {
        List<AuthzTeamEntity> resultList = getAuthzTeamService().getEntityListByDeptId(deptId);
        if (CollectionUtils.isEmpty(resultList)) {
            return ApiRestResponse.fail(getMessage("authz.team.not-found"));
        }
        List<AuthzTeamDTO> retList = Lists.newArrayList();
        for (AuthzTeamEntity model : resultList) {
            retList.add(getBeanMapper().map(model, AuthzTeamDTO.class));
        }
        return ApiRestResponse.success(retList);
    }

    @ApiOperation(value = "团队信息：键值对集合", notes = "根据部门id编码查询团队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门id编码", dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_TEAM, business = "查询团队信息", opt = BusinessType.SELECT)
    @GetMapping("pairs")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<PairModel>> pairs(@RequestParam(required = false) String deptId) throws Exception {
        return ApiRestResponse.success(getAuthzTeamService().getPairValues(deptId));
    }

    @ApiOperation(value = "创建团队信息", notes = "增加一个新的团队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "teamDTO", value = "团队信息", required = true, dataType = "AuthzTeamNewDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_TEAM, business = "创建团队信息", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:team:new','*') ")
    public ApiRestResponse<String> team(@Valid @RequestBody AuthzTeamNewDTO teamDTO) throws Exception {

        int count1 = getAuthzTeamService().getTeamCountByName(teamDTO.getName(), teamDTO.getDeptId(), null);
        if (count1 > 0) {
            return fail("authz.team.new.name-exists");
        }
        AuthzTeamEntity model = getBeanMapper().map(teamDTO, AuthzTeamEntity.class);
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        model.setCreator(principal.getUid());
        // 新增一条数据库配置记录
        boolean result = getAuthzTeamService().save(model);
        if (result) {
            return success("authz.team.new.success", result);
        }
        return fail("authz.team.new.fail", result);
    }

    @ApiOperation(value = "更新团队信息", notes = "更新团队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "teamDTO", value = "团队信息", required = true, dataType = "AuthzTeamRenewDTO"),
    })
    @ApiOperationLog(module = Constants.AUTHZ_TEAM, business = "更新团队信息", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:team:renew','*') ")
    public ApiRestResponse<String> renew(@Valid @RequestBody AuthzTeamRenewDTO teamDTO) throws Exception {
        int count1 = getAuthzTeamService().getTeamCountByName(teamDTO.getName(), teamDTO.getDeptId(), teamDTO.getId());
        if (count1 > 0) {
            return fail("authz.team.renew.name-exists");
        }
        AuthzTeamEntity model = getBeanMapper().map(teamDTO, AuthzTeamEntity.class);
        boolean result = getAuthzTeamService().updateById(model);
        if (result) {
            return success("authz.team.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.team.renew.fail", result);
    }

    @ApiOperation(value = "更新团队信息状态", notes = "更新团队信息状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "团队信息id", dataType = "String"),
            @ApiImplicitParam(name = "status", required = true, value = "团队信息状态", dataType = "String", allowableValues = "1,0")
    })
    @ApiOperationLog(module = Constants.AUTHZ_TEAM, business = "更新团队信息状态", opt = BusinessType.UPDATE)
    @PostMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:team:status','*') ")
    public ApiRestResponse<String> status(@RequestParam String id, @RequestParam String status) throws Exception {
        boolean result = getAuthzTeamService().setStatus(id, status);
        if (result) {
            return success("authz.team.status.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.team.status.fail", result);
    }

    @ApiOperation(value = "删除团队信息", notes = "删除团队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "团队信息id", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_TEAM, business = "删除团队信息", opt = BusinessType.UPDATE)
    @GetMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:team:delete','*') ")
    public ApiRestResponse<String> delete(@RequestParam("id") String id) throws Exception {

        int count = getAuthzTeamService().getStaffCount(id);
        if (count > 0) {
            return fail("authz.team.delete.staff-exists");
        }
        // 执行团队信息删除操作
        boolean result = getAuthzTeamService().removeById(id);
        if (result) {
            return success("authz.team.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.team.delete.fail", result);
    }

    @ApiOperation(value = "查询团队信息", notes = "根据id查询团队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "团队信息id", dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_TEAM, business = "查询团队信息", opt = BusinessType.SELECT)
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:team:detail','*') ")
    public ApiRestResponse<AuthzTeamDTO> detail(@RequestParam("id") String id) throws Exception {
        AuthzTeamEntity model = getAuthzTeamService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("authz.team.not-found"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, AuthzTeamDTO.class));
    }

    public IAuthzTeamService getAuthzTeamService() {
        return authzTeamService;
    }

    public void setAuthzTeamService(IAuthzTeamService authzTeamService) {
        this.authzTeamService = authzTeamService;
    }

}
