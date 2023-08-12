/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.setup.provider;

import com.alibaba.fastjson.JSONObject;
import com.arcsoft.face.spring.boot.ArcFaceRecognitionTemplate;
import com.arcsoft.face.spring.boot.FaceLiveness;
import io.hiwepy.boot.api.exception.BizRuntimeException;
import io.hiwepy.cloud.authz.facex.dao.AuthzFaceMapper;
import io.hiwepy.cloud.authz.facex.dao.AuthzFaceRepositoryMapper;
import io.hiwepy.cloud.authz.facex.dao.entities.AuthzFaceEntity;
import io.hiwepy.cloud.authz.facex.setup.FaceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ArcFaceRecognitionProvider implements FaceRecognitionProvider {

    @Autowired
    private ArcFaceRecognitionTemplate faceRecognitionTemplate;
    @Autowired
    private AuthzFaceMapper authzFace;
    @Autowired
    private AuthzFaceRepositoryMapper authzFaceRepository;

    @Override
    public FaceProvider getType() {
        return FaceProvider.ARCSOFT;
    }

    @Override
    public JSONObject detect(byte[] imageBytes, String filename) throws Exception {
        return getFaceRecognitionTemplate().detect(imageBytes);
    }

    @Override
    public JSONObject verify(byte[] imageBytes) throws Exception {
        return getFaceRecognitionTemplate().verify(imageBytes, FaceLiveness.LOW);
    }

    @Override
    public JSONObject match(String userId, String filename, byte[] imageBytes) throws Exception {
        AuthzFaceEntity model = getAuthzFace().selectById(userId);
        return getFaceRecognitionTemplate().match(imageBytes, Base64.getDecoder().decode(model.getFace()), FaceLiveness.NONE);
    }

    @Override
    public JSONObject search(String filename, byte[] imageBytes) throws Exception {
        throw new BizRuntimeException("not support");
    }

    @Override
    public JSONObject merge(byte[] template, byte[] target) throws Exception {
        throw new BizRuntimeException("not support");
    }

    public ArcFaceRecognitionTemplate getFaceRecognitionTemplate() {
        return faceRecognitionTemplate;
    }

    public AuthzFaceMapper getAuthzFace() {
        return authzFace;
    }

    public AuthzFaceRepositoryMapper getAuthzFaceRepository() {
        return authzFaceRepository;
    }

}
