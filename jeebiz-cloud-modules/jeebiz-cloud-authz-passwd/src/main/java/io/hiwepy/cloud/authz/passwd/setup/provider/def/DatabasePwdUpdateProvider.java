package io.hiwepy.cloud.authz.passwd.setup.provider.def;

import io.hiwepy.boot.api.dao.entities.BaseMap;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeAccountService;
import io.hiwepy.cloud.authz.passwd.setup.Constants;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeCaptcha;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeResult;
import io.hiwepy.cloud.authz.passwd.setup.provider.CaptchaProvider;
import io.hiwepy.cloud.authz.passwd.setup.provider.PwdUpdateProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DatabasePwdUpdateProvider implements PwdUpdateProvider {

    protected final IPwdRetakeAccountService pwdRetakeAccountService;
    /**
     * 验证码生成服务提供者
     */
    protected final CaptchaProvider captchaProvider;

    protected final PasswordEncoder passwordEncoder;

    public DatabasePwdUpdateProvider(IPwdRetakeAccountService pwdRetakeAccountService, CaptchaProvider captchaProvider,
                                     PasswordEncoder passwordEncoder) {
        super();
        this.pwdRetakeAccountService = pwdRetakeAccountService;
        this.captchaProvider = captchaProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String name() {
        return this.getClass().getName();
    }

    @Override
    public Object get(PwdRetakeDto data) {
        return null;
    }

    @Override
    public PwdRetakeResult update(PwdRetakeDto dto) {

        /*
         * 密码是否应该加密传输,数据库中密码不能明文保存
         */
        if (StringUtils.isBlank(dto.getUsername())) {
            return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_UPDATE_USERNAME_REQUIRED);
        }

        String newPassword = dto.getData().get("newpwd");
        if (StringUtils.isBlank(newPassword)) {
            return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_UPDATE_NEWPWD_REQUIRED);
        }

        String repeatPassword = dto.getData().get("repeatpwd");
        if (StringUtils.isBlank(repeatPassword)) {
            return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_UPDATE_REPEATPWD_REQUIRED);
        }

        // 提取、验证 验证码
        PwdRetakeCaptcha captcha = getCaptchaProvider().input(dto);
        // -1 : 验证码对象为空；, 0 : 验证码过期, 1：验证码在有效期内
        if (!"1".equals(getCaptchaProvider().valid(dto, captcha))) {
            return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_UPDATE_CAPTCHA_INVALID);
        }

        String uuid = dto.getUuid();
        String captcha_code = dto.getData().get(Constants.CAPTCHA);
        // 对比提交的验证码和存储的验证码
        if (!captcha.getCaptcha().equals(captcha_code) || !captcha.getUuid().equals(uuid)) {
            return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_UPDATE_CAPTCHA_INVALID);
        }

        // 查询账号信息
        BaseMap accountBefor = getPwdRetakeAccountService().getAccount(dto);
        if (null == accountBefor) {
            return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_UPDATE_FAIL);
        }

        try {

            // 修改密码
            accountBefor.put("newpassword", passwordEncoder.encode(newPassword));
            getPwdRetakeAccountService().resetPwd(accountBefor);

            // 清除验证码;防止复用
            getCaptchaProvider().evict(dto);

            return PwdRetakeResult.to(Constants.SUCCESS, Constants.PWD_UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_UPDATE_ERROR);
        }

    }

    public IPwdRetakeAccountService getPwdRetakeAccountService() {
        return pwdRetakeAccountService;
    }

    public CaptchaProvider getCaptchaProvider() {
        return captchaProvider;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }


}
