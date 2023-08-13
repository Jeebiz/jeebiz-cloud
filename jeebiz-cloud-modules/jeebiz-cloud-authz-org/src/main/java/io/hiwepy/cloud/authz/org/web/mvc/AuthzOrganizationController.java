package io.hiwepy.cloud.authz.org.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzOrganizationEntity;
import io.hiwepy.cloud.authz.org.service.IAuthzOrganizationService;
import io.hiwepy.cloud.authz.org.setup.Constants;
import io.hiwepy.cloud.authz.org.utils.OrgUtils;
import io.hiwepy.cloud.authz.org.web.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "组织机构：机构信息维护")
@RestController
@RequestMapping(value = "/authz/org/")
public class AuthzOrganizationController extends BaseMapperController {

    @Autowired
    private IAuthzOrganizationService authzOrganizationService;

    @ApiOperation(value = "分页查询机构信息", notes = "分页查询机构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "分页查询参数", dataType = "AuthzOrganizationPaginationDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_ORG, business = "分页查询机构信息", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:org:list','*') ")
    public Result<AuthzOrganizationDTO> list(@Valid @RequestBody AuthzOrganizationPaginationDTO paginationDTO) throws Exception {

        AuthzOrganizationEntity model = getBeanMapper().map(paginationDTO, AuthzOrganizationEntity.class);
        Page<AuthzOrganizationEntity> pageResult = getAuthzOrganizationService().getPagedList(model);
        List<AuthzOrganizationDTO> retList = Lists.newArrayList();
        for (AuthzOrganizationEntity orgModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(orgModel, AuthzOrganizationDTO.class));
        }

        return new Result<AuthzOrganizationDTO>(pageResult, retList);

    }

    @ApiOperation(value = "机构信息：键值对集合", notes = "查询机构信息列表")
    @ApiOperationLog(module = Constants.AUTHZ_ORG, business = "查询机构信息", opt = BusinessType.SELECT)
    @GetMapping("pairs")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<PairModel>> pairs() throws Exception {
        List<PairModel> pairValues = getAuthzOrganizationService().getPairValues("");
        return ApiRestResponse.success(pairValues);
    }


    @ApiOperation(value = "创建机构信息", notes = "增加一个新的机构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "orgDTO", value = "机构信息传输对象", required = true, dataType = "AuthzOrganizationNewDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_ORG, business = "创建机构信息", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:org:new','*') ")
    public ApiRestResponse<String> newOrg(@Valid @RequestBody AuthzOrganizationNewDTO orgDTO) throws Exception {

        Long count1 = getAuthzOrganizationService().getCountByCode(orgDTO.getCode(), null);
        if (count1 > 0) {
            return fail("authz.org.new.code-exists");
        }
        Long count2 = getAuthzOrganizationService().getCountByName(orgDTO.getName(), null);
        if (count2 > 0) {
            return fail("authz.org.new.name-exists");
        }
        int count3 = getAuthzOrganizationService().getRootCount();
        if (count3 == 1 && StringUtils.equalsIgnoreCase("0", orgDTO.getCode())) {
            return fail("authz.org.new.root-exists");
        }
        AuthzOrganizationEntity model = getBeanMapper().map(orgDTO, AuthzOrganizationEntity.class);
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        model.setCreator(principal.getUid());
        // 新增一条数据库配置记录
        boolean result = getAuthzOrganizationService().save(model);
        if (result) {
            return success("authz.org.new.success", result);
        }
        return fail("authz.org.new.fail", result);
    }

    @ApiOperation(value = "更新机构信息", notes = "更新机构信息")
    @ApiOperationLog(module = Constants.AUTHZ_ORG, business = "更新机构信息", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:org:renew','*') ")
    public ApiRestResponse<String> renew(@Valid @RequestBody AuthzOrganizationRenewDTO orgDTO) throws Exception {

        // 查询历史记录
        Long count1 = getAuthzOrganizationService().getCountByCode(orgDTO.getCode(), orgDTO.getId());
        if (count1 > 0) {
            return fail("authz.org.renew.code-exists");
        }
        Long count2 = getAuthzOrganizationService().getCountByName(orgDTO.getName(), orgDTO.getId());
        if (count2 > 0) {
            return fail("authz.org.renew.name-exists");
        }

        AuthzOrganizationEntity model = getBeanMapper().map(orgDTO, AuthzOrganizationEntity.class);
        boolean result = getAuthzOrganizationService().updateById(model);
        if (result) {
            return success("authz.org.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.org.renew.fail", result);
    }

    @ApiOperation(value = "更新机构信息状态", notes = "更新机构信息状态")
    @ApiOperationLog(module = Constants.AUTHZ_ORG, business = "更新机构信息状态", opt = BusinessType.UPDATE)
    @PostMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:org:status','*') ")
    public ApiRestResponse<String> status(@Valid @RequestBody AuthzOrganizationStatusDTO satusDTO) throws Exception {
        boolean result = getAuthzOrganizationService().setStatus(satusDTO.getId(), satusDTO.getStatus());
        if (result) {
            return success("authz.org.status.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.org.status.fail", result);
    }

    @ApiOperation(value = "删除机构信息", notes = "删除机构信息")
    @ApiOperationLog(module = Constants.AUTHZ_ORG, business = "删除机构信息", opt = BusinessType.UPDATE)
    @PostMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:org:delete','*') ")
    public ApiRestResponse<String> delete(@Valid @RequestBody AuthzOrganizationDeleteDTO deleteDTO) throws Exception {

        Long count1 = getAuthzOrganizationService().getCountByParent(deleteDTO.getId());
        if (count1 > 0) {
            return fail("authz.org.delete.child-exists");
        }
        int count2 = getAuthzOrganizationService().getDeptCount(deleteDTO.getId());
        if (count2 > 0) {
            return fail("authz.org.delete.dept-exists");
        }

        // 执行机构信息删除操作
        boolean result = getAuthzOrganizationService().removeById(deleteDTO.getId());
        if (result) {
            return success("authz.org.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("authz.org.delete.fail", result);
    }

    @ApiOperation(value = "根据id查询机构信息", notes = "根据id查询机构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "机构信息id", dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_ORG, business = "查询机构信息", opt = BusinessType.SELECT)
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('authz:org:detail','*') ")
    public ApiRestResponse<AuthzOrganizationDTO> detail(@RequestParam("id") String id) throws Exception {
        AuthzOrganizationEntity model = getAuthzOrganizationService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("authz.org.not-found"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, AuthzOrganizationDTO.class));
    }

    @ApiOperation(value = "组织机构-树形结构数据（全部数据）", notes = "查询组织机构树形结构数据")
    @GetMapping("tree")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<AuthzOrganizationTreeDTO>> tree() {
        // 所有的组织机构
        List<AuthzOrganizationEntity> orgList = getAuthzOrganizationService().getOrgList();
        // 返回组织机构树形结构
        return ApiRestResponse.success(OrgUtils.getOrgTreeList(orgList));
    }

    public IAuthzOrganizationService getAuthzOrganizationService() {
        return authzOrganizationService;
    }

    public void setAuthzOrganizationService(IAuthzOrganizationService authzOrganizationService) {
        this.authzOrganizationService = authzOrganizationService;
    }

}
