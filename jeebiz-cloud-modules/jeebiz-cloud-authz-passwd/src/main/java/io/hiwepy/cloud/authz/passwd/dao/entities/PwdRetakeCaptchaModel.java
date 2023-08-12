package io.hiwepy.cloud.authz.passwd.dao.entities;

import io.hiwepy.boot.api.dao.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * 验证码校验表，绑定时存储验证信息，验证后立即物理删除
 */
@Alias("PwdRetakeCaptchaModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class PwdRetakeCaptchaModel extends BaseEntity<PwdRetakeCaptchaModel> {

    /**
     * 验证码表ID，用于数据删除
     */
    protected String id;
    /**
     * 验证码关联外部UUID;用于业务逻辑关联查询
     */
    protected String uuid;
    /**
     * 验证码值
     */
    protected String captcha;
    /**
     * 验证码发送时间
     */
    protected String time24;

}
