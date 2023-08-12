package io.hiwepy.cloud.authz.passwd.setup.strategy.def;

import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeField;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdStrategy;

/**
 * 手机找回密码策略实现
 */
public class PwdCaptchaByOksmsRetakeStrategy extends PwdCaptchaRetakeStrategyAdapter {

    public PwdCaptchaByOksmsRetakeStrategy() {
        this.bindFields = new PwdRetakeField[]{new PwdRetakeField(PwdStrategy.PWD_RETAKE_BY_PHONE, "手机号码", true)};
    }

    @Override
    public String name() {
        return PwdStrategy.PWD_RETAKE_BY_PHONE;
    }

}
