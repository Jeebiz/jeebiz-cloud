/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.autoconfigure.security.jwt;

import com.github.hiwepy.jwt.JwtClaims;
import com.github.hiwepy.jwt.JwtPayload;
import com.github.hiwepy.jwt.exception.ExpiredJwtException;
import com.github.hiwepy.jwt.exception.IncorrectJwtException;
import com.github.hiwepy.jwt.exception.InvalidJwtToken;
import com.github.hiwepy.jwt.exception.JwtException;
import com.github.hiwepy.jwt.token.SignedWithSecretKeyJWTRepository;
import hitool.core.lang3.time.DateUtils;
import io.hiwepy.cloud.api.UserProfiles;
import io.hiwepy.cloud.base.redis.BizRedisKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.boot.biz.userdetails.JwtPayloadRepository;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.biz.userdetails.UserProfilePayload;
import org.springframework.security.boot.jwt.exception.AuthenticationJwtExpiredException;
import org.springframework.security.boot.jwt.exception.AuthenticationJwtIncorrectException;
import org.springframework.security.boot.jwt.exception.AuthenticationJwtInvalidException;
import org.springframework.security.boot.jwt.exception.AuthenticationJwtIssuedException;
import org.springframework.security.core.AuthenticationException;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class DefaultJwtPayloadRepository implements JwtPayloadRepository, InitializingBean {

    /**
     * 过期时间（7天），单位毫秒
     */
    private static final long EXPIRE_TIME = 7 * DateUtils.MILLIS_PER_DAY;
    /**
     * JWT 秘钥
     */
    private final SecretKey jwtSecretKey;
    /**
     * JWT 签发者
     */
    private final String jwtIssuer;
    /**
     * JWT 加密算法
     */
    private final String jwtAlgorithm;
    /**
     * JWT 过期时间（7天），单位毫秒
     */
    private final long jwtPeriod;

    private final SignedWithSecretKeyJWTRepository secretKeyJWTRepository;
    private final RedisOperationTemplate redisOperationTemplate;

    public DefaultJwtPayloadRepository(SecretKey jwtSecretKey,
                                       String jwtIssuer,
                                       String jwtAlgorithm,
                                       Long jwtPeriod,
                                       SignedWithSecretKeyJWTRepository secretKeyJWTRepository,
                                       RedisOperationTemplate redisOperationTemplate) {
        super();
        this.jwtSecretKey = jwtSecretKey;
        this.jwtIssuer = jwtIssuer;
        this.jwtAlgorithm = jwtAlgorithm;
        this.jwtPeriod = Optional.ofNullable(jwtPeriod).orElse(EXPIRE_TIME);
        this.secretKeyJWTRepository = secretKeyJWTRepository;
        this.redisOperationTemplate = redisOperationTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public String issueJwt(AbstractAuthenticationToken token) {

        SecurityPrincipal principal = (SecurityPrincipal) token.getPrincipal();
        return this.issueJwt(principal);
    }

    @Override
    public String issueJwt(SecurityPrincipal principal) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(JwtClaims.UID, principal.getUid());
        claims.put(JwtClaims.UUID, principal.getUuid());
        claims.put(JwtClaims.RID, principal.getRid());
        claims.put(JwtClaims.RKEY, principal.getRkey());
        claims.put(JwtClaims.BOUND, principal.isBound());
        claims.put(JwtClaims.INITIAL, principal.isInitial());
        //claims.put(JwtClaims.VERIFY, principal.isVerify());

        // 4、查询是否缓存（在线用户缓存数据）
        if (MapUtils.isNotEmpty(principal.getProfile())) {

            Map<String, Object> profile = new HashMap<>();
            profile.put(UserProfiles.ACCOUNT_ID, MapUtils.getString(principal.getProfile(), UserProfiles.ACCOUNT_ID));
            profile.put(UserProfiles.USER_ID, MapUtils.getString(principal.getProfile(), UserProfiles.USER_ID));
            profile.put(UserProfiles.ORG_ID, MapUtils.getString(principal.getProfile(), UserProfiles.ORG_ID));
            profile.put(UserProfiles.DEPT_ID, MapUtils.getString(principal.getProfile(), UserProfiles.DEPT_ID));
            profile.put(UserProfiles.IDENTITY_ID, MapUtils.getString(principal.getProfile(), UserProfiles.IDENTITY_ID));
            profile.put(UserProfiles.PHONE, MapUtils.getString(principal.getProfile(), UserProfiles.PHONE));
            profile.put(UserProfiles.NICKNAME, MapUtils.getString(principal.getProfile(), UserProfiles.NICKNAME));
            profile.put(UserProfiles.IDCARD, MapUtils.getString(principal.getProfile(), UserProfiles.IDCARD));
            claims.put(UserProfiles.PROFILE, profile);

        }

        return this.issueJwt(principal.getUid(), claims);
    }

    @Override
    public String issueJwt(String uid, Map<String, Object> claims) {

        try {

            // 1、签发Token并进行Redis缓存
            String jwtId = BizRedisKey.USER_TOKEN.getKey(uid);

            String jwtString = getSecretKeyJWTRepository().issueJwt(jwtSecretKey, jwtId, uid,
                    jwtIssuer, uid, claims, jwtAlgorithm, -1);

            getRedisOperationTemplate().set(jwtId, jwtString, Duration.ofDays(7));

            return jwtString;

        } catch (JwtException e) {
            throw new AuthenticationJwtIssuedException("JWT issue error");
        }

    }

    @Override
    public boolean verify(AbstractAuthenticationToken token, boolean checkExpiry) throws AuthenticationException {
        String jwtString = String.valueOf(token.getCredentials());
        return this.verify(jwtString, checkExpiry);
    }

    @Override
    public boolean verify(String token, boolean checkExpiry) throws AuthenticationException {
        try {
            return getSecretKeyJWTRepository().verify(jwtSecretKey, token, checkExpiry);
        } catch (ExpiredJwtException e) {
            throw new AuthenticationJwtExpiredException("JWT has expired");
        } catch (InvalidJwtToken e) {
            throw new AuthenticationJwtInvalidException("JWT has invalid");
        } catch (IncorrectJwtException e) {
            throw new AuthenticationJwtIncorrectException("JWT has incorrect");
        }
    }

    @Override
    public JwtPayload getPayload(AbstractAuthenticationToken token, boolean checkExpiry) {
        String jwtString = String.valueOf(token.getCredentials());
        return this.getPayload(jwtString, checkExpiry);
    }

    @Override
    public JwtPayload getPayload(String token, boolean checkExpiry) {
        try {

            // 1、解析并检查jwt
            JwtPayload payload = getSecretKeyJWTRepository().getPlayload(jwtSecretKey, token, false);
            // 2、检查jwt是否过期
            if (!getRedisOperationTemplate().hasKey(payload.getTokenId())) {
                throw new AuthenticationJwtExpiredException("JWT has expired");
            }
            // 3、查询是否用户被禁用
            String userKey = BizRedisKey.USER_INFO.getKey(payload.getSubject());
            Integer disabled = redisOperationTemplate.hGetInteger(userKey, UserProfiles.DISABLED);
            if (Objects.nonNull(disabled) && disabled == 0) {
                throw new DisabledException("账号已被禁用，请联系管理员！");
            }

            return payload;
        } catch (ExpiredJwtException e) {
            throw new AuthenticationJwtExpiredException("JWT has expired");
        } catch (InvalidJwtToken e) {
            throw new AuthenticationJwtInvalidException("JWT has invalid");
        } catch (IncorrectJwtException e) {
            throw new AuthenticationJwtIncorrectException("JWT has incorrect");
        }
    }


    @Override
    public UserProfilePayload getProfilePayload(AbstractAuthenticationToken token, boolean checkExpiry) {

        // 利用登陆用户信息
        SecurityPrincipal principal = (SecurityPrincipal) token.getPrincipal();

        String tokenString = this.issueJwt(token);

        UserProfilePayload payload = principal.toPayload();
        payload.setToken(tokenString);

        return payload;

    }

    public RedisOperationTemplate getRedisOperationTemplate() {
        return redisOperationTemplate;
    }

    public SignedWithSecretKeyJWTRepository getSecretKeyJWTRepository() {
        return secretKeyJWTRepository;
    }


}
