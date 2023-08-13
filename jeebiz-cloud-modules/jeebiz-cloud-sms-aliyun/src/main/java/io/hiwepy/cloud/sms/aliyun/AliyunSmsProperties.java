/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.sms.aliyun;

import com.aliyuncs.http.FormatType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(AliyunSmsProperties.PREFIX)
@Data
public class AliyunSmsProperties {

    public static final String PREFIX = "alibaba.cloud.sms";

    private FormatType acceptFormat = FormatType.JSON;

    private String encoding;

    private Integer connectTimeout;

    private Integer readTimeout;

    private String[] signs;

}
