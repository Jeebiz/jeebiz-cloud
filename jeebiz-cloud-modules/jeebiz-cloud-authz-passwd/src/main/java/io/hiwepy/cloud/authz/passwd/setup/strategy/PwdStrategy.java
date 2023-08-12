package io.hiwepy.cloud.authz.passwd.setup.strategy;

public final class PwdStrategy {

    /**
     * 默认：default
     */
    public static final String DEFAULT_STRATEGY = "default";
    /**
     * 邮件：email
     */
    public static final String PWD_RETAKE_BY_EMAIL = "email";
    /**
     * 手机验证码：oksms
     */
    public static final String PWD_RETAKE_BY_PHONE = "oksms";
    /**
     * 动态口令：One-time Password （otp）
     */
    public static final String PWD_RETAKE_BY_OTP = "otp";

}
