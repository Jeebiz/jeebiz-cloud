/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.flow.web.mvc;

import io.hiwepy.boot.api.XHeaders;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.cloud.oss.core.bo.FileDownloadResult;
import io.hiwepy.cloud.oss.core.vo.FileInfoVO;
import io.hiwepy.cloud.oss.flow.service.IFileService;
import io.hiwepy.cloud.oss.flow.web.param.FileDownloadByFileIdParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Api(tags = "文件IO：图片文件预览（Ok）")
@RestController
@RequestMapping("/image/")
@Slf4j
@Validated
public class ImageController extends BaseApiController {

    @Autowired
    private IFileService fileService;

    @ApiOperation(value = "文件服务：根据uuid查询图片Base64数据", notes = "根据uuid查询图片Base64数据")
    @GetMapping("{uuid}")
    public ResponseEntity<String> getByFileId(
            @Valid @NotBlank(message = "文件路径不能为空") @PathVariable("uuid") String uuid,
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) throws Exception {

        log.info("getByFileId => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);

        // 1、执行文件删除操作
        FileInfoVO fileInfoVO = fileService.getByFileId(uuid, Boolean.FALSE);

        // 定义http头 ，状态
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "filename=" + fileInfoVO.getName());
        header.setContentType(MediaTypeFactory.getMediaType(fileInfoVO.getName()).get());

        // 定义ResponseEntity封装返回信息
        return new ResponseEntity<>(fileInfoVO.getBase64(), header, HttpStatus.OK);
    }

    @ApiOperation(value = "文件服务：根据uuid查询图片二进制数据", notes = "根据uuid查询图片二进制数据")
    @GetMapping("uuid/{uuid}")
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
                header.add("Content-Disposition", "filename=" + filename);
                header.setContentType(MediaTypeFactory.getMediaType(filename).get());

                // 定义ResponseEntity封装返回信息
                return new ResponseEntity<>(downloadRt.getBytes(), header, HttpStatus.OK);

            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return entity;
    }

    @ApiOperation(value = "文件服务：根据path查询图片Base64数据", notes = "根据path查询图片Base64数据")
    @GetMapping("path/**")
    public ResponseEntity<String> getByPath(
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) throws Exception {

        log.info("getByPath => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);
        String path = request.getRequestURI().replace("/image/path/", "");
        // 1、执行文件删除操作
        FileInfoVO fileInfoVO = fileService.getByPath(path, Boolean.FALSE);

        // 定义http头 ，状态
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "filename=" + fileInfoVO.getName());
        header.setContentType(MediaTypeFactory.getMediaType(fileInfoVO.getName()).get());

        // 定义ResponseEntity封装返回信息
        return new ResponseEntity<>(fileInfoVO.getBase64(), header, HttpStatus.OK);
    }

    @ApiOperation(value = "文件服务：根据path查询图片二进制数据", notes = "根据path查询图片二进制数据")
    @GetMapping("path2/**")
    public ResponseEntity<byte[]> getByPath2(
            @RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
            @RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
            @RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
            @RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
            @ApiIgnore HttpServletRequest request) {

        ResponseEntity<byte[]> entity = null;
        try {
            String path = request.getRequestURI().replace("/image/path2/", "");
            log.info("downloadByPath => appId：{}, appChannel：{}, appVersion：{}, path：{}", appId, appChannel, appVersion, path);

            FileDownloadResult downloadRt = fileService.downloadByPath(path);
            if (Objects.nonNull(downloadRt)) {

                String filename = downloadRt.getFile().getName();

                // 定义http头 ，状态
                HttpHeaders header = new HttpHeaders();
                header.add("Content-Disposition", "filename=" + filename);
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
