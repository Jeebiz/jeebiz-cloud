/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.feature.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JeebizFeatureConfiguration {

    @Bean
    public FlywayFluentConfiguration flywayFeatureConfiguration() {

        FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("feature",
                "功能菜单-模块初始化", "1.0.0");

        return configuration;
    }

}
