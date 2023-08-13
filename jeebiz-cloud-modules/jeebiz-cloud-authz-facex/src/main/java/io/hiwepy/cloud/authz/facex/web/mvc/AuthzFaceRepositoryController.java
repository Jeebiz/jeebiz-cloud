/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.authz.facex.dao.entities.AuthzFaceRepositoryEntity;
import io.hiwepy.cloud.authz.facex.service.IAuthzFaceRepositoryService;
import io.hiwepy.cloud.authz.facex.setup.Constants;
import io.hiwepy.cloud.authz.facex.web.dto.AuthzFaceDTO;
import io.hiwepy.cloud.authz.facex.web.dto.AuthzFaceRepositoryNewDTO;
import io.hiwepy.cloud.authz.facex.web.dto.AuthzFaceRepositoryPaginationDTO;
import io.hiwepy.cloud.authz.facex.web.dto.AuthzFaceRepositoryRenewDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "人脸识别：存储接口")
@RestController
@RequestMapping(value = "/authz/face/repo/")
public class AuthzFaceRepositoryController extends BaseApiController {

    @Autowired
    protected IAuthzFaceRepositoryService authzFaceRepositoryService;

    @ApiOperation(value = "分页查询人脸识别数据分组信息", notes = "分页查询人脸识别数据分组信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "分页查询参数", dataType = "AuthzFaceRepositoryPaginationDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_FACE_REPO, business = "分页查询机构信息", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('face-repo:list','*') ")
    @ResponseBody
    public Object list(@Valid @RequestBody AuthzFaceRepositoryPaginationDTO paginationDTO) throws Exception {

        AuthzFaceRepositoryEntity model = getBeanMapper().map(paginationDTO, AuthzFaceRepositoryEntity.class);
        Page<AuthzFaceRepositoryEntity> pageResult = getAuthzFaceRepositoryService().getPagedList(model);
        List<AuthzFaceDTO> retList = Lists.newArrayList();
        for (AuthzFaceRepositoryEntity orgModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(orgModel, AuthzFaceDTO.class));
        }

        return new Result<AuthzFaceDTO>(pageResult, retList);

    }

    @ApiOperation(value = "增加人脸识别数据分组信息", notes = "增加人脸识别数据分组信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "repoDTO", value = "人脸识别数据分组信息", dataType = "AuthzFaceRepositoryNewDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_FACE_REPO, business = Constants.Biz.AUTHZ_FACE_REPO_NEW, opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('face-repo:new','*') ")
    @ResponseBody
    public ApiRestResponse<String> faceRepo(@Valid @RequestBody AuthzFaceRepositoryNewDTO repoDTO) throws Exception {
        Long total = getAuthzFaceRepositoryService().getCountByName(repoDTO.getName(), null);
        if (total > 0) {
            return fail("face.repo.new.name-exists");
        }
        AuthzFaceRepositoryEntity model = getBeanMapper().map(repoDTO, AuthzFaceRepositoryEntity.class);
        boolean ret = getAuthzFaceRepositoryService().save(model);
        if (ret) {
            return success("face.repo.new.success", ret);
        }
        return fail("face.repo.new.fail", ret);
    }

    @ApiOperation(value = "修改人脸识别数据分组信息", notes = "修改人脸识别数据分组信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "repoDTO", value = "人脸识别数据分组信息", dataType = "AuthzFaceRepositoryRenewDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_FACE_REPO, business = Constants.Biz.AUTHZ_FACE_REPO_RENEW, opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('face-repo:renew','*') ")
    @ResponseBody
    public ApiRestResponse<String> renew(@Valid @RequestBody AuthzFaceRepositoryRenewDTO repoDTO) throws Exception {
        Long total = getAuthzFaceRepositoryService().getCountByName(repoDTO.getName(), repoDTO.getId());
        if (total > 0) {
            return fail("face.repo.new.name-exists");
        }
        AuthzFaceRepositoryEntity model = getBeanMapper().map(repoDTO, AuthzFaceRepositoryEntity.class);
        boolean ret = getAuthzFaceRepositoryService().updateById(model);
        if (ret) {
            return success("face.repo.renew.success", ret);
        }
        return fail("face.repo.renew.fail", ret);
    }

    @ApiOperation(value = "删除人脸识别数据分组信息", notes = "删除人脸识别数据分组信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "人脸识别数据分组ID", dataType = "String")
    })
    @ApiOperationLog(module = Constants.AUTHZ_FACE_REPO, business = Constants.Biz.AUTHZ_FACE_REPO_DELETE, opt = BusinessType.DELETE)
    @PostMapping("delete/{id}")
    @PreAuthorize("authenticated and hasAnyAuthority('face-repo:delete','*') ")
    @ResponseBody
    public ApiRestResponse<String> delFace(@Valid @NotNull(message = "人脸识别数据分组ID不能为空") @RequestParam String id) throws Exception {
        Long count = getAuthzFaceRepositoryService().getCountByUid(id);
        if (count > 0) {
            return fail("face.repo.delete.child-exists");
        }
        boolean rt = getAuthzFaceRepositoryService().removeById(id);
        if (rt) {
            return success("face.repo.delete.success", rt);
        }
        return success("face.repo.delete.fail", rt);
    }

    public IAuthzFaceRepositoryService getAuthzFaceRepositoryService() {
        return authzFaceRepositoryService;
    }

}
