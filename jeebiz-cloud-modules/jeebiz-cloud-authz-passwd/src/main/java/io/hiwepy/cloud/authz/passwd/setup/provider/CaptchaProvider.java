package io.hiwepy.cloud.authz.passwd.setup.provider;

import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeCaptcha;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;

/**
 * 验证码生成,存储策略接口
 */
public interface CaptchaProvider {

    /**
     * 服务提供者名称
     */
    public String name();

    /**
     * 验证码提取，用于获取上次存储的验证码
     */
    public PwdRetakeCaptcha input(PwdRetakeDto dto);

    /**
     * 验证上次的验证码是否有效
     *
     * @param data
     * @param captcha
     * @return -1 : 验证码对象为空；, 0 : 验证码过期, 1：验证码在有效期内
     */
    public String valid(PwdRetakeDto dto, PwdRetakeCaptcha captcha);

    /**
     * 重新生成新的验证码
     *
     * @return
     */
    public PwdRetakeCaptcha gen(PwdRetakeDto dto);

    /**
     * 存储生成后的验证码
     *
     * @param captcha
     * @return
     */
    public boolean store(PwdRetakeDto dto, PwdRetakeCaptcha captcha);

    /**
     * 手动清除无用的验证码
     *
     * @param data
     * @return
     */
    public boolean evict(PwdRetakeDto dto);

}
