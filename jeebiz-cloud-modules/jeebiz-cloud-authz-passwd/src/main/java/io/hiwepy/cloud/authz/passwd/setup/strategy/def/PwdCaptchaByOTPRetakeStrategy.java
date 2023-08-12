package io.hiwepy.cloud.authz.passwd.setup.strategy.def;

import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeField;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdStrategy;

public class PwdCaptchaByOTPRetakeStrategy extends PwdCaptchaRetakeStrategyAdapter {

    public PwdCaptchaByOTPRetakeStrategy() {
        this.bindFields = new PwdRetakeField[]{new PwdRetakeField(PwdStrategy.PWD_RETAKE_BY_OTP, "动态口令", true)};
    }

    @Override
    public String name() {
        return PwdStrategy.PWD_RETAKE_BY_OTP;
    }

}