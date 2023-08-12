/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.flow.web.mvc;

import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.XHeaders;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.cloud.oss.core.bo.*;
import io.hiwepy.cloud.oss.core.enums.OssChannel;
import io.hiwepy.cloud.oss.core.vo.FileInfoVO;
import io.hiwepy.cloud.oss.flow.service.IFileService;
import io.hiwepy.cloud.oss.flow.web.param.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.WebUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Api(tags = "文件IO：文件上传、下载（Ok）")
@RestController
@RequestMapping("/file/")
@Slf4j
@Validated
public class OssController extends BaseApiController {

    @Autowired
    private IFileService fileService;

    @ApiOperation(value = "文件服务配置信息", notes = "文件服务配置信息")
    @GetMapping("config")
    public ApiRestResponse<FileStoreConfig> config(@ApiParam(value = "存储目标", required = true, defaultValue = "LOCAL") @RequestParam(value = "channel", required = true, defaultValue = "LOCAL") OssChannel channel) {
        FileStoreConfig config = fileService.getConfig(channel);
        return ApiRestResponse.success(config);
    }

    @ApiOperation(value = "文件服务：单上传文件", notes = "将单个文件上传到指定的存储对象")
    @PostMapping(value = "upload", headers = "content-type=multipart/form-data")
    @PreAuthorize("authenticated")
    public ApiRestResponse<FileInfoVO> upload(
            @Valid FileUploadParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) throws Exception {
        try {

            log.info("upload => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

            // 1、当前登录用户
            String userId = SubjectUtils.getUserId();

            // 2、文件上传参数
            FileUploadBO uploadBo = FileUploadBO.builder()
                    .appId(appId)
                    .appChannel(appChannel)
                    .appVer(appVersion)
                    .bizId(param.getBizId())
                    .channel(param.getChannel())
                    .file(param.getFile())
                    .thumb(param.isThumb())
                    .thumbWidth(param.getWidth())
                    .thumbHeight(param.getHeight())
                    .userId(userId)
                    .ipAddress(WebUtils.getRemoteAddr(request))
                    .build();

            // 3、执行文件上传操作
            FileUploadResult uploadRt = fileService.upload(uploadBo);
            if (uploadRt.getStatus() == 1) {
                return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.upload.success"), uploadRt.getFile());
            }
            return ApiRestResponse.of(ApiCode.SC_FAIL, getMessage("file.upload.fail"), uploadRt.getFile());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "文件服务：多上传文件", notes = "将多个文件上传到指定的存储对象")
    @PostMapping(value = "uploads", headers = "content-type=multipart/form-data")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<FileInfoVO>> uploads(
            @Valid FilesUploadParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) throws Exception {
        if (param.getFiles().length == 0) {
            return ApiRestResponse.of(ApiCode.SC_UNSATISFIED_PARAM);
        }

        try {

            log.info("uploads => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

            // 1、当前登录用户
            String userId = SubjectUtils.getUserId();

            // 2、文件上传参数
            FilesUploadBO uploadBo = FilesUploadBO.builder()
                    .appId(appId)
                    .appChannel(appChannel)
                    .appVer(appVersion)
                    .bizId(param.getBizId())
                    .channel(param.getChannel())
                    .files(param.getFiles())
                    .thumb(param.isThumb())
                    .thumbWidth(param.getWidth())
                    .thumbHeight(param.getHeight())
                    .userId(userId)
                    .ipAddress(WebUtils.getRemoteAddr(request))
                    .build();

            // 3、执行文件上传操作
            FilesUploadResult uploadRt = fileService.upload(uploadBo);
            if (uploadRt.getStatus() == 1) {
                return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.upload.success"), uploadRt.getFiles());
            }
            return fail("file.upload.fail");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "文件服务：图片文件上传", notes = "将图片文件上传到数据库存储和缓存中")
    @PostMapping(value = "upload/image", headers = "content-type=multipart/form-data")
    @PreAuthorize("authenticated")
    public ApiRestResponse<FileInfoVO> uploadImage(
            @Valid ImageUploadParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) throws Exception {
        try {

            log.info("upload => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

            // 1、当前登录用户
            String userId = SubjectUtils.getUserId();

            // 2、文件上传参数
            FileUploadBO uploadBo = FileUploadBO.builder()
                    .appId(appId)
                    .appChannel(appChannel)
                    .appVer(appVersion)
                    .bizId(param.getBizId())
                    .channel(OssChannel.OSS_DATABASE)
                    .file(param.getFile())
                    .thumb(param.isThumb())
                    .thumbWidth(param.getWidth())
                    .thumbHeight(param.getHeight())
                    .userId(userId)
                    .ipAddress(WebUtils.getRemoteAddr(request))
                    .build();

            // 3、执行文件上传操作
            FileUploadResult uploadRt = fileService.upload(uploadBo);
            if (uploadRt.getStatus() == 1) {
                return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.upload.success"), uploadRt.getFile());
            }
            return ApiRestResponse.of(ApiCode.SC_FAIL, getMessage("file.upload.fail"), uploadRt.getFile());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "文件服务：多图片文件上传", notes = "将多个图片文件上传到数据库存储和缓存中")
    @PostMapping(value = "upload/images", headers = "content-type=multipart/form-data")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<FileInfoVO>> uploadImages(
            @Valid ImagesUploadParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) throws Exception {
        if (param.getFiles().length == 0) {
            return ApiRestResponse.of(ApiCode.SC_UNSATISFIED_PARAM);
        }

        try {

            log.info("uploads => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

            // 1、当前登录用户
            String userId = SubjectUtils.getUserId();

            // 2、文件上传参数
            FilesUploadBO uploadBo = FilesUploadBO.builder()
                    .appId(appId)
                    .appChannel(appChannel)
                    .appVer(appVersion)
                    .bizId(param.getBizId())
                    .channel(OssChannel.OSS_DATABASE)
                    .files(param.getFiles())
                    .thumb(param.isThumb())
                    .thumbWidth(param.getWidth())
                    .thumbHeight(param.getHeight())
                    .userId(userId)
                    .ipAddress(WebUtils.getRemoteAddr(request))
                    .build();

            // 3、执行文件上传操作
            FilesUploadResult uploadRt = fileService.upload(uploadBo);
            if (uploadRt.getStatus() == 1) {
                return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.upload.success"), uploadRt.getFiles());
            }
            return fail("file.upload.fail");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "文件服务：根据uuid删除文件", notes = "删除指定存储对象下的指定文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "文件Uid", required = true, dataType = "String"),
    })
    @PostMapping("delete")
    @PreAuthorize("authenticated")
    public ApiRestResponse<String> delete(
            @Valid @RequestBody FileDeleteParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) {
        try {

            log.info("delete => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

            // 1、当前登录用户
            String userId = SubjectUtils.getUserId();

            // 2、文件删除参数
            FileDeleteBO deleteBo = FileDeleteBO.builder()
                    .appId(appId)
                    .appChannel(appChannel)
                    .appVer(appVersion)
                    .userId(userId)
                    .uuids(Arrays.asList(param.getUuid()))
                    .ipAddress(WebUtils.getRemoteAddr(request))
                    .build();

            // 3、执行文件删除操作
            fileService.delete(deleteBo);

            return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.delete.success"), "");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "文件服务：根据path批量删除文件", notes = "批量删除指定存储对象下的指定文件")
    @PostMapping("deleteByPath")
    @PreAuthorize("authenticated")
    public ApiRestResponse<String> deleteByPath(
            @Valid @RequestBody FileDeleteByPathParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) {
        try {

            log.info("deleteByPath => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

            // 1、当前登录用户
            String userId = SubjectUtils.getUserId();

            // 2、文件删除参数
            FileDeleteBO deleteBo = FileDeleteBO.builder()
                    .appId(appId)
                    .appChannel(appChannel)
                    .appVer(appVersion)
                    .paths(param.getPaths())
                    .userId(userId)
                    .ipAddress(WebUtils.getRemoteAddr(request))
                    .build();

            // 3、执行文件删除操作
            fileService.delete(deleteBo);

            return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.delete.success"), "");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "文件服务：根据uuid批量删除文件", notes = "批量删除指定存储对象下的指定文件")
    @PostMapping("deleteByFileId")
    @PreAuthorize("authenticated")
    public ApiRestResponse<String> deleteByFileId(
            @Valid @RequestBody FileDeleteByFileIdParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) {
        try {

            log.info("deleteByFileId => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

            // 1、当前登录用户
            String userId = SubjectUtils.getUserId();

            // 2、文件删除参数
            FileDeleteBO deleteBo = FileDeleteBO.builder()
                    .appId(appId)
                    .appChannel(appChannel)
                    .appVer(appVersion)
                    .userId(userId)
                    .uuids(param.getUuids())
                    .ipAddress(WebUtils.getRemoteAddr(request))
                    .build();

            // 3、执行文件删除操作
            fileService.delete(deleteBo);

            return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.delete.success"), "");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "文件服务：重新上传文件", notes = "重新上传指定的文件")
    @PostMapping(value = "reupload", headers = "content-type=multipart/form-data")
    @PreAuthorize("authenticated")
    public ApiRestResponse<FileInfoVO> reupload(
            @Valid FileReuploadParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) throws Exception {

        try {

            log.info("reupload => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

            // 1、当前登录用户
            String userId = SubjectUtils.getUserId();

            // 2、文件重新上传参数
            FileReuploadBO uploadBo = FileReuploadBO.builder()
                    .appId(appId)
                    .appChannel(appChannel)
                    .appVer(appVersion)
                    .channel(param.getChannel())
                    .file(param.getFile())
                    .thumb(param.isThumb())
                    .thumbWidth(param.getWidth())
                    .thumbHeight(param.getHeight())
                    .userId(userId)
                    .ipAddress(WebUtils.getRemoteAddr(request))
                    .build();

            // 3、执行文件上传操作
            FileReuploadResult uploadRt = fileService.reupload(uploadBo);
            if (uploadRt.getStatus() == 1) {
                return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.reupload.success"), uploadRt.getFile());
            }
            return fail("file.reupload.fail");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "文件服务：根据path查询文件信息", notes = "根据给出的文件相对路径获取文件信息")
    @GetMapping("getByPath")
    //@PreAuthorize("authenticated")
    public ApiRestResponse<FileInfoVO> getByPath(
            @Valid FileGetByPathParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) {
        try {

            log.info("getByPath => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

            // 1、执行文件删除操作
            FileInfoVO fileInfoVO = fileService.getByPath(param.getPath(), param.isMetadata());

            return ApiRestResponse.success(fileInfoVO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "文件服务：根据uuid查询文件信息", notes = "根据给出的文件Uuid获取文件信息")
    @GetMapping("getByFileId")
    //@PreAuthorize("authenticated")
    public ApiRestResponse<FileInfoVO> getByFileId(
            @Valid FileGetByFileIdParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) {
        try {

            log.info("getByFileId => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

            // 1、执行文件删除操作
            FileInfoVO fileInfoVO = fileService.getByFileId(param.getUuid(), param.isMetadata());

            return ApiRestResponse.success(fileInfoVO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "文件服务：根据path批量查询文件信息", notes = "根据给出的文件相对路径获取文件信息")
    @PostMapping("listByPath")
    //@PreAuthorize("authenticated")
    public ApiRestResponse<List<FileInfoVO>> listByPath(
            @Valid @RequestBody FileListByPathParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) {
        try {

            log.info("listByPath => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

            // 1、执行文件删除操作
            List<FileInfoVO> fileInfoVOS = fileService.listByPath(param.getPaths(), param.isMetadata());

            return ApiRestResponse.success(fileInfoVOS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "文件服务：根据uuid批量查询文件信息", notes = "根据给出的文件Uuid获取文件信息")
    @PostMapping("listByFileId")
    //@PreAuthorize("authenticated")
    public ApiRestResponse<List<FileInfoVO>> listByFileId(
            @Valid @RequestBody FileListByFileIdParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) {
        try {

            log.info("listByFileId => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

            // 1、执行文件删除操作
            List<FileInfoVO> fileInfoVOS = fileService.listByFileId(param.getUuids(), param.isMetadata());

            return ApiRestResponse.success(fileInfoVOS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiRestResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "文件服务：根据path下载文件", notes = "根据给出的文件相对路径下载文件")
    @GetMapping("downloadByPath")
    @PreAuthorize("authenticated")
    public ResponseEntity<byte[]> downloadByPath(
            @Valid FileDownloadByPathParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) {

        ResponseEntity<byte[]> entity = null;
        try {

            log.info("downloadByPath => appId：{}, appChannel：{}, appVersion：{}, path：{}", appId, appChannel, appVersion, param.getPath());

            FileDownloadResult downloadRt = fileService.downloadByPath(param.getPath());
            if (Objects.nonNull(downloadRt)) {

                String filename = downloadRt.getFile().getName();

                // 定义http头 ，状态
                HttpHeaders header = new HttpHeaders();
                header.add("Content-Disposition", "attchement;filename=" + filename);
                header.setContentType(MediaTypeFactory.getMediaType(filename).get());

                // 定义ResponseEntity封装返回信息
                return new ResponseEntity<byte[]>(downloadRt.getBytes(), header, HttpStatus.OK);

            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return entity;
    }

    @ApiOperation(value = "文件服务：根据uuid下载文件", notes = "根据给出的文件Uuid下载文件")
    @GetMapping("downloadByFileId")
    @PreAuthorize("authenticated")
    public ResponseEntity<byte[]> downloadByFileId(
            @Valid FileDownloadByFileIdParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) throws Exception {
        ResponseEntity<byte[]> entity = null;
        try {

            log.info("downloadByFileId => appId：{}, appChannel：{}, appVersion：{}, path：{}", appId, appChannel, appVersion, param.getUuid());

            FileDownloadResult downloadRt = fileService.downloadByFileId(param.getUuid());
            if (Objects.nonNull(downloadRt)) {

                String filename = downloadRt.getFile().getName();

                // 定义http头 ，状态
                HttpHeaders header = new HttpHeaders();
                header.add("Content-Disposition", "attchement;filename=" + filename);
                header.setContentType(MediaTypeFactory.getMediaType(filename).get());

                // 定义ResponseEntity封装返回信息
                return new ResponseEntity<byte[]>(downloadRt.getBytes(), header, HttpStatus.OK);

            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return entity;
    }


    @ApiOperation(value = "文件服务：根据uuid下载文件", notes = "根据给出的文件Uuid下载文件")
    @GetMapping("downloadByFileIdUrlencode")
    @PreAuthorize("authenticated")
    public ResponseEntity<byte[]> downloadByFileIdUrlencode(
            @Valid FileDownloadByFileIdParam param,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) throws Exception {
        ResponseEntity<byte[]> entity = null;
        try {

            log.info("downloadByFileId => appId：{}, appChannel：{}, appVersion：{}, path：{}", appId, appChannel, appVersion, param.getUuid());

            FileDownloadResult downloadRt = fileService.downloadByFileId(param.getUuid());
            if (Objects.nonNull(downloadRt)) {

                String filename = downloadRt.getFile().getName();

                // 定义http头 ，状态
                HttpHeaders header = new HttpHeaders();
                filename = URLEncoder.encode(filename, "UTF-8");
                header.add("Content-Disposition", "attchement;filename=" + filename);
                header.setContentType(MediaTypeFactory.getMediaType(filename).get());

                // 定义ResponseEntity封装返回信息
                return new ResponseEntity<byte[]>(downloadRt.getBytes(), header, HttpStatus.OK);

            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return entity;
    }

}
