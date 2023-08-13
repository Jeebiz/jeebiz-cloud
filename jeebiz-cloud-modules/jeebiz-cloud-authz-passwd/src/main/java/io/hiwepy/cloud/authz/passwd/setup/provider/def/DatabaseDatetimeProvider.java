package io.hiwepy.cloud.authz.passwd.setup.provider.def;

import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeCaptchaService;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeTime;
import io.hiwepy.cloud.authz.passwd.setup.provider.DatetimeProvider;

/**
 * 数据库时间提供者实现，统一在一个数据库情况下的时间
 */
public class DatabaseDatetimeProvider implements DatetimeProvider {

    protected final IPwdRetakeCaptchaService pwdRetakeCaptchaService;

    public DatabaseDatetimeProvider(IPwdRetakeCaptchaService pwdRetakeCaptchaService) {
        super();
        this.pwdRetakeCaptchaService = pwdRetakeCaptchaService;
    }

    @Override
    public String name() {
        return this.getClass().getName();
    }

    @Override
    public PwdRetakeTime dateTime(String format, int effectTime) {
        return new PwdRetakeTime(getPwdRetakeCaptchaService().getDatetime(), format, effectTime);
    }

    public IPwdRetakeCaptchaService getPwdRetakeCaptchaService() {
        return pwdRetakeCaptchaService;
    }

}
