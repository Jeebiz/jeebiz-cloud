package io.hiwepy.cloud.authz.passwd.setup.strategy.def;

import io.hiwepy.boot.api.dao.entities.BaseMap;
import io.hiwepy.cloud.authz.passwd.setup.Constants;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeCaptcha;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeField;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeResult;
import io.hiwepy.cloud.authz.passwd.setup.provider.CaptchaOutputProvider;
import io.hiwepy.cloud.authz.passwd.setup.provider.CaptchaProvider;
import io.hiwepy.cloud.authz.passwd.setup.provider.PwdUpdateProvider;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdRetakeStrategyAdapter;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeAdviceDTO;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeCheckDTO;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeDoneDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证码方式找回密码策略Adapter
 */
public abstract class PwdCaptchaRetakeStrategyAdapter extends PwdRetakeStrategyAdapter {

    protected Logger LOG = LoggerFactory.getLogger(PwdCaptchaRetakeStrategyAdapter.class);

    /**
     * 验证码生成服务提供者
     */
    protected CaptchaProvider captchaProvider;
    /**
     * 验证码输出对象提供者
     */
    protected CaptchaOutputProvider captchaOutputProvider;

    protected PwdUpdateProvider pwdUpdateProvider;

    @Override
    public PwdRetakeResult advice(PwdRetakeAdviceDTO adviceDTO) {

        // 第1步：从数据提供者获取账户相关信息
        PwdRetakeDto dto = new PwdRetakeDto();
        dto.setUsername(adviceDTO.getUsername());
        dto.setUuid(adviceDTO.getUuid());
        BaseMap rtMap = getAccountProvider().input(dto);
        if (null == rtMap) {
            //如果没有根据参数获取到数据，则返回提醒信息
            return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_RETAKE_INPUT_NULL, rtMap);
        }

        // 第2步：必要字段检查
        int index = 0;
        do {
            PwdRetakeField field = bindFields[index];
            Object field_val = rtMap.get(field.getName());
            if (field.isRequired() && (!rtMap.containsKey(field.getName()) || field_val == null || StringUtils.isEmpty(field_val.toString()))) {
                return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_RETAKE_FILED_REQUIRED, rtMap);
            }
            //扩展别名值，解决不同系统参数名称不同问题
            if (!StringUtils.isEmpty(field.getAlias())) {
                rtMap.put(field.getAlias(), field_val);
            }
            index++;
        } while (index < bindFields.length);

        // 第3步：提取、验证（ 不通过则创建）、存储验证码
        PwdRetakeCaptcha captcha = getCaptchaProvider().input(dto);
        // -1 : 验证码对象为空；, 0 : 验证码过期, 1：验证码在有效期内
        if (!"1".equals(getCaptchaProvider().valid(dto, captcha))) {
            // 验证码失效,从新生成、存储
            captcha = getCaptchaProvider().gen(dto);
            try {
                if (!getCaptchaProvider().store(dto, captcha)) {
                    //验证码没有存储
                    return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_RETAKE_CAPTCHA_STORE_FAIL, rtMap);
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
                //验证码存储失败
                return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_RETAKE_CAPTCHA_STORE_ERROR, rtMap);
            }
        }
        dto.getData().put(Constants.CAPTCHA, captcha.getCaptcha());

        // 第4步：发送验证码
        for (PwdRetakeField bindField : bindFields) {
            String key = bindField.getName();
            dto.getData().put(key, (String) rtMap.get(key));
            //扩展别名值，解决不同系统参数名称不同问题
            if (!StringUtils.isEmpty(bindField.getAlias())) {
                dto.getData().put(bindField.getAlias(), (String) rtMap.get(key));
            }
        }

        // 验证码的唯一ID;用于检测验证码的安全性
        rtMap.put("uuid", captcha.getUuid());

        // 模拟成功逻辑
        //return ResultData.to(Constants.SUCCESS, Constants.PWD_RETAKE_CAPTCHA_OUTPUT_SUCCESS,   rtMap);

        try {
            getCaptchaOutputProvider().output(dto);
            return PwdRetakeResult.to(Constants.SUCCESS, Constants.PWD_RETAKE_CAPTCHA_OUTPUT_SUCCESS, rtMap);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            return PwdRetakeResult.to(Constants.ERROR, Constants.PWD_RETAKE_CAPTCHA_OUTPUT_ERROR, rtMap);
        }
    }

    @Override
    public PwdRetakeResult check(PwdRetakeCheckDTO checkDTO) {
        // 第1步：提取、验证 验证码
        PwdRetakeDto dto = new PwdRetakeDto();
        dto.setUsername(checkDTO.getUsername());
        dto.setUuid(checkDTO.getUuid());
        PwdRetakeCaptcha captcha = getCaptchaProvider().input(dto);
        // -1 : 验证码对象为空；, 0 : 验证码过期, 1：验证码在有效期内
        if (!"1".equals(getCaptchaProvider().valid(dto, captcha))) {
            return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_RETAKE_CAPTCHA_EXPIRE);
        }
        String uuid = checkDTO.getUuid();
        String captcha_code = checkDTO.getCaptcha();
        // 对比提交的验证码和存储的验证码
        if (StringUtils.equalsIgnoreCase(captcha.getCaptcha(), captcha_code)
                && StringUtils.equalsIgnoreCase(captcha.getUuid(), uuid)) {
            return PwdRetakeResult.to(Constants.SUCCESS, Constants.PWD_RETAKE_CAPTCHA_VERIFI_PASS);
        }
        return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_RETAKE_CAPTCHA_VERIFI_UNPASS);
    }

    @Override
    public PwdRetakeResult done(PwdRetakeDoneDTO doneDTO) {
        PwdRetakeDto dto = new PwdRetakeDto();
        dto.setUsername(doneDTO.getUsername());
        dto.setUuid(doneDTO.getUuid());

        // 更新密码
        PwdRetakeResult result = getPwdUpdateProvider().update(dto);

        return result;
    }

    public CaptchaProvider getCaptchaProvider() {
        return captchaProvider;
    }

    public void setCaptchaProvider(CaptchaProvider captchaProvider) {
        this.captchaProvider = captchaProvider;
    }

    public CaptchaOutputProvider getCaptchaOutputProvider() {
        return captchaOutputProvider;
    }

    public void setCaptchaOutputProvider(CaptchaOutputProvider captchaOutputProvider) {
        this.captchaOutputProvider = captchaOutputProvider;
    }

    public PwdUpdateProvider getPwdUpdateProvider() {
        return pwdUpdateProvider;
    }

    public void setPwdUpdateProvider(PwdUpdateProvider pwdUpdateProvider) {
        this.pwdUpdateProvider = pwdUpdateProvider;
    }

}