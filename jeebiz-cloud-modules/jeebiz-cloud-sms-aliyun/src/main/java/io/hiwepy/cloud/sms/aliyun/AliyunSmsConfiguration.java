/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.sms.aliyun;

import com.alibaba.cloud.spring.boot.sms.SmsServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AliyunSmsProperties.class)
public class AliyunSmsConfiguration {

    @Bean
    public AliyunSmsTemplate aliyunSmsTemplate(SmsServiceImpl smsService, AliyunSmsProperties smsProperties) {
        return new AliyunSmsTemplate(smsService, smsProperties);
    }

}
