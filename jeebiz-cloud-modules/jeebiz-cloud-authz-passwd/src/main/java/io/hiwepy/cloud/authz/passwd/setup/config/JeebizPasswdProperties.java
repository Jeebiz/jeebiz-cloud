package io.hiwepy.cloud.authz.passwd.setup.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(JeebizPasswdProperties.PREFIX)
@Data
public class JeebizPasswdProperties {

    public static final String PREFIX = "jeebiz.passwd";

    /**
     * 验证码过期时间（单位：毫秒）
     */
    protected int effectTime = 1000 * 30;


}
