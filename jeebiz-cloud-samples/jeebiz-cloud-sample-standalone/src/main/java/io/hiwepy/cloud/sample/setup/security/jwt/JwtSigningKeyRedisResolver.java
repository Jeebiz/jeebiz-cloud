/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.sample.setup.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.security.boot.jwt.exception.AuthenticationJwtIncorrectException;
import org.springframework.util.StringUtils;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.concurrent.ExecutionException;

/**
 * 	根据keyID获取对应的Key
 */
public class JwtSigningKeyRedisResolver extends SigningKeyResolverAdapter {

    private RedisOperationTemplate redisOperationTemplate;

    public JwtSigningKeyRedisResolver(RedisOperationTemplate redisOperationTemplate) {
        super();
        this.redisOperationTemplate = redisOperationTemplate;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {

        //inspect the header or claims, lookup and return the signing key
        String keyId = jwsHeader.getKeyId(); //or any other field that you need to inspect
        if (!StringUtils.hasText(keyId)) {
            throw new AuthenticationJwtIncorrectException("The header named 'kid' is required.");
        }
        try {
            return lookupVerificationKey(keyId); //implement me
        } catch (Exception e) {
            throw new AuthenticationJwtIncorrectException("The header named 'kid' is required.", e);
        }
    }

    protected Key lookupVerificationKey(String keyId) throws ExecutionException, GeneralSecurityException {

        // 获取应用基本信息Redis缓存
        //String appInfo = (String) getStringRedisTemplate().opsForHash().get(Constants.DSB_APPS, String.format("jwt-%s", keyId));
        // 根据应用uid获取应用信息：主要获取rsa公钥和rsa私钥
        //MyApplicationModel appModel = JSONObject.parseObject(appInfo, MyApplicationModel.class);

        // 第一种用法：公钥加密，私钥解密。---用于加解密
        // 第二种用法：私钥签名，公钥验签。---用于签名

        // 因为在管理端使用的是私钥签名，这里需要构建公钥对象以便进行jwt验证
        //RSAPublicKey publicKey = (RSAPublicKey) SecretKeyUtils.genPublicKey("RSA", Base64.decode(appModel.getAppKey()));

        //return publicKey;
        return null;

    }

    public RedisOperationTemplate getRedisOperationTemplate() {
        return redisOperationTemplate;
    }

}
