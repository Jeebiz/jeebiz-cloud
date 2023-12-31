package io.hiwepy.cloud.authz.login.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 账号登录参数
 */
@Data
public class LoginByAccountParam {
    /**
     * 账号
     */
    @ApiModelProperty(name = "account", required = true, value = "账号")
    private String account;

}
