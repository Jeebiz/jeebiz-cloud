/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.login;

import io.hiwepy.cloud.authz.login.strategy.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoginProperties.class)
public class LoginAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "spring.security.cas", value = "enabled", havingValue = "true")
    @ConditionalOnMissingBean(CasAuthStrategy.class)
    public CasAuthStrategy casAuthStrategy(){
        return new CasAuthStrategy();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.security.dingtalk", value = "enabled", havingValue = "true")
    @ConditionalOnMissingBean(DingtalkMaAuthStrategy.class)
    public DingtalkMaAuthStrategy dingtalkMaAuthStrategy(){
        return new DingtalkMaAuthStrategy();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.security.dingtalk", value = "enabled", havingValue = "true")
    @ConditionalOnMissingBean(DingtalkScanCodeAuthStrategy.class)
    public DingtalkScanCodeAuthStrategy dingtalkScanCodeAuthStrategy(){
        return new DingtalkScanCodeAuthStrategy();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.security.dingtalk", value = "enabled", havingValue = "true")
    @ConditionalOnMissingBean(DingtalkTmpCodeAuthStrategy.class)
    public DingtalkTmpCodeAuthStrategy dingtalkTmpCodeAuthStrategy(){
        return new DingtalkTmpCodeAuthStrategy();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.security.pac4j", value = "enabled", havingValue = "true")
    @ConditionalOnMissingBean(Pac4jAuthStrategy.class)
    public Pac4jAuthStrategy pac4jAuthStrategy(){
        return new Pac4jAuthStrategy();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.security.jwt", value = "enabled", havingValue = "true")
    @ConditionalOnMissingBean(PasswordAuthStrategy.class)
    public PasswordAuthStrategy passwordAuthStrategy(){
        return new PasswordAuthStrategy();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.security.jwt", value = "enabled", havingValue = "true")
    @ConditionalOnMissingBean(TokenAuthStrategy.class)
    public TokenAuthStrategy tokenAuthStrategy(){
        return new TokenAuthStrategy();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.security.qrcode", value = "enabled", havingValue = "true")
    @ConditionalOnMissingBean(QrCodeAuthStrategy.class)
    public QrCodeAuthStrategy qrCodeAuthStrategy(){
        return new QrCodeAuthStrategy();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.security.weixin", value = "enabled", havingValue = "true")
    @ConditionalOnMissingBean(WxMaAuthStrategy.class)
    public WxMaAuthStrategy wxMaAuthStrategy(){
        return new WxMaAuthStrategy();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.security.weixin", value = "enabled", havingValue = "true")
    @ConditionalOnMissingBean(WxMpAuthStrategy.class)
    public WxMpAuthStrategy wxMpAuthStrategy(){
        return new WxMpAuthStrategy();
    }


}
