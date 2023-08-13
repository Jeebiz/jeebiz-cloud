package io.hiwepy.cloud.authz.login.exception;

import org.springframework.security.boot.biz.exception.AuthResponseCode;
import org.springframework.security.boot.biz.exception.AuthenticationExceptionAdapter;

public class ThirdUserNotFoundException extends AuthenticationExceptionAdapter {

    public ThirdUserNotFoundException(String msg) {
        super(AuthResponseCode.SC_AUTHC_FAIL.getCode(), msg, null);
    }

    public ThirdUserNotFoundException(String msg, Throwable t) {
        super(AuthResponseCode.SC_AUTHC_FAIL.getCode(), msg, t);
    }
}
