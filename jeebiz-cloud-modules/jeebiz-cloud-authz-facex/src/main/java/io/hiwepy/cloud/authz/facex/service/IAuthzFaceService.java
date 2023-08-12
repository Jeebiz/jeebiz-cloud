/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.service;

import com.alibaba.fastjson.JSONObject;
import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.facex.dao.entities.AuthzFaceEntity;
import io.hiwepy.cloud.authz.facex.web.dto.AuthzFaceQueryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IAuthzFaceService extends IBaseService<AuthzFaceEntity> {

    /**
     * 根据用户ID查询人脸数据
     * @param userId 用户ID
     * @return
     */
    AuthzFaceEntity getFaceModel(String userId);

    /**
     * 检测图片中的人脸并存储检查合格的人脸图片作为识别的底片
     * @param userId 用户ID
     * @param image 人脸图片文件
     * @return
     */
    JSONObject scanning(String userId, MultipartFile image) throws Exception;

    /**
     * 人脸检测与属性分析：检测图片中的人脸并标记出位置信息
     * @param image 人脸图片文件
     * @return
     */
    JSONObject detect(MultipartFile image) throws Exception;

    /**
     * 人脸对比：比对两张图片中人脸的相似度，并返回相似度分值
     * @param userId 用户ID
     * @param image 人脸图片文件
     * @return
     */
    JSONObject match(String userId, MultipartFile image) throws Exception;

    /**
     * 人脸搜索：在指定人脸集合中，找到最相似的人脸
     * @param image 人脸图片文件
     * @return
     */
    JSONObject search(MultipartFile image) throws Exception;

    /**
     * 人脸融合：对两张人脸进行融合处理，生成的人脸同时具备两张人脸的外貌特征（并不是换脸）
     * @param template 人脸融合模板图片文件
     * @param target 人脸融合人脸图片文件
     * @return
     */
    JSONObject merge(MultipartFile template, MultipartFile target) throws Exception;

    ResponseEntity<byte[]> download(AuthzFaceQueryDTO queryDTO) throws Exception;

}
