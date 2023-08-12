package io.hiwepy.cloud.base.mqflow.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(FlywayFluentConfiguration.class)
public class JeebizMqFlowConfiguration {

    @Bean
    public FlywayFluentConfiguration flywayMqFlowConfiguration() {

        FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("mqflow",
                "MQ消息流水-模块初始化", "1.0.0");

        return configuration;
    }

}
