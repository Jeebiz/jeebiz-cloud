/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.autoconfigure.redis;

import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.autoconfigure.webmvc.ExceptinHandler;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.*;
import org.springframework.data.redis.connection.RedisInvalidSubscriptionException;
import org.springframework.data.redis.connection.RedisPipelineException;
import org.springframework.data.redis.connection.RedisSubscribedConnectionException;
import org.springframework.data.redis.core.RedisOperationException;
import org.springframework.data.redis.listener.adapter.RedisListenerExecutionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.boot.biz.SpringSecurityBizMessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RedisExceptinHandler extends ExceptinHandler {

    protected final MessageSourceAccessor messages = SpringSecurityBizMessageSource.getAccessor();

    // --- Redis Biz Error ---

    /**
     * 500 (Redis Operation Exception)
     */
    @ExceptionHandler({RedisOperationException.class})
    @ResponseBody
    public ResponseEntity<ApiRestResponse<String>> redisOperationException(RedisOperationException ex) {
        this.logException(ex);
        ApiRestResponse<String> resp = ApiCode.SC_INTERNAL_SERVER_ERROR.toResponse(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 (Redis Exception)
     */
    @ExceptionHandler({ClusterRedirectException.class})
    @ResponseBody
    public ResponseEntity<ApiRestResponse<String>> redisClusterRedirectException(ClusterRedirectException ex) {
        this.logException(ex);
        ApiRestResponse<String> resp = ApiCode.SC_INTERNAL_SERVER_ERROR.toResponse(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 (Redis Exception)
     */
    @ExceptionHandler({ClusterStateFailureException.class})
    @ResponseBody
    public ResponseEntity<ApiRestResponse<String>> redisClusterStateFailureException(ClusterStateFailureException ex) {
        ApiRestResponse<String> resp = ApiCode.SC_INTERNAL_SERVER_ERROR.toResponse(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 (Redis Exception)
     */
    @ExceptionHandler({RedisConnectionFailureException.class})
    @ResponseBody
    public ResponseEntity<ApiRestResponse<String>> redisConnectionFailureException(RedisConnectionFailureException ex) {
        ApiRestResponse<String> resp = ApiCode.SC_INTERNAL_SERVER_ERROR.toResponse(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 (Redis Exception)
     */
    @ExceptionHandler({RedisInvalidSubscriptionException.class})
    @ResponseBody
    public ResponseEntity<ApiRestResponse<String>> redisInvalidSubscriptionException(RedisInvalidSubscriptionException ex) {
        this.logException(ex);
        ApiRestResponse<String> resp = ApiCode.SC_INTERNAL_SERVER_ERROR.toResponse(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 (Redis Exception)
     */
    @ExceptionHandler({RedisListenerExecutionFailedException.class})
    @ResponseBody
    public ResponseEntity<ApiRestResponse<String>> redisListenerExecutionFailedException(RedisListenerExecutionFailedException ex) {
        this.logException(ex);
        ApiRestResponse<String> resp = ApiCode.SC_INTERNAL_SERVER_ERROR.toResponse(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * 500 (Redis Exception)
     */
    @ExceptionHandler({RedisPipelineException.class})
    @ResponseBody
    public ResponseEntity<ApiRestResponse<String>> redisPipelineException(RedisPipelineException ex) {
        this.logException(ex);
        ApiRestResponse<String> resp = ApiCode.SC_INTERNAL_SERVER_ERROR.toResponse(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 (Redis Exception)
     */
    @ExceptionHandler({RedisSubscribedConnectionException.class})
    @ResponseBody
    public ResponseEntity<ApiRestResponse<String>> redisSubscribedConnectionException(RedisSubscribedConnectionException ex) {
        ApiRestResponse<String> resp = ApiCode.SC_INTERNAL_SERVER_ERROR.toResponse(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 (Redis Exception)
     */
    @ExceptionHandler({RedisSystemException.class})
    @ResponseBody
    public ResponseEntity<ApiRestResponse<String>> redisSystemException(RedisSystemException ex) {
        ApiRestResponse<String> resp = ApiCode.SC_INTERNAL_SERVER_ERROR.toResponse(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 500 (Redis Exception)
     */
    @ExceptionHandler({TooManyClusterRedirectionsException.class})
    @ResponseBody
    public ResponseEntity<ApiRestResponse<String>> redisTooManyClusterRedirectionsException(TooManyClusterRedirectionsException ex) {
        ApiRestResponse<String> resp = ApiCode.SC_INTERNAL_SERVER_ERROR.toResponse(ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
