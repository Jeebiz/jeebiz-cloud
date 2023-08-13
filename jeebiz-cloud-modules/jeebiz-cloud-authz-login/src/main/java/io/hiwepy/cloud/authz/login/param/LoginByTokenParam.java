package io.hiwepy.cloud.authz.login.param;

import lombok.Data;

/**
 * Token自动登录参数
 */
@Data
public class LoginByTokenParam {

    /**
     * 用户当前正在使用的token
     */
    private String token;

}
