/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.device.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JeebizDeviceConfiguration {

    @Bean
    public FlywayFluentConfiguration flywayDeviceConfiguration() {

        FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("device",
                "设备激活-模块初始化", "1.0.0");

        return configuration;
    }

}
