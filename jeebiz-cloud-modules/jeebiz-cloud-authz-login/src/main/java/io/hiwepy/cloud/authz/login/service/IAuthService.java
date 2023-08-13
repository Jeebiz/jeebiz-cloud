/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.login.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserAccountEntity;
import org.springframework.security.boot.biz.userdetails.UserProfilePayload;

import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPublicKey;

public interface IAuthService extends IBaseService<UserAccountEntity> {

    /**
     * 生成公钥
     * @param request
     * @return
     * @throws GeneralSecurityException
     */
    RSAPublicKey genPublicKey(HttpServletRequest request) throws GeneralSecurityException;

    /**
     * Token 登录
     * @param token
     * @param request
     * @return
     */
    UserProfilePayload loginForToken(String token, HttpServletRequest request);

    /**
     * 切换身份登录
     * @param identityId
     * @param deptId
     * @param token
     * @return
     */
    UserProfilePayload runAs(Integer identityId, Long deptId, String token);

    /**
     * 登录后的操作
     * @param <T>
     * @param authBO
     */
    <T> void afterLogin(AuthBO<T> authBO);
}
