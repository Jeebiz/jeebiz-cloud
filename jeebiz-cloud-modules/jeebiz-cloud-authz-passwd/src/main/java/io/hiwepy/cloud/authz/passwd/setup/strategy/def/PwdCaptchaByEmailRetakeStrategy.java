package io.hiwepy.cloud.authz.passwd.setup.strategy.def;

import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeField;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdStrategy;

/**
 * 电子邮件找回密码策略实现
 */
public class PwdCaptchaByEmailRetakeStrategy extends PwdCaptchaRetakeStrategyAdapter {

    public PwdCaptchaByEmailRetakeStrategy() {
        this.bindFields = new PwdRetakeField[]{new PwdRetakeField(PwdStrategy.PWD_RETAKE_BY_EMAIL, "电子邮箱", true)};
    }

    @Override
    public String name() {
        return PwdStrategy.PWD_RETAKE_BY_EMAIL;
    }

}