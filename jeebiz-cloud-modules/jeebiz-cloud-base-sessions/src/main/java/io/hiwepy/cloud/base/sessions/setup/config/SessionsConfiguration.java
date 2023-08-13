/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.sessions.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 30)//session过期时间(秒)
@Configuration
public class SessionsConfiguration {

    @Bean
    public FlywayFluentConfiguration flywaySessionsConfiguration() {

        FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("sessions",
                "会话管理-模块初始化（会话列表、监控、强制退出等）", "1.0.0");

        return configuration;
    }

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        //让springSession不再执行config命令
        return ConfigureRedisAction.NO_OP;
    }

}
