/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.sample.setup.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.boot.SecurityDingTalkProperties;
import org.springframework.security.boot.biz.userdetails.JwtPayloadRepository;
import org.springframework.security.boot.dingtalk.authentication.DingTalkMatchedAuthenticationSuccessJwtHandler;

@Configuration
@EnableConfigurationProperties(SecurityDingTalkProperties.class)
public class DingTalkAuthcConfig {

    @Bean
    public DingTalkMatchedAuthenticationSuccessJwtHandler dingTalkAuthenticationSuccessJwtHandler(JwtPayloadRepository payloadRepository) {
        return new DingTalkMatchedAuthenticationSuccessJwtHandler(payloadRepository);
    }
}
