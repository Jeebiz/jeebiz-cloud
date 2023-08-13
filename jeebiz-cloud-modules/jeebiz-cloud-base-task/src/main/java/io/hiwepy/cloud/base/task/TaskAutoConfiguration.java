/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.task;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wandl
 */
@Configuration
@EnableConfigurationProperties(TaskProperties.class)
public class TaskAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TaskInfoProvider taskInfoProvider() {
        return new TaskInfoProvider() {
        };
    }

}
