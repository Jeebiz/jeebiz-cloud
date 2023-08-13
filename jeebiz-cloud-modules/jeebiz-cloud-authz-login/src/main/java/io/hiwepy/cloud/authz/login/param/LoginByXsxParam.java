package io.hiwepy.cloud.authz.login.param;

import lombok.Data;

/**
 * 账号密码登录参数
 */
@Data
public class LoginByXsxParam {

    /**
     * 姓名
     */
    private String name;

    /**
     * 账号
     */
    private String mobile;

    /**
     * 密码
     */
    private String idCard;

}
