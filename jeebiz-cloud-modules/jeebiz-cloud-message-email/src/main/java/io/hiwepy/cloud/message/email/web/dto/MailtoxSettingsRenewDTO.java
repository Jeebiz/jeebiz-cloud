/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.email.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "MailtoxSettingsRenewDTO", description = "发送邮件服务配置修改参数DTO")
@Getter
@Setter
@ToString
public class MailtoxSettingsRenewDTO {

    /**
     * 邮件服务设置ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "邮件服务设置ID")
    @NotBlank(message = "邮件服务设置ID不能为空")
    protected String id;
    /**
     * 发送邮件账号
     */
    @ApiModelProperty(name = "eamil", dataType = "String", value = "发送邮件账号")
    @NotBlank(message = "发送邮件账号不能为空")
    protected String eamil;
    /**
     * 发送邮件账号密码
     */
    @ApiModelProperty(name = "passwd", dataType = "String", value = "发送邮件账号密码")
    @NotBlank(message = "发送邮件账号密码不能为空")
    protected String passwd;
    /**
     * 邮件服务器根域名
     */
    @ApiModelProperty(name = "domain", dataType = "String", value = "邮件服务器根域名")
    @NotBlank(message = "邮件服务器根域名不能为空")
    protected String domain;
    /**
     * 邮件服务器地址
     */
    @ApiModelProperty(name = "addr", dataType = "String", value = "邮件服务器地址")
    @NotBlank(message = "邮件服务器地址不能为空")
    protected String addr;
    /**
     * 邮件服务器端口
     */
    @ApiModelProperty(name = "port", dataType = "String", value = "邮件服务器端口")
    @NotBlank(message = "邮件服务器端口不能为空")
    protected String port = "25";
    /**
     * 发送超时时间，默认25000
     */
    @ApiModelProperty(name = "timeout", dataType = "String", value = "发送超时时间，默认25000")
    @NotBlank(message = "发送超时时间不能为空")
    protected String timeout;
    /**
     * 邮件服务器备注信息
     */
    @ApiModelProperty(name = "remark", dataType = "String", value = "邮件服务器备注信息")
    @NotBlank(message = "邮件服务器备注信息不能为空")
    protected String remark;
    /**
     * 邮件协议 smtp、nntp、pop3、pop4、imap
     */
    @ApiModelProperty(name = "protocol", dataType = "String", value = "邮件协议 smtp、nntp、pop3、pop4、imap", allowableValues = "smtp,nntp,pop3,pop4,imap")
    @NotBlank(message = "邮件协议不能为空")
    protected String protocol = "smtp";
    /**
     * 是否要求身份认证 (1:验证;0:不验证)
     */
    @ApiModelProperty(name = "useauth", dataType = "String", value = "是否要求身份认证 (1:验证;0:不验证)", allowableValues = "0,1")
    @NotBlank(message = "是否要求身份认证不能为空")
    protected String useauth = "0";
    /**
     * 使用SSL加密通信(1:使用;0:不使用)
     */
    @ApiModelProperty(name = "usessl", dataType = "String", value = "使用SSL加密通信(1:使用;0:不使用)", allowableValues = "0,1")
    @NotBlank(message = "使用SSL加密通信不能为空")
    protected String usessl = "0";
    /**
     * 邮箱主机配置启用状态标记，1：启用，0：停用
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "邮箱主机配置启用状态标记，1：启用，0：停用", allowableValues = "0,1")
    @NotBlank(message = "邮箱主机配置启用状态标记不能为空")
    protected String status;


}
