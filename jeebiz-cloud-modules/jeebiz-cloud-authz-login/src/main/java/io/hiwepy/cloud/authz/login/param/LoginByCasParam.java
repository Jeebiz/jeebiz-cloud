package io.hiwepy.cloud.authz.login.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jasig.cas.client.validation.Assertion;

/**
 * Cas登录参数
 */
@Data
public class LoginByCasParam {
    /**
     * assertion
     */
    @ApiModelProperty(name = "assertion", required = true, value = "Cas 携带的信息")
    private Assertion assertion;

    private String loginType;

    private String userId;

    private String username;

    private String accountId;

    private String nickName;

    private String mobile;
}
