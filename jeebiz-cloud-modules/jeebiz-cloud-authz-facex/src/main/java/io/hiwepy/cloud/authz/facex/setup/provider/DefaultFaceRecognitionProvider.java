package io.hiwepy.cloud.authz.facex.setup.provider;

import com.alibaba.fastjson.JSONObject;
import io.hiwepy.boot.api.exception.BizRuntimeException;
import io.hiwepy.cloud.authz.facex.setup.FaceProvider;

public class DefaultFaceRecognitionProvider implements FaceRecognitionProvider {

    @Override
    public FaceProvider getType() {
        return FaceProvider.UNKNOWN;
    }

    @Override
    public JSONObject detect(byte[] imageBytes, String filename) throws Exception {
        throw new BizRuntimeException("not support");
    }

    @Override
    public JSONObject verify(byte[] imageBytes) throws Exception {
        throw new BizRuntimeException("not support");
    }

    @Override
    public JSONObject match(String userId, String filename, byte[] imageBytes) throws Exception {
        return null;
    }

    @Override
    public JSONObject search(String filename, byte[] imageBytes) throws Exception {
        throw new BizRuntimeException("not support");
    }

    @Override
    public JSONObject merge(byte[] template, byte[] target) throws Exception {
        throw new BizRuntimeException("not support");
    }

}
