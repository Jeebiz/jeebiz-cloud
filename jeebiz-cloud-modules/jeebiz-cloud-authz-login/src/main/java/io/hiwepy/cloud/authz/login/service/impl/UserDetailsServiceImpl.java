/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.login.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.param.LoginByAccountParam;
import io.hiwepy.cloud.authz.login.param.LoginByUserIdParam;
import io.hiwepy.cloud.authz.login.strategy.AuthChannel;
import io.hiwepy.cloud.authz.login.strategy.AuthStrategyRouter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.boot.biz.userdetails.UserDetailsServiceAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserDetailsServiceImpl extends UserDetailsServiceAdapter {

    @Autowired
    private AuthStrategyRouter authStrategyRouter;

    @Override
    @Transactional
    public UserDetails loadUserDetails(String userId) throws AuthenticationException {
        return this.loadUserDetails(userId, null);
    }

    @Override
    @Transactional
    public UserDetails loadUserDetails(String userId, String roleId) {
        log.info("{} >> loadUserDetails roleId:{}", userId, roleId);
        AuthBO<LoginByUserIdParam> authBO = AuthBO.<LoginByUserIdParam>builder()
                .accountId(Long.getLong(userId))
                .roleId(Long.getLong(roleId))
                .channel(AuthChannel.USER_ID)
                .build();

        LoginByUserIdParam param = new LoginByUserIdParam();
        param.setUserId(Long.getLong(userId));
        param.setRoleId(roleId);
        authBO.setParam(param);

        return authStrategyRouter.route(authBO).login(authBO);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String account) throws AuthenticationException {
        return this.loadUserDetailsWithoutPwd(account);
    }

    @Override
    @Transactional
    public UserDetails loadUserDetailsWithoutPwd(String account) throws AuthenticationException {
        log.info("{} >> loadUserDetails ", account);
        AuthBO<LoginByAccountParam> authBO = AuthBO.<LoginByAccountParam>builder()
                .account(account)
                .channel(AuthChannel.ACCOUNT)
                .build();

        LoginByAccountParam param = new LoginByAccountParam();
        param.setAccount(account);
        authBO.setParam(param);

        return authStrategyRouter.route(authBO).login(authBO);
    }

    @Override
    @Transactional
    public UserDetails loadUserDetails(Authentication token) throws AuthenticationException {
        log.info("{} >> loadUserDetails token:{}", token.getName(), JSONObject.toJSONString(token, SerializerFeature.WriteNonStringValueAsString));
        return authStrategyRouter.route(token).login(token);
    }

}
