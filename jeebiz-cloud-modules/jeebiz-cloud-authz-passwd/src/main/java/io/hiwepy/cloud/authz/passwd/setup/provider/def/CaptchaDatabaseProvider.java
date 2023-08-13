package io.hiwepy.cloud.authz.passwd.setup.provider.def;

import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeCaptchaModel;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeCaptchaService;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeCaptcha;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeTime;
import io.hiwepy.cloud.authz.passwd.setup.provider.CaptchaProvider;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;

/**
 * 基于数据库存储的验证码服务提供实现
 */
public class CaptchaDatabaseProvider implements CaptchaProvider {

    protected String format = "yyyy-MM-dd HH:mm:ss";
    protected SimpleDateFormat sdf = new SimpleDateFormat(format);
    protected int effectTime = 1000 * 30;

    protected final IPwdRetakeCaptchaService pwdRetakeCaptchaService;

    public CaptchaDatabaseProvider(IPwdRetakeCaptchaService pwdRetakeCaptchaService) {
        super();
        this.pwdRetakeCaptchaService = pwdRetakeCaptchaService;
    }

    @Override
    public String name() {
        return this.getClass().getName();
    }

    @Override
    public PwdRetakeCaptcha input(PwdRetakeDto data) {
        PwdRetakeCaptchaModel model = getPwdRetakeCaptchaService().getById(data.getUuid());
        if (null != model) {
            return new PwdRetakeCaptcha(model.getUuid(), model.getCaptcha(), model.getTime24());
        }
        return null;
    }

    @Override
    public String valid(PwdRetakeDto data, PwdRetakeCaptcha captcha) {
        if (captcha == null) {
            return "-1";
        }
        // 当前数据库时间
        PwdRetakeTime now = new PwdRetakeTime(getPwdRetakeCaptchaService().getDatetime(), getFormat(), getEffectTime());
        // 验证码发送时间
        PwdRetakeTime send = new PwdRetakeTime(captcha.getTimestamp(), getFormat(), getEffectTime());
        // 验证码对象不为空且发送时间在有效时间内
        if (send.compareTo(now) > 0) {
            return "1";
        }
        // 删除数据库中的过期值
        PwdRetakeCaptchaModel model = new PwdRetakeCaptchaModel();
        getPwdRetakeCaptchaService().removeById(model);
        return "0";
    }

    @Override
    public PwdRetakeCaptcha gen(PwdRetakeDto data) {
        return new PwdRetakeCaptcha(data.getUuid(), RandomStringUtils.random(6), getPwdRetakeCaptchaService().getDatetime());
    }

    @Override
    public boolean store(PwdRetakeDto data, PwdRetakeCaptcha captcha) {

        PwdRetakeCaptchaModel model = new PwdRetakeCaptchaModel();
        model.setUuid(data.getUuid());
        model.setCaptcha(captcha.getCaptcha());
        model.setTime24(captcha.getTimestamp());

        getPwdRetakeCaptchaService().save(model);

        return true;
    }

    @Override
    public boolean evict(PwdRetakeDto data) {
        getPwdRetakeCaptchaService().removeById(data.getUuid());
        return true;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
        this.sdf = new SimpleDateFormat(format);
    }

    public int getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(int effectTime) {
        this.effectTime = effectTime;
    }

    public IPwdRetakeCaptchaService getPwdRetakeCaptchaService() {
        return pwdRetakeCaptchaService;
    }

}
