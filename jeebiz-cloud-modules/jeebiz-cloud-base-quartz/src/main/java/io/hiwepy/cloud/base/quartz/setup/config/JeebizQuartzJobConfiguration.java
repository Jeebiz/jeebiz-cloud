/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.quartz.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JeebizQuartzJobConfiguration {

    @Bean
    public FlywayFluentConfiguration flywayQuartzJobConfiguration() {

        FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("quartz",
                "定时任务-模块初始化", "1.0.0");

        return configuration;
    }

}
