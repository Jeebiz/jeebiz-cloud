package io.hiwepy.cloud.authz.passwd.setup.strategy;

import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeResult;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeAdviceDTO;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeCheckDTO;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeDoneDTO;

/**
 * 密码找回策接口
 */
public interface PwdRetakeStrategy {

    /**
     * 策略名称，该策略名称将对应系统提供的找回方式代码
     */
    public String name();

    /**
     * 密码找回：发送验证码
     */
    public PwdRetakeResult advice(PwdRetakeAdviceDTO adviceDTO);

    /**
     * 密码找回：验证提供的验证码
     */
    public PwdRetakeResult check(PwdRetakeCheckDTO checkDTO);

    /**
     * 密码找回：密码重置
     */
    public PwdRetakeResult done(PwdRetakeDoneDTO doneDTO);

}
