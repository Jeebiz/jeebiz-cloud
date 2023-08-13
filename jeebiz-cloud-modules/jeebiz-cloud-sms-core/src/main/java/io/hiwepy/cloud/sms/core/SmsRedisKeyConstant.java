package io.hiwepy.cloud.sms.core;

public class SmsRedisKeyConstant {

    /**
     * 手机/设备发送短信次数记录过期时间
     */
    public static Long SMS_TIME_EXPIRE = 3500 * 6L;
    /**
     * 验证码过期时间
     */
    public static Long SMS_EXPIRE = 60 * 5L;

    /**
     * 手机号黑名单
     */
    public static String SET_SMS_BLACK_LIST = "set:sms:blacklist";
    /**
     * 记录手机号发短信次数
     */
    public static String STR_SMS_MOBILE_TIME = "str:sms:mobile:time:";
    /**
     * 记录设备发短信次数
     */
    public static String STR_SMS_DEV_TIME = "str:sms:dev:time:";
    /**
     * 发送短信锁
     */
    public static String STR_SMS_SEND_LOCK = "str:sms:lock:";
    /**
     * 短信发送状态
     */
    public static String SMS_SMS_SEND_STATE = "str:sms:state:";
    /**
     * 短信验证码 type + 手机号
     */
    public static String STR_SMS_CODE = "str:sms:code:";


}
