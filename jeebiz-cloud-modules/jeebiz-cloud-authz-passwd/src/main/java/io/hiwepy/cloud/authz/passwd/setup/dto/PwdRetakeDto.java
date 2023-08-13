package io.hiwepy.cloud.authz.passwd.setup.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class PwdRetakeDto {

    /**
     * 密码找回操作UID：作为后续操作的凭证
     */
    protected String uuid;
    /**
     * 请求地址
     */
    protected String addr;
    /**
     * 用户选择的密码找回策略
     */
    protected String strategy;
    /**
     * 用户账号
     */
    protected String username;
    /**
     * 附属参数
     */
    protected Map<String, String> data = new HashMap<String, String>();

}
