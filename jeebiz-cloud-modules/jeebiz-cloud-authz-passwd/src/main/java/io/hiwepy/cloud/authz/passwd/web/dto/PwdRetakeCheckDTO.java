package io.hiwepy.cloud.authz.passwd.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ApiModel(value = "PwdRetakeCheckDTO", description = "账号核实验证参数")
@Getter
@Setter
@ToString
public class PwdRetakeCheckDTO {

    /**
     * 密码找回操作UID：作为后续操作的凭证
     */
    @ApiModelProperty(name = "uuid", dataType = "String", value = "密码找回操作UID：作为后续操作的凭证")
    protected String uuid;
    /**
     * 用户选择的密码找回策略
     */
    @ApiModelProperty(name = "strategy", dataType = "String", value = "用户选择的密码找回策略")
    protected String strategy;
    /**
     * 用户账号
     */
    @ApiModelProperty(name = "username", dataType = "String", value = "用户账号")
    protected String username;
    /**
     * 验证码（收到的邮箱或短信验证码）
     */
    @ApiModelProperty(name = "captcha", dataType = "String", value = "验证码（收到的邮箱或短信验证码）")
    protected String captcha;
    /**
     * 附属参数
     */
    @ApiModelProperty(name = "username", dataType = "java.util.Map<String, String>", value = "附属参数")
    protected Map<String, String> data = new HashMap<String, String>();

}
