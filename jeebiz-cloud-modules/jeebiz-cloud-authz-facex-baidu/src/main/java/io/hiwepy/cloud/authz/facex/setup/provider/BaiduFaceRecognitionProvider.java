/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.setup.provider;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ai.aip.spring.boot.FaceLiveness;
import com.baidu.ai.aip.spring.boot.FaceOption;
import com.baidu.ai.aip.spring.boot.FaceRecognitionV3Template;
import com.baidu.ai.aip.spring.boot.FaceType;
import com.baidu.aip.util.Base64Util;
import io.hiwepy.cloud.authz.facex.dao.AuthzFaceMapper;
import io.hiwepy.cloud.authz.facex.dao.AuthzFaceRepositoryMapper;
import io.hiwepy.cloud.authz.facex.dao.entities.AuthzFaceEntity;
import io.hiwepy.cloud.authz.facex.setup.FaceProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BaiduFaceRecognitionProvider implements FaceRecognitionProvider {

    @Autowired
    private FaceRecognitionV3Template faceRecognitionTemplate;
    @Autowired
    private AuthzFaceMapper authzFace;
    @Autowired
    private AuthzFaceRepositoryMapper authzFaceRepository;

    @Override
    public FaceProvider getType() {
        return FaceProvider.BAIDU;
    }

    @Override
    public JSONObject detect(byte[] imageBytes, String filename) throws Exception {
        JSONObject detect = getFaceRecognitionTemplate().detect(imageBytes, FaceType.LIVE, FaceLiveness.NORMAL);
        if (StringUtils.equalsIgnoreCase(detect.getString("error_code"), "0") && detect.containsKey("result")) {
            JSONObject result = detect.getJSONObject("result");
            result.put("error_code", 0);
            return result;
        }
        return detect;
    }

    @Override
    public JSONObject verify(byte[] imageBytes) throws Exception {
        // 对文件进行转码
        String imageBase64 = Base64Util.encode(imageBytes);
        JSONObject verify = getFaceRecognitionTemplate().faceVerify(imageBase64, FaceOption.COMMON);
        if (StringUtils.equalsIgnoreCase(verify.getString("error_code"), "0") && verify.containsKey("result")) {
            JSONObject result = verify.getJSONObject("result");
            result.put("error_code", 0);
            return result;
        }
        return verify;
    }

    @Override
    public JSONObject match(String userId, String filename, byte[] imageBytes) throws Exception {
        AuthzFaceEntity model = getAuthzFace().selectById(userId);
        String imageBase64_2 = Base64Util.encode(imageBytes);
        JSONObject match = getFaceRecognitionTemplate().match(model.getFace(), imageBase64_2);
        if (StringUtils.equalsIgnoreCase(match.getString("error_code"), "0") && match.containsKey("result")) {
            JSONObject result = match.getJSONObject("result");
            result.put("error_code", 0);
            return result;
        }
        return match;
    }

    @Override
    public JSONObject search(String filename, byte[] imageBytes) throws Exception {
        // 对文件进行转码
        String imageBase64 = Base64Util.encode(imageBytes);
        JSONObject search = getFaceRecognitionTemplate().search(imageBase64, "");
        if (StringUtils.equalsIgnoreCase(search.getString("error_code"), "0") && search.containsKey("result")) {
            JSONObject result = search.getJSONObject("result");
            result.put("error_code", 0);
            return result;
        }
        return search;
    }

    @Override
    public JSONObject merge(byte[] template, byte[] target) throws Exception {
        JSONObject merge = getFaceRecognitionTemplate().merge(template, target);
        if (StringUtils.equalsIgnoreCase(merge.getString("error_code"), "0") && merge.containsKey("result")) {
            JSONObject result = merge.getJSONObject("result");
            result.put("error_code", 0);
            return result;
        }
        return merge;
    }

    public FaceRecognitionV3Template getFaceRecognitionTemplate() {
        return faceRecognitionTemplate;
    }

    public AuthzFaceMapper getAuthzFace() {
        return authzFace;
    }

    public AuthzFaceRepositoryMapper getAuthzFaceRepository() {
        return authzFaceRepository;
    }

}
