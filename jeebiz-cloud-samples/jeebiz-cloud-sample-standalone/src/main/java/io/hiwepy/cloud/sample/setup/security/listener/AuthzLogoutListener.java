/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.sample.setup.security.listener;

import io.hiwepy.boot.api.Constants;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.spring.boot.utils.Log4jUtils;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 会话注销日志记录：
 * 更多的参数信息可参见{@link org.apache.logging.log4j.spring.boot.ext.web.Log4j2MDCInterceptor}
 */
@Component
public class AuthzLogoutListener implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);

        ThreadContext.put("userid", principal.getUid());
        ThreadContext.put("username", principal.getUsername());
        ThreadContext.put("location", "");

        Log4jUtils.instance("io.hiwepy.authz").error(Constants.authzMarker, String.format("用户:%s账户注销失败.", principal.getUsername()));

    }

}