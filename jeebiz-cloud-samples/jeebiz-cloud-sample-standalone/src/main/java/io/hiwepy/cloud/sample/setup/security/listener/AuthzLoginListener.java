/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.sample.setup.security.listener;

import com.github.hiwepy.ip2region.spring.boot.IP2regionTemplate;
import io.hiwepy.boot.api.Constants;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.spring.boot.utils.Log4jUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.WebUtils;
import org.springframework.security.boot.biz.authentication.AuthenticationListener;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.RemoteAddrUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * 登录认证日志记录：
 * 更多的参数信息可参见{@link org.apache.logging.log4j.spring.boot.ext.web.Log4j2MDCInterceptor}
 */
@Component
public class AuthzLoginListener implements AuthenticationListener {

    @Autowired
    private IP2regionTemplate ip2regionTemplate;

    @Override
    public void onSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        ThreadContext.put("uuid", UUID.randomUUID().toString());
        ThreadContext.put("requestURL", request.getRequestURL().toString());
        ThreadContext.put("requestURI", request.getRequestURI());
        ThreadContext.put("queryString", request.getQueryString());
        ThreadContext.put("remoteAddr", RemoteAddrUtils.getRemoteAddr(request));
        ThreadContext.put("remoteHost", request.getRemoteHost());
        ThreadContext.put("remotePort", String.valueOf(request.getRemotePort()));
        ThreadContext.put("localAddr", request.getLocalAddr());
        ThreadContext.put("localName", request.getLocalName());

        SecurityPrincipal principal = (SecurityPrincipal) authentication.getPrincipal();
        ThreadContext.put("userId", principal.getUid());
        ThreadContext.put("opt", "login");
        ThreadContext.put("protocol", "JWT");
        ThreadContext.put("status", "success");
        ThreadContext.put("username", principal.getUsername());
        ThreadContext.put("location", ip2regionTemplate.getRegion(WebUtils.getRemoteAddr(request)));

        Log4jUtils.instance("io.hiwepy.authz").info(Constants.authzMarker, String.format("账户:%s登录成功.", principal.getUsername()));

    }

    @Override
    public void onFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ae) {
        // TODO Auto-generated method stub

        String userid = "";

        ThreadContext.put("userid", userid);
        ThreadContext.put("location", "");

        //Log4jUtils.instance("io.hiwepy.authz").error(Constants.authzMarker, String.format("账户:%s登录失败.", userid), ae);
    }

}
