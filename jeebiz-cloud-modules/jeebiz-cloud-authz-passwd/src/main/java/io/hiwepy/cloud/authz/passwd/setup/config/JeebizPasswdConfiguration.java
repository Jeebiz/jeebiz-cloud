/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.passwd.setup.config;


import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeAccountService;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeCaptchaService;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeSettingsService;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeVerifiService;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeField;
import io.hiwepy.cloud.authz.passwd.setup.provider.*;
import io.hiwepy.cloud.authz.passwd.setup.provider.def.*;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdRetakeStrategy;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdStrategyManager;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdVerifiStrategy;
import io.hiwepy.cloud.authz.passwd.setup.strategy.def.PwdCaptchaByEmailRetakeStrategy;
import io.hiwepy.cloud.authz.passwd.setup.strategy.def.PwdDatabaseVerifiStrategy;
import io.hiwepy.cloud.message.email.service.IMailtoxSendService;
import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class JeebizPasswdConfiguration {

    @Bean
    public FlywayFluentConfiguration flywayPasswdConfiguration() {

        FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("passwd",
                "密码找回-模块初始化", "1.0.0");

        return configuration;
    }

    @Bean
    @ConditionalOnMissingBean
    public DatetimeProvider timeProvider(IPwdRetakeCaptchaService pwdRetakeCaptchaService) {
        return new DatabaseDatetimeProvider(pwdRetakeCaptchaService);
    }

    @Bean
    @ConditionalOnMissingBean
    public AccountInputProvider accountInputProvider(IPwdRetakeAccountService pwdRetakeAccountService) {
        return new DatabaseAccountInputProvider(pwdRetakeAccountService);
    }

    @Bean
    @ConditionalOnMissingBean
    public PwdPropertiesProvider pwdPropertiesProvider(IPwdRetakeSettingsService pwdRetakeSettingsService) {
        return new DatabasePwdPropertiesProvider(pwdRetakeSettingsService);
    }
	
	/*
	 * 验证码生成,存储:数据库实现
	 
	public CaptchaDatabaseProvider captchaProvider(IPwdRetakeCaptchaService pwdRetakeCaptchaService) {
		return new CaptchaDatabaseProvider(pwdRetakeCaptchaService);
	}*/

    /*
     * 验证码生成,存储:Redis实现
     */
    @Bean
    public CaptchaRedisProvider captchaRedisProvider(RedisTemplate<String, Object> redisTemplate,
                                                     DatetimeProvider timeProvider) {
        return new CaptchaRedisProvider(redisTemplate, timeProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public PwdUpdateProvider pwdUpdateProvider(IPwdRetakeAccountService pwdRetakeAccountService,
                                               CaptchaProvider captchaProvider,
                                               PasswordEncoder passwordEncoder) {
        return new DatabasePwdUpdateProvider(pwdRetakeAccountService, captchaProvider, passwordEncoder);
    }

    /**
     * 电子邮件发送服务提供实现
     * @return
     */
    @Bean
    public CaptchaEmailOutputProvider captchaEmailOutputProvider(
            PwdPropertiesProvider pwdPropertiesProvider,
            IMailtoxSendService mailtoxSendService) {

        CaptchaEmailOutputProvider outputProvider = new CaptchaEmailOutputProvider(mailtoxSendService);
        // propsProvider: 参数配置提供者实现
        outputProvider.setPropsProvider(pwdPropertiesProvider);

        return outputProvider;
    }

    /*
     * 电子邮箱找回密码策略实现
     */
    @Bean
    public PwdCaptchaByEmailRetakeStrategy emailRetakeStrategy(
            AccountInputProvider accountProvider,
            CaptchaProvider captchaProvider,
            CaptchaEmailOutputProvider captchaOutputProvider) {
        PwdCaptchaByEmailRetakeStrategy retakeStrategy = new PwdCaptchaByEmailRetakeStrategy();
        // accountProvider: 账号数据提供者
        retakeStrategy.setAccountProvider(accountProvider);
        // captchaProvider: 验证码生成服务提供者
        retakeStrategy.setCaptchaProvider(captchaProvider);
        // captchaOutput: 验证码输出对象提供者
        retakeStrategy.setCaptchaOutputProvider(captchaOutputProvider);
        // bindFields: 该验证方式绑定的数据库字段信息
        retakeStrategy.setBindFields(new PwdRetakeField("sjhm", "手机号码", true));

        return retakeStrategy;
    }

    /**
     * 短信发送服务提供实现
     * @return

     @Bean public CaptchaOksmsOutputProvider captchaOksmsOutputProvider(
     PwdPropertiesProvider pwdPropertiesProvider,
     OksmsTemplate oksmsTemplate) {
     CaptchaOksmsOutputProvider outputProvider = new CaptchaOksmsOutputProvider(oksmsTemplate);
     // propsProvider: 参数配置提供者实现
     outputProvider.setPropsProvider(pwdPropertiesProvider);

     return outputProvider;
     }*/
	
	/*
	 * 手机号码找回密码策略实现
	 
	@Bean
	public PwdCaptchaByOksmsRetakeStrategy oksmsRetakeStrategy(
			AccountInputProvider accountProvider,
			CaptchaProvider captchaProvider,
			CaptchaOksmsOutputProvider captchaOutputProvider) {
		PwdCaptchaByOksmsRetakeStrategy retakeStrategy = new PwdCaptchaByOksmsRetakeStrategy();
		// accountProvider: 账号数据提供者 
		retakeStrategy.setAccountProvider(accountProvider);
		// captchaProvider: 验证码生成服务提供者
		retakeStrategy.setCaptchaProvider(captchaProvider);
		// captchaOutput: 验证码输出对象提供者
		retakeStrategy.setCaptchaOutputProvider(captchaOutputProvider);
		// bindFields: 该验证方式绑定的数据库字段信息
		retakeStrategy.setBindFields(new PwdRetakeField("phone","手机号码", true));
		
		return retakeStrategy;
	}*/
	
	/*
	 * 动态口令找回密码策略实现
	@Bean
	public PwdCaptchaByOTPRetakeStrategy otpRetakeStrategy(
			AccountInputProvider accountProvider,
			CaptchaProvider captchaProvider,
			CaptchaOksmsOutputProvider captchaOutputProvider) {
		PwdCaptchaByOTPRetakeStrategy retakeStrategy = new PwdCaptchaByOTPRetakeStrategy();
		// accountProvider: 账号数据提供者 
		retakeStrategy.setAccountProvider(accountProvider);
		// captchaProvider: 验证码生成服务提供者
		retakeStrategy.setCaptchaProvider(captchaProvider);
		// captchaOutput: 验证码输出对象提供者
		retakeStrategy.setCaptchaOutputProvider(captchaOutputProvider);
		// bindFields: 该验证方式绑定的数据库字段信息
		retakeStrategy.setBindFields(new PwdRetakeField("otp","动态口令", true));
		
		return retakeStrategy;
	}*/
    @Bean
    @ConditionalOnMissingBean
    public PwdVerifiStrategy pwdVerifiStrategy(IPwdRetakeVerifiService pwdRetakeVerifiService,
                                               AccountInputProvider accountProvider) {
        return new PwdDatabaseVerifiStrategy(pwdRetakeVerifiService, accountProvider);
    }

    /*
     * 密码找回策略管理器
     */
    @Bean
    public PwdStrategyManager pwdStrategyManager(
            ObjectProvider<PwdRetakeStrategy> pwdRetakeStrategyProvider,
            ObjectProvider<PwdVerifiStrategy> pwdVerifiStrategyProvider) {
        // 构造密码相关策略对象管理者
        PwdStrategyManager strategyManager = new PwdStrategyManager();
        // 注册扩展的密码找回策略
        List<PwdRetakeStrategy> retakeList = pwdRetakeStrategyProvider.stream().collect(Collectors.toList());
        if (retakeList != null) {
            for (PwdRetakeStrategy pwdRetakeStrategy : retakeList) {
                strategyManager.register(pwdRetakeStrategy);
            }
        }
        // 注册账号核实字段验证策略
        List<PwdVerifiStrategy> verifiList = pwdVerifiStrategyProvider.stream().collect(Collectors.toList());
        if (verifiList != null) {
            for (PwdVerifiStrategy pwdVerifiStrategy : verifiList) {
                strategyManager.register(pwdVerifiStrategy);
            }
        }
        return strategyManager;
    }


}
