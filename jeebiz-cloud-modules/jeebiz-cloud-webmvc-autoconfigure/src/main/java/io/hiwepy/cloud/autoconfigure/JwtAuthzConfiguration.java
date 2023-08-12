/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.autoconfigure;

import com.github.hiwepy.jwt.time.JwtTimeProvider;
import com.github.hiwepy.jwt.token.SignedWithSecretKeyJWTRepository;
import com.github.hiwepy.jwt.token.SignedWithSecretResolverJWTRepository;
import com.github.hiwepy.jwt.utils.SecretKeyUtils;
import io.hiwepy.cloud.autoconfigure.security.jwt.DefaultJwtPayloadRepository;
import io.hiwepy.cloud.autoconfigure.security.jwt.JwtSigningKeyRedisResolver;
import io.hiwepy.cloud.autoconfigure.security.jwt.JwtTimeRedisProvider;
import io.jsonwebtoken.JwtClock;
import io.jsonwebtoken.SigningKeyResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.security.boot.biz.userdetails.JwtPayloadRepository;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
public class JwtAuthzConfiguration {

    /**
     * JWT 秘钥
     */
    @Value("${jwt.secret.key:6Tb9jCzN1jXppMrsLYfXbERiGiGp4nNtXuVdTGV9qN0}")
    private String jwtSecretKey;
    /**
     * JWT 秘钥
     */
    @Value("${jwt.secret.algorithm:HmacSHA256}")
    private String jwtSecretAlgorithm;
    /**
     * JWT 签发者
     */
    @Value("${jwt.issuer:hiwepy.com}")
    private String jwtIssuer;
    /**
     * JWT 加密算法
     */
    @Value("${jwt.algorithm:HS256}")
    private String jwtAlgorithm;
    /**
     * 过期时间（7天），单位毫秒
     */
    @Value("${jwt.period:30240000}")
    private Long jwtPeriod;

    @Bean
    @ConditionalOnMissingBean
    public SigningKeyResolver signingKeyResolver(RedisOperationTemplate redisOperationTemplate) {
        return new JwtSigningKeyRedisResolver(redisOperationTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public SignedWithSecretResolverJWTRepository secretResolverJWTRepository(SigningKeyResolver signingKeyResolver) {
        return new SignedWithSecretResolverJWTRepository(signingKeyResolver);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtTimeProvider jwtTimeProvider(RedisOperationTemplate redisOperation) {
        return new JwtTimeRedisProvider(redisOperation);
    }

    @Bean
    public JwtClock jwtClock(JwtTimeProvider timeProvider) {
        JwtClock clock = new JwtClock();
        clock.setTimeProvider(timeProvider);
        return clock;
    }

    @Bean
    @ConditionalOnMissingBean
    public SignedWithSecretKeyJWTRepository secretKeyJWTRepository(JwtClock jwtClock) {
        SignedWithSecretKeyJWTRepository jWTRepository = new SignedWithSecretKeyJWTRepository();
        jWTRepository.setClock(jwtClock);
        return jWTRepository;
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtPayloadRepository jwtPayloadRepository(SignedWithSecretKeyJWTRepository secretKeyJWTRepository,
                                                     RedisOperationTemplate redisOperationTemplate) throws Exception {
        byte[] secretKeyBytes = Base64.getDecoder().decode(jwtSecretKey);
        SecretKey jwtSecretKey = SecretKeyUtils.genSecretKey(secretKeyBytes, jwtSecretAlgorithm);
        return new DefaultJwtPayloadRepository(jwtSecretKey, jwtIssuer, jwtAlgorithm, jwtPeriod, secretKeyJWTRepository, redisOperationTemplate);
    }

}
