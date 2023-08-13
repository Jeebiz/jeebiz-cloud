/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.setup.config;

import io.hiwepy.cloud.authz.facex.setup.provider.DefaultFaceRecognitionProvider;
import io.hiwepy.cloud.authz.facex.setup.provider.FaceRecognitionProvider;
import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JeebizFaceRecognitionConfiguration {

    @Bean
    public FlywayFluentConfiguration flywayFacexConfiguration() {

        FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("facex",
                "人脸识别-模块初始化", "1.0.0");

        return configuration;
    }

    @Bean
    @ConditionalOnMissingBean
    public FaceRecognitionProvider faceRecognitionProvider() {
        return new DefaultFaceRecognitionProvider();
    }

}