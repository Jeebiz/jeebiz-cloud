/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.autoconfigure.security;

import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.autoconfigure.webflux.ExceptinHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.boot.biz.SpringSecurityBizMessageSource;
import org.springframework.security.boot.biz.exception.*;
import org.springframework.security.boot.jwt.exception.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
@Slf4j
public class SecurityExceptinHandler extends ExceptinHandler {

    protected final MessageSourceAccessor messages = SpringSecurityBizMessageSource.getAccessor();

    // --- Security Default Error ---

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiRestResponse<String>> accessDeniedException(AccessDeniedException ex) {
        this.logException(ex);
        return new ResponseEntity<>(ApiCode.SC_ACCESS_DENIED.toResponse(), HttpStatus.OK);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationException(AuthenticationException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHC_FAIL, ex);
    }


    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationServiceException.class})
    @ResponseBody
    public ResponseEntity<ApiRestResponse<String>> authenticationServiceException(AuthenticationServiceException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHZ_THIRD_PARTY_SERVICE, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ResponseEntity<ApiRestResponse<String>> internalAuthenticationServiceException(InternalAuthenticationServiceException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHZ_THIRD_PARTY_SERVICE, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ApiRestResponse<String>> usernameNotFoundException(UsernameNotFoundException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHC_ACCOUNT_NOT_FOUND, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiRestResponse<String>> badCredentialsException(BadCredentialsException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHC_BAD_CREDENTIALS, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({DisabledException.class})
    public ResponseEntity<ApiRestResponse<String>> disabledException(DisabledException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHC_ACCOUNT_DISABLED, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({LockedException.class})
    public ResponseEntity<ApiRestResponse<String>> lockedException(LockedException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHC_ACCOUNT_LOCKED, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AccountExpiredException.class})
    public ResponseEntity<ApiRestResponse<String>> accountExpiredException(AccountExpiredException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHC_ACCOUNT_EXPIRED, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({CredentialsExpiredException.class})
    public ResponseEntity<ApiRestResponse<String>> credentialsExpiredException(CredentialsExpiredException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHC_CREDENTIALS_EXPIRED, ex);
    }

    // --- Security Biz Error ---

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationServiceExceptionAdapter.class})
    public ResponseEntity<ApiRestResponse<String>> externalAuthenticationServiceException(AuthenticationServiceExceptionAdapter ex) {
        this.logException(ex);
        return new ResponseEntity<>(ApiRestResponse.of(ex.getCode(), messages.getMessage(ex.getMsgKey(), ex.getMessage())), HttpStatus.OK);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationMethodNotSupportedException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationMethodNotSupportedException(AuthenticationMethodNotSupportedException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHC_METHOD_NOT_ALLOWED, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationOverRetryRemindException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationOverRetryRemindException(AuthenticationOverRetryRemindException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHC_OVER_RETRY_REMIND, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationCaptchaNotFoundException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationCaptchaNotFoundException(AuthenticationCaptchaNotFoundException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHC_CAPTCHA_REQUIRED, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationCaptchaExpiredException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationCaptchaExpiredException(AuthenticationCaptchaExpiredException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHC_CAPTCHA_EXPIRED, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationCaptchaIncorrectException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationCaptchaIncorrectException(AuthenticationCaptchaIncorrectException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHC_CAPTCHA_INCORRECT, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationTokenNotFoundException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationTokenNotFoundException(AuthenticationTokenNotFoundException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHZ_TOKEN_REQUIRED, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationTokenExpiredException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationTokenExpiredException(AuthenticationTokenExpiredException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHZ_TOKEN_EXPIRED, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationTokenInvalidException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationTokenInvalidException(AuthenticationTokenInvalidException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHZ_TOKEN_INCORRECT, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationTokenIncorrectException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationTokenIncorrectException(AuthenticationTokenIncorrectException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHZ_TOKEN_INCORRECT, ex);
    }


    // --- Security JWT Error ---

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationJwtIssuedException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationJwtIssuedException(AuthenticationJwtIssuedException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHZ_TOKEN_ISSUED, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationJwtNotFoundException.class})
    @ResponseBody
    public ResponseEntity<ApiRestResponse<String>> authenticationJwtNotFoundException(AuthenticationJwtNotFoundException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHZ_TOKEN_REQUIRED, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationJwtExpiredException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationJwtExpiredException(AuthenticationJwtExpiredException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHZ_TOKEN_EXPIRED, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationJwtInvalidException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationJwtInvalidException(AuthenticationJwtInvalidException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHZ_TOKEN_INVALID, ex);
    }

    /**
     * 401 (Unauthorized)
     */
    @ExceptionHandler({AuthenticationJwtIncorrectException.class})
    public ResponseEntity<ApiRestResponse<String>> authenticationJwtIncorrectException(AuthenticationJwtIncorrectException ex) {
        return this.authenticationException(AuthResponseCode.SC_AUTHZ_TOKEN_INCORRECT, ex);
    }

    protected ResponseEntity<ApiRestResponse<String>> authenticationException(AuthResponseCode responseCode, Exception ex) {
        this.logException(ex);
        String message = messages.getMessage(responseCode.getMsgKey(), ex.getMessage());
        return new ResponseEntity<>(ApiRestResponse.of(responseCode.getCode(), message), HttpStatus.OK);
    }

}
