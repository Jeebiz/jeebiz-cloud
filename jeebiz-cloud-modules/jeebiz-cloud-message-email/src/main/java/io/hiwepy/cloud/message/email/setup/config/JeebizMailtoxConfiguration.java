/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.email.setup.config;


import hitool.mail.JavaMailClientAdapter;
import hitool.mail.def.JavaMailClientImpl;
import hitool.mail.def.SpringMailClientImpl;
import hitool.mail.provider.EmailPropertiesProvider;
import io.hiwepy.cloud.message.email.service.IMailtoxSettingsService;
import io.hiwepy.cloud.message.email.setup.event.ContextRefreshedEventListener;
import io.hiwepy.cloud.message.email.setup.event.EmailPushEventListener;
import io.hiwepy.cloud.message.email.setup.event.MailtoxSettingsUpdateEventListener;
import io.hiwepy.cloud.message.email.setup.provider.DatabaseEmailPropertiesProvider;
import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JeebizMailtoxConfiguration {

    @Bean
    public FlywayFluentConfiguration flywayMailtoxConfiguration() {

        FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("mailtox",
                "邮件发送-模块初始化", "1.0.0");

        return configuration;
    }

    /**
     * 邮件发送者，Spring实现
     */
    @Bean
    public JavaMailSenderImpl springMailSender() {
        return new JavaMailSenderImpl();
    }

    /*
     * 数据库邮件服务配置数据提供者实现
     */
    @Bean
    public DatabaseEmailPropertiesProvider databaseEmailPropertiesProvider(IMailtoxSettingsService mailtoxSettingsService) {
        DatabaseEmailPropertiesProvider propsProvider = new DatabaseEmailPropertiesProvider(mailtoxSettingsService);
        // 基于配置文件的默认配置

        // 指定从参数设置表提取的参数
        // mail.from.desc

        return propsProvider;
    }

    /**
     * 邮件发送客户端
     * @return
     */
    @Bean
    public JavaMailClientAdapter mailClient(JavaMailSenderImpl mailSender,
                                            EmailPropertiesProvider propsProvider) {
        SpringMailClientImpl client = new SpringMailClientImpl();
        client.setMailSender(mailSender);
        client.setPropsProvider(propsProvider);
        return client;
    }

    public JavaMailClientAdapter mailClient(
            EmailPropertiesProvider propsProvider) {
        JavaMailClientImpl client = new JavaMailClientImpl();
        client.setPropsProvider(propsProvider);
        return client;
    }

    /**
     * 邮件发送服务提供实现
     * @return
     */
    @Bean
    public EmailPushEventListener emailPushEventListener(JavaMailClientAdapter mailClient) {
        return new EmailPushEventListener(mailClient);
    }

    @Bean
    public MailtoxSettingsUpdateEventListener mailtoxSettingsUpdateEventListener() {
        return new MailtoxSettingsUpdateEventListener();
    }

    @Bean("mailtoxContextRefreshedEventListener")
    public ContextRefreshedEventListener mailtoxContextRefreshedEventListener(IMailtoxSettingsService mailtoxSettingsService) {
        return new ContextRefreshedEventListener(mailtoxSettingsService);
    }

}
