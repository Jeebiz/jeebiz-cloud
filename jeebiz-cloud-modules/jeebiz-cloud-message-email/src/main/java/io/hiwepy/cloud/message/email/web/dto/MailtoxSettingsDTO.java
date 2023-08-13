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

@ApiModel(value = "MailtoxSettingsDTO", description = "邮件发送服务设置参数DTO")
@Getter
@Setter
@ToString
public class MailtoxSettingsDTO {

    /**
     * 邮件服务设置ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "邮件服务设置ID")
    protected String id;
    /**
     * 发送邮件账号
     */
    @ApiModelProperty(name = "eamil", dataType = "String", value = "发送邮件账号")
    protected String eamil;
    /**
     * 发送邮件账号密码
     */
    @ApiModelProperty(name = "passwd", dataType = "String", value = "发送邮件账号密码")
    protected String passwd;
    /**
     * 邮件服务器根域名
     */
    @ApiModelProperty(name = "domain", dataType = "String", value = "邮件服务器根域名")
    protected String domain;
    /**
     * 邮件服务器地址
     */
    @ApiModelProperty(name = "addr", dataType = "String", value = "邮件服务器地址")
    protected String addr;
    /**
     * 邮件服务器默认端口
     */
    @ApiModelProperty(name = "port", dataType = "String", value = "邮件服务器默认端口")
    protected String port = "25";
    /**
     * 发送超时时间，默认25000
     */
    @ApiModelProperty(name = "timeout", dataType = "String", value = "发送超时时间，默认25000")
    protected String timeout;
    /**
     * 邮件服务器备注信息
     */
    @ApiModelProperty(name = "remark", dataType = "String", value = "邮件服务器备注信息")
    protected String remark;
    /**
     * 邮件协议 smtp、nntp、pop3、pop4、imap
     */
    @ApiModelProperty(name = "protocol", dataType = "String", value = "邮件协议 smtp、nntp、pop3、pop4、imap", allowableValues = "smtp,nntp,pop3,pop4,imap")
    protected String protocol = "smtp";
    /**
     * 是否要求身份认证 (1:验证;0:不验证)
     */
    @ApiModelProperty(name = "useauth", dataType = "String", value = "是否要求身份认证 (1:验证;0:不验证)", allowableValues = "0,1")
    protected String useauth = "0";
    /**
     * 使用SSL加密通信(1:使用;0:不使用)
     */
    @ApiModelProperty(name = "usessl", dataType = "String", value = "使用SSL加密通信(1:使用;0:不使用)", allowableValues = "0,1")
    protected String usessl = "0";
    /**
     * 邮箱主机配置启用状态标记，1：启用，0：停用
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "邮箱主机配置启用状态标记，1：启用，0：停用", allowableValues = "0,1")
    protected String status;

}
