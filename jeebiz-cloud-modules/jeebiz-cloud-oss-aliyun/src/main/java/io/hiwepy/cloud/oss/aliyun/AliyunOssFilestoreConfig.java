/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.aliyun;

import io.hiwepy.cloud.oss.core.bo.FileStoreConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "AliyunOssFilestoreConfig", description = "阿里云对外Oss公开配置")
@Data
@EqualsAndHashCode(callSuper = false)
public class AliyunOssFilestoreConfig extends FileStoreConfig {

    @ApiModelProperty(name = "bucketName", dataType = "String", value = "Bucket 名称")
    private String bucketName;
    @ApiModelProperty(name = "expiration", dataType = "String", value = "Token 过期时间")
    private String expiration;
    @ApiModelProperty(name = "accessKey", dataType = "String", value = "阿里云访问key")
    private String accessKey;
    @ApiModelProperty(name = "accessKeySecret", dataType = "String", value = "阿里云访问密钥")
    private String accessKeySecret;
    @ApiModelProperty(name = "securityToken", dataType = "String", value = "Token")
    private String securityToken;

}
