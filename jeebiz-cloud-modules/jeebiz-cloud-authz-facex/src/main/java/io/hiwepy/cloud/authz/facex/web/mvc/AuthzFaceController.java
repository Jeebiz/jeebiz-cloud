/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.web.mvc;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.authz.facex.dao.entities.AuthzFaceEntity;
import io.hiwepy.cloud.authz.facex.service.IAuthzFaceService;
import io.hiwepy.cloud.authz.facex.setup.Constants;
import io.hiwepy.cloud.authz.facex.web.dto.AuthzFaceDTO;
import io.hiwepy.cloud.authz.facex.web.dto.AuthzFacePaginationDTO;
import io.hiwepy.cloud.authz.facex.web.dto.AuthzFaceQueryDTO;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "人脸识别：服务接口")
@RestController
@RequestMapping(value = "/authz/face/")
@Validated
public class AuthzFaceController extends BaseApiController {

    @Autowired
    protected IAuthzFaceService authzFaceService;

    @ApiOperation(value = "分页查询人脸识别数据信息", notes = "分页查询人脸识别数据信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "分页查询参数", dataType = "AuthzFacePaginationDTO")
    })
    @ApiOperationLog(module = Constants.AUTHZ_FACE, business = Constants.Biz.AUTHZ_FACE_LIST, opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('face:list','*') ")
    @ResponseBody
    public Object list(@Valid @RequestBody AuthzFacePaginationDTO paginationDTO) throws Exception {

        AuthzFaceEntity model = getBeanMapper().map(paginationDTO, AuthzFaceEntity.class);
        Page<AuthzFaceEntity> pageResult = getAuthzFaceService().getPagedList(model);
        List<AuthzFaceDTO> retList = Lists.newArrayList();
        for (AuthzFaceEntity orgModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(orgModel, AuthzFaceDTO.class));
        }

        return new Result<AuthzFaceDTO>(pageResult, retList);

    }

    @ApiOperation(value = "查询人脸照片", notes = "根据人脸识别数据ID查询人脸照片数据")
    @PostMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('face:detail','*') ")
    @ResponseBody
    public ApiRestResponse<AuthzFaceDTO> detail(@Valid
                                                @ApiParam(value = "人脸识别数据ID", required = true) @RequestParam(value = "faceId")
                                                @NotBlank(message = "人脸识别数据ID不能为空") String faceId) throws Exception {
        AuthzFaceEntity model = getAuthzFaceService().getById(faceId);
        if (model == null) {
            return fail("face.image.get.empty");
        }
        return ApiRestResponse.success(getBeanMapper().map(model, AuthzFaceDTO.class));
    }


    @ApiOperation(value = "查询个人人脸照片", notes = "个人照片人脸识别数据")
    @PostMapping("info")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<AuthzFaceDTO> info() throws Exception {
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        AuthzFaceEntity model = getAuthzFaceService().getFaceModel(principal.getUid());
        if (model == null) {
            return fail("face.image.get.empty");
        }
        return ApiRestResponse.success(getBeanMapper().map(model, AuthzFaceDTO.class));
    }

    @ApiOperation(value = "人脸扫描", notes = "检测图片中的人脸并存储检查合格的人脸图片作为识别的底片")
    @ApiOperationLog(module = Constants.AUTHZ_FACE, business = Constants.Biz.AUTHZ_FACE_DETECT, opt = BusinessType.INSERT)
    @PostMapping("scanning")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<JSONObject> scanning(@Valid
                                                @ApiParam(value = "人脸图片文件", required = true) @RequestParam(value = "image")
                                                @NotNull(message = "人脸图片不能为空") MultipartFile image) throws Exception {
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        JSONObject detect = getAuthzFaceService().scanning(principal.getUid(), image);
        if (StringUtils.equalsIgnoreCase(detect.getString("error_code"), "0")) {
            return ApiRestResponse.success(detect);
        }
        return ApiRestResponse.fail(detect.getIntValue("error_code"), detect.getString("error_msg"));
    }

    @ApiOperation(value = "人脸检测与属性分析", notes = "检测图片中的人脸并标记出位置信息")
    @ApiOperationLog(module = Constants.AUTHZ_FACE, business = Constants.Biz.AUTHZ_FACE_DETECT, opt = BusinessType.INSERT)
    @PostMapping("detect")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<JSONObject> detect(@Valid
                                              @ApiParam(value = "人脸图片文件", required = true) @RequestParam(value = "image")
                                              @NotNull(message = "人脸图片不能为空") MultipartFile image) throws Exception {
        JSONObject detect = getAuthzFaceService().detect(image);
        if (StringUtils.equalsIgnoreCase(detect.getString("error_code"), "0")) {
            return ApiRestResponse.success(detect);
        }
        return ApiRestResponse.fail(detect.getIntValue("error_code"), detect.getString("error_msg"));
    }

    @ApiOperation(value = "人脸对比", notes = "比对两张图片中人脸的相似度，并返回相似度分值（接口提供被对比图片上传字段，图片底片以历史采集记录为准）")
    @ApiOperationLog(module = Constants.AUTHZ_FACE, business = Constants.Biz.AUTHZ_FACE_MATCH, opt = BusinessType.INSERT)
    @PostMapping("match")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<JSONObject> match(@Valid
                                             @ApiParam(value = "人脸图片文件", required = true) @RequestParam(value = "image")
                                             @NotNull(message = "人脸图片不能为空") MultipartFile image) throws Exception {
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        JSONObject match = getAuthzFaceService().match(principal.getUid(), image);
        if (StringUtils.equalsIgnoreCase(match.getString("error_code"), "0")) {
            return ApiRestResponse.success(match);
        }
        return ApiRestResponse.fail(match.getIntValue("error_code"), match.getString("error_msg"));
    }

    @ApiOperation(value = "人脸搜索", notes = "在指定人脸集合中，找到最相似的人脸")
    @ApiOperationLog(module = Constants.AUTHZ_FACE, business = Constants.Biz.AUTHZ_FACE_SEARCH, opt = BusinessType.INSERT)
    @PostMapping("search")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<JSONObject> search(@Valid
                                              @ApiParam(value = "人脸图片文件", required = true) @RequestParam(value = "image")
                                              @NotNull(message = "人脸图片不能为空") MultipartFile image) throws Exception {
        JSONObject search = getAuthzFaceService().search(image);
        if (StringUtils.equalsIgnoreCase(search.getString("error_code"), "0")) {
            return ApiRestResponse.success(search);
        }
        return ApiRestResponse.fail(search.getIntValue("error_code"), search.getString("error_msg"));
    }

    @ApiOperation(value = "人脸融合", notes = "对两张人脸进行融合处理，生成的人脸同时具备两张人脸的外貌特征（并不是换脸）")
    @ApiOperationLog(module = Constants.AUTHZ_FACE, business = Constants.Biz.AUTHZ_FACE_MERGE, opt = BusinessType.INSERT)
    @PostMapping("merge")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<JSONObject> merge(@Valid
                                             @ApiParam(value = "模板图片文件", required = true) @RequestParam(value = "template")
                                             @NotNull(message = "模板图片不能为空") MultipartFile template,
                                             @ApiParam(value = "人脸图片文件", required = true) @RequestParam(value = "target")
                                             @NotNull(message = "人脸图片不能为空") MultipartFile target) throws Exception {
        JSONObject merge = getAuthzFaceService().merge(template, target);
        if (StringUtils.equalsIgnoreCase(merge.getString("error_code"), "0")) {
            return ApiRestResponse.success(merge);
        }
        return ApiRestResponse.fail(merge.getIntValue("error_code"), merge.getString("error_msg"));
    }

    @ApiOperation(value = "人脸照片：压缩包文件下载", notes = "根据筛选条件查询符合条件的照片，并打包照片数据下载")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "queryDTO", value = "下载查询参数", dataType = "AuthzFaceQueryDTO")
    })
    @PostMapping("download")
    @PreAuthorize("authenticated and hasAnyAuthority('face:download','*') ")
    public ResponseEntity<byte[]> download(@Valid @RequestBody AuthzFaceQueryDTO queryDTO) throws Exception {
        return getAuthzFaceService().download(queryDTO);
    }

    public IAuthzFaceService getAuthzFaceService() {
        return authzFaceService;
    }

}
