package io.hiwepy.cloud.authz.login.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "MessageVerifyParam", description = "短信验证VO")
@Data
public class MessageVerifyParam {
    /**
     * 手机号
     */
    @ApiModelProperty(value = "clientId", dataType = "String", notes = "手机号")
    @NotBlank(message = "手机号 必填")
    private String phone;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "verifyCode", dataType = "String", notes = "验证码")
    @NotBlank(message = "验证码 必填")
    private String verifyCode;
}
