/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hitool.core.format.ByteUnitFormat;
import hitool.core.lang3.time.DateUtils;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.boot.api.utils.CollectionUtils;
import io.hiwepy.cloud.authz.facex.dao.AuthzFaceMapper;
import io.hiwepy.cloud.authz.facex.dao.entities.AuthzFaceEntity;
import io.hiwepy.cloud.authz.facex.service.IAuthzFaceService;
import io.hiwepy.cloud.authz.facex.setup.FaceType;
import io.hiwepy.cloud.authz.facex.setup.provider.FaceRecognitionProvider;
import io.hiwepy.cloud.authz.facex.web.dto.AuthzFaceQueryDTO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class AuthzFaceServiceImpl extends BaseServiceImpl<AuthzFaceMapper, AuthzFaceEntity> implements IAuthzFaceService {

    @Autowired
    private FaceRecognitionProvider faceRecognitionProvider;

    @Override
    public AuthzFaceEntity getFaceModel(String userId) {
        return getBaseMapper().getFaceModel(userId);
    }

    private byte[] scale(byte[] imageBytes) throws IOException {

        double imageSize = ByteUnitFormat.B.toDouble(ByteUnitFormat.M, imageBytes.length);
        // 图片大于2M,则进行压缩
        if (imageSize >= 2) {
            double scale = 1D / imageSize;
            if (imageSize >= 15) {
                scale = 5 / imageSize;
            } else if (imageSize >= 10) {
                scale = 4D / imageSize;
            } else if (imageSize >= 4) {
                scale = 2D / imageSize;
            } else {
                scale = 1D / imageSize;
            }
            try (ByteArrayInputStream input = new ByteArrayInputStream(imageBytes);
                 ByteArrayOutputStream output = new ByteArrayOutputStream();) {
                Thumbnails.of(input).scale(scale).toOutputStream(output);
                return output.toByteArray();
            }
        }
        return imageBytes;
    }

    @Override
    public JSONObject scanning(String userId, MultipartFile image) throws Exception {
        byte[] imageBytes = this.scale(image.getBytes());
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        JSONObject detect = getFaceRecognitionProvider().detect(imageBytes, image.getOriginalFilename());
        if (StringUtils.equalsIgnoreCase(detect.getString("error_code"), "0")) {

            //int  face_num = result.getIntValue("face_num");
            JSONArray face_list = detect.getJSONArray("face_list");
            JSONObject face = face_list.getJSONObject(0);

            String face_token = face.getString("face_token");

            AuthzFaceEntity model = new AuthzFaceEntity();

            model.setRepoId("11");
            model.setUserId(userId);
            model.setType(FaceType.LIVE.name());
            model.setFace(imageBase64);
            model.setToken(face_token);

            // 判断是否已经保存过面部识别结果
            Long ct = getBaseMapper().getCountByUid(userId);
            if (ct > 0) {
                getBaseMapper().updateById(model);
            } else {
                getBaseMapper().insert(model);
            }
            detect.put("imageBase64", imageBase64);
        }

        return detect;
    }

    @Override
    public JSONObject detect(MultipartFile image) throws Exception {
        byte[] imageBytes = this.scale(image.getBytes());
        return getFaceRecognitionProvider().detect(imageBytes, image.getOriginalFilename());
    }

    @Override
    public JSONObject match(String userId, MultipartFile image) throws Exception {
        byte[] imageBytes = this.scale(image.getBytes());
        return getFaceRecognitionProvider().match(userId, image.getOriginalFilename(), imageBytes);
    }

    @Override
    public JSONObject search(MultipartFile image) throws Exception {
        byte[] imageBytes = this.scale(image.getBytes());
        return getFaceRecognitionProvider().search(image.getOriginalFilename(), imageBytes);
    }

    @Override
    public JSONObject merge(MultipartFile template, MultipartFile target) throws Exception {
        return getFaceRecognitionProvider().merge(this.scale(template.getBytes()), this.scale(target.getBytes()));
    }

    @Override
    public ResponseEntity<byte[]> download(AuthzFaceQueryDTO queryDTO) throws Exception {

        AuthzFaceEntity queryModel = getBeanMapper().map(queryDTO, AuthzFaceEntity.class);
        // 按条件查询学生数据
        List<AuthzFaceEntity> retList = getBaseMapper().getEntityList(queryModel);
        // 有数据
        if (CollectionUtils.isNotEmpty(retList)) {
            log.debug("此次下载学生照片{} 张！", retList.size());
            // 创建输出流
            //try{
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ZipOutputStream outzip = new ZipOutputStream(output, StandardCharsets.UTF_8);
            outzip.setComment("学生照片");
            // 循环对象
            for (AuthzFaceEntity model : retList) {
                byte[] imageBytes = Base64.getDecoder().decode(model.getFace());
                String filename = String.format("%s-%s.jpg", model.getUserId(), model.getNickname());
                log.debug("照片{} ", filename);
                /**
                 * 压缩包内文件名定义
                 * <pre>
                 * 如果有多级目录，那么这里就需要给出包含目录的文件名
                 * 如果用WinRAR打开压缩包，中文名将显示为乱码
                 * </pre>
                 */
                ZipEntry zipEntry = new ZipEntry(filename);
                zipEntry.setComment(filename);
                outzip.putNextEntry(zipEntry);
                if (imageBytes != null && imageBytes.length > 0) {
                    outzip.write(imageBytes);
                }
                outzip.closeEntry();
            }

            // 将流关闭
            outzip.finish();
            outzip.flush();
            outzip.close();

            // 定义http头 ，状态
            HttpHeaders header = new HttpHeaders();
            String filename = String.format("学生照片-%s.zip", DateUtils.formatDate(new Date(), "yyyyMMdd"));
            header.add("Content-Disposition", "attchement;filename=" + URLEncoder.encode(filename, "UTF-8"));
            header.add("Content-Type", "application/zip");
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            // 定义ResponseEntity封装返回信息
            return new ResponseEntity<>(output.toByteArray(), header, HttpStatus.OK);
            //}
        }
        // 定义http头 ，状态
        HttpHeaders header = new HttpHeaders();
        String filename = String.format("学生照片-%s.txt", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        header.add("Content-Disposition", "attchement;filename=" + URLEncoder.encode(filename, "UTF-8"));
        header.setContentType(MediaTypeFactory.getMediaType(filename).get());
        // 定义ResponseEntity封装返回信息
        return new ResponseEntity<byte[]>("当前筛选条件没有符合的学生数据！".getBytes(), header, HttpStatus.OK);
    }

    public FaceRecognitionProvider getFaceRecognitionProvider() {
        return faceRecognitionProvider;
    }


}
