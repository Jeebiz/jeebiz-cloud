/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.login.service.impl;


import cn.hutool.crypto.symmetric.AES;
import hitool.core.lang3.time.DateFormats;
import io.hiwepy.boot.api.XHeaders;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.boot.api.utils.WebUtils;
import io.hiwepy.cloud.api.UserProfiles;
import io.hiwepy.cloud.api.dto.AuthzLogNewDTO;
import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.param.LoginByTokenParam;
import io.hiwepy.cloud.authz.login.service.IAuthService;
import io.hiwepy.cloud.authz.login.strategy.AuthChannel;
import io.hiwepy.cloud.authz.login.strategy.AuthOptEnum;
import io.hiwepy.cloud.authz.login.strategy.AuthStrategyRouter;
import io.hiwepy.cloud.authz.rbac.dao.UserAccountMapper;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserAccountEntity;
import io.hiwepy.cloud.authz.rbac.service.IUserAccountService;
import io.hiwepy.cloud.authz.rbac.service.IUserIdentityService;
import io.hiwepy.cloud.authz.rbac.service.IUserProfileService;
import io.hiwepy.cloud.base.logs.service.IAuthzLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.boot.biz.userdetails.JwtPayloadRepository;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.biz.userdetails.UserProfilePayload;
import org.springframework.security.boot.jwt.authentication.JwtAuthorizationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class AuthServiceImpl extends BaseServiceImpl<UserAccountMapper, UserAccountEntity> implements IAuthService {

    public static final String PRIVATE_KEY_ATTRIBUTE_NAME = "privateKey";

    //  加密方式：AES
//  加密模式：CBC
//  填充方式：pkcs5padding
//  数据块：128位
//  协定密码(KEY)：1234123412ABCDEF
//  偏移量(IV)：ABCDEF1234123412
//  输出16进制字符串
//  编码：utf-8
    private final AES aes = new AES(
            "CBC",
            "PKCS7Padding",
            "1234123412ABCDEF".getBytes(),
            "ABCDEF1234123412".getBytes()
    );

    @Autowired
    private IUserProfileService userProfileService;
    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IUserIdentityService userIdentityService;
    @Autowired
    private AuthStrategyRouter authStrategyRouter;
    @Autowired
    private JwtPayloadRepository jwtPayloadRepository;
    @Autowired
    private IAuthzLogService authzLogService;

    @Override
    public RSAPublicKey genPublicKey(HttpServletRequest request) throws GeneralSecurityException {
		
		/*KeyPair keyPair = SecretKeyUtils.genKeyPair("RSA");
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		
		SubjectUtils.getSession().setAttribute(PRIVATE_KEY_ATTRIBUTE_NAME, privateKey);
		
		return publicKey;*/
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserProfilePayload loginForToken(String token, HttpServletRequest request) {
        // 1、构造登录token对象
        JwtAuthorizationToken aToken = new JwtAuthorizationToken(null, token);
        // 2、进入策略逻辑
        SecurityPrincipal ret = authStrategyRouter.route(aToken).login(aToken);
        // 3、生成新的token，返回登录结果对象
        String tokenString = jwtPayloadRepository.issueJwt(ret);
        UserProfilePayload payload = ret.toPayload();
        payload.setToken(tokenString);
        return payload;
    }

    @Override
    public UserProfilePayload runAs(Integer identityId, Long deptId, String token) {

        AuthBO<LoginByTokenParam> authBO = AuthBO.<LoginByTokenParam>builder()
                .deptId(deptId)
                .identityId(identityId)
                .channel(AuthChannel.TOKEN)
                .build();
        LoginByTokenParam param = new LoginByTokenParam();
        param.setToken(token);
        authBO.setParam(param);

        SecurityPrincipal ret = authStrategyRouter.route(authBO).runAs(authBO);
        Map<String, Object> profile = ret.getProfile();
        profile.put(UserProfiles.DEPT_ID, deptId);

        String tokenString = jwtPayloadRepository.issueJwt(ret);
        UserProfilePayload payload = ret.toPayload();
        payload.setToken(tokenString);
        return payload;
    }

    @Override
    public <T> void afterLogin(AuthBO<T> authBO) {
        try {
            // 1、获取 Request对象，解析请求来源
            HttpServletRequest request = WebUtils.getHttpServletRequest();
            String ipAddress = "";
            if (Objects.nonNull(request)) {
                ipAddress = WebUtils.getRemoteAddr(request);
                request.getHeader(XHeaders.X_APP_ID);
            }
            AuthzLogNewDTO logDto = AuthzLogNewDTO.builder()
                    .appChannel(authBO.getAppChannel())
                    .appVersion(authBO.getAppVer())
                    .appId(authBO.getAppId())
                    .userId(authBO.getUserId().toString())
                    .opt(AuthOptEnum.LOGIN.getKey())
                    .addr(ipAddress)
                    .msg(authBO.getChannel().getDesc())
                    //.addr(authBO.getIpAddress())
                    .level("info")
                    .protocol("JWT")
                    .status("success")
                    .time24(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormats.TIMESTAMP_FORMAT)))
                    .build();
            authzLogService.saveLog(logDto);
        } catch (Exception e) {
            log.error("Auth Log Error!", e);
        }
    }

}
