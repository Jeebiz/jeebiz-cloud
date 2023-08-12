package io.hiwepy.cloud.authz.login.strategy;

import io.hiwepy.cloud.authz.login.bo.AuthBO;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 登录
 */
public interface AuthStrategy<T> {

    /**
     * 返回登陆所用渠道
     *
     * @return
     */
    AuthChannel getChannel();

    /**
     * 获取账号信息
     *
     * @param token
     * @return
     */
    AuthBO<T> initInfo(Authentication token) throws AuthenticationException;

    /**
     * 获取账号信息
     *
     * @param authBO
     * @return
     */
    AuthBO<T> initInfo(AuthBO<T> authBO) throws AuthenticationException;

    /**
     * 统一登陆
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    SecurityPrincipal login(Authentication token) throws AuthenticationException;

    SecurityPrincipal login(AuthBO<T> authBO) throws AuthenticationException;

    SecurityPrincipal runAs(AuthBO<T> authBO) throws AuthenticationException;

    SecurityPrincipal getPrincipal(AuthBO<T> authBO) throws AuthenticationException;

    void afterLogin(AuthBO<T> authBO, Long userId) throws AuthenticationException;

    void checkAndBind(AuthBO<T> authBO) throws AuthenticationException;

    void afterReg(AuthBO<T> authBO, Long userId) throws AuthenticationException;

}
