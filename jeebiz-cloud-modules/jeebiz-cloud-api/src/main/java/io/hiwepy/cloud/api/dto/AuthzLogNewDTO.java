package io.hiwepy.cloud.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 认证授权日志信息DTO
 */
@ApiModel(value = "AuthzLogNewDTO", description = "认证授权日志信息DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthzLogNewDTO {

    /**
     * 认证授权对象ID
     */
    @ApiModelProperty(value = "认证授权对象ID")
    private String userId;
    /**
     * 认证授权类型（login:登录认证、logout:会话注销）
     */
    @ApiModelProperty(value = "认证授权类型（login:登录认证、logout:会话注销）")
    private String opt;
    /**
     * 认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等
     */
    @ApiModelProperty(value = "认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等")
    private String protocol;
    /**
     * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
     */
    @ApiModelProperty(value = "日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）")
    private String level;
    /**
     * 认证请求来源IP地址
     */
    @ApiModelProperty(value = "认证请求来源IP地址")
    private String addr;
    /**
     * 认证授权结果：（fail:失败、success:成功）
     */
    @ApiModelProperty(value = "认证授权结果：（fail:失败、success:成功）")
    private String status;
    /**
     * 认证授权请求信息
     */
    @ApiModelProperty(value = "认证授权请求信息")
    private String msg;
    /**
     * 认证授权异常信息
     */
    @ApiModelProperty(value = "认证授权异常信息")
    private String exception;
    /**
     * 认证授权发生时间
     */
    @ApiModelProperty(value = "认证授权发生时间")
    private String time24;

    /**
     * 设备激活记录ID
     */
    @ApiModelProperty(value = "设备激活记录ID")
    private String deviceId;
    /**
     * 用户登录的客户端应用ID
     */
    @ApiModelProperty(value = "用户登录的客户端应用ID")
    private String appId;
    /**
     * 用户登录的客户端应用渠道编码
     */
    @ApiModelProperty(value = "用户登录的客户端应用渠道编码")
    private String appChannel;
    /**
     * 用户登录的客户端版本
     */
    @ApiModelProperty(value = "用户登录的客户端版本")
    private String appVersion;

}
