package io.hiwepy.cloud.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OssStsDTO {

    @ApiModelProperty(name = "endpoint", dataType = "String", value = "Endpoint（地域节点）")
    private String endpoint;
    @ApiModelProperty(name = "bucketName", dataType = "String", value = "Bucket 名称")
    private String bucketName;
    @ApiModelProperty(name = "expiration", dataType = "String", value = "Token 过期时间")
    private String expiration;
    @ApiModelProperty(name = "accessKeyId", dataType = "String", value = "阿里云访问key")
    private String accessKeyId;
    @ApiModelProperty(name = "accessKeySecret", dataType = "String", value = "阿里云访问密钥")
    private String accessKeySecret;
    @ApiModelProperty(name = "securityToken", dataType = "String", value = "Token")
    private String securityToken;
}
