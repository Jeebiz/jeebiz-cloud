package io.hiwepy.cloud.sms.core.param;

import io.hiwepy.boot.api.annotation.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "SmsCheckParam", description = "短信验证Param")
@Data
public class SmsCheckParam {

    /**
     * 城市编码
     */
    @ApiModelProperty(name = "countryCode", required = true, value = "城市编码")
    @NotBlank(message = "{sms.check.countryCode.required}")
    private Integer countryCode;
    /**
     * 手机号码
     */
    @ApiModelProperty(name = "phone", required = true, value = "手机号码")
    @NotBlank(message = "{sms.check.phone.required}")
    @Phone(message = "{sms.check.phone.invalid}")
    private String phone;
    /**
     * 业务ID：（biz_0：注册、biz_1：绑定、2：找回密码、3：忘记密码、4：手机号码登录）
     */
    @ApiModelProperty(name = "bizId", required = true, value = " 业务类型：（0：注册、1：绑定、2：找回密码、3：忘记密码、4：手机号码登录）")
    @NotNull(message = "{sms.check.bizId.required}")
    private String bizId;
    /**
     * 验证码
     */
    @ApiModelProperty(name = "vcode", required = true, value = "验证码")
    @NotBlank(message = "{sms.check.vcode.required}")
    private String vcode;

}
