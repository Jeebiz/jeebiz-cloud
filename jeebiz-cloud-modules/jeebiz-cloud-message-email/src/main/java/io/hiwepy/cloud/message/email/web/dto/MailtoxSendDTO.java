/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.email.web.dto;

import hitool.mail.conf.EmailFrom;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@ApiModel(value = "MailtoxSendDTO", description = "邮件发送对象")
@Getter
@Setter
@ToString
public class MailtoxSendDTO {

    /**
     * 邮件优先级(1:紧急 3:普通 5:低)
     */
    @ApiModelProperty(name = "priority", dataType = "String", value = "邮件优先级(1:紧急 3:普通 5:低)", allowableValues = "1,3,5")
    protected String priority;
    /**
     * 邮件主题
     */
    @ApiModelProperty(name = "subject", required = true, dataType = "String", value = "邮件主题")
    @NotBlank(message = "邮件主题必填")
    protected String subject;
    /**
     * 邮件内容是否是html
     */
    @ApiModelProperty(name = "html", required = true, dataType = "Boolean", value = "邮件内容是否是html")
    protected boolean html = false;
    /**
     * 邮件内容,普通文本或者html
     */
    @ApiModelProperty(name = "content", required = true, dataType = "String", value = "邮件内容,普通文本或者html")
    @NotBlank(message = "邮件内容,普通文本或者html")
    protected String content;
    /**
     * 发件人名称和邮箱
     */
    @ApiModelProperty(name = "from", required = true, dataType = "io.hiwepy.javamail.conf.EmailFrom", value = "发件人名称和邮箱")
    @NotBlank(message = "发件人名称和邮箱")
    protected EmailFrom from;
    /**
     * 收件人名称和邮箱
     */
    @ApiModelProperty(name = "mailto", required = true, dataType = "java.util.Map<String, String>", value = "收件人名称和邮箱")
    @NotBlank(message = "收件人名称和邮箱")
    protected Map<String, String> mailto;
    /**
     * 抄送人名称和邮箱
     */
    @ApiModelProperty(name = "mailcc", required = true, dataType = "java.util.Map<String, String>", value = "抄送人名称和邮箱")
    @NotBlank(message = "抄送人名称和邮箱")
    protected Map<String, String> mailcc;
    /**
     * 密送人名称和邮箱
     */
    @ApiModelProperty(name = "mailBcc", required = true, dataType = "java.util.Map<String, String>", value = "密送人名称和邮箱")
    @NotBlank(message = "密送人名称和邮箱")
    protected Map<String, String> mailBcc;

}
