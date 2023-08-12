/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.service.impl;

import io.hiwepy.boot.autoconfigure.aspect.DefaultApiOperationLogProvider;
import io.hiwepy.cloud.base.logs.strategy.LogWriteChannel;
import io.hiwepy.cloud.base.logs.strategy.LogWriteStrategyRouter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Objects;

@Component
@Slf4j
public class BusinessOperationLogImpl extends DefaultApiOperationLogProvider {

    @Autowired
    private LogWriteStrategyRouter logWriteStrategyRouter;
    @Value("${logging.biz-log.channel:database}")
    private LogWriteChannel logWriteChannel = LogWriteChannel.database;

    @Override
    protected void saveLog(JoinPoint joinPoint, Method method, ApiOperation apiOperation, Object rt, Throwable ex, StopWatch stopWatch) {
        // 1、获取注解信息
        Api api = AnnotationUtils.findAnnotation(method.getDeclaringClass(), Api.class);
        if (Objects.isNull(api) || Objects.isNull(apiOperation)) {
            return;
        }
        try {
            //logWriteStrategyRouter.routeFor(logWriteChannel).saveLog(method, api, apiOperation, rt, ex);
            if (logWriteStrategyRouter != null && logWriteStrategyRouter.routeFor(logWriteChannel) != null) {
                logWriteStrategyRouter.routeFor(logWriteChannel).saveLog(method, api, apiOperation, rt, ex);
            }
        } catch (Exception e) {
            log.error("Business Operation Log Error!", e);
        }
    }
}



