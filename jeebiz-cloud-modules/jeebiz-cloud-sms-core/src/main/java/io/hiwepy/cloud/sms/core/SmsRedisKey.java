package io.hiwepy.cloud.sms.core;

import hitool.core.lang3.time.DateUtils;
import org.springframework.data.redis.core.RedisKey;

import java.util.function.BiFunction;

public enum SmsRedisKey {

    /**
     * redis 短信黑名单缓存
     */
    SMS_BLACKLIST("短信黑名单", (mobile, type) -> {
        return RedisKey.getKeyStr(SmsRedisKeyConstant.SET_SMS_BLACK_LIST);
    }),
    /**
     * redis 短信每分钟发送缓存
     */
    SMS_LIMIT_MINUTE("短信每秒发送缓存", (mobile, type) -> {
        return RedisKey.getKeyStr(SmsRedisKeyConstant.STR_SMS_MOBILE_TIME, DateUtils.getDate("yyyyMMddHHmm"), type, mobile);
    }),
    /**
     * redis 短信每小时发送缓存
     */
    SMS_LIMIT_HOUR("短信每小时发送缓存", (mobile, type) -> {
        return RedisKey.getKeyStr(SmsRedisKeyConstant.STR_SMS_MOBILE_TIME, DateUtils.getDate("yyyyMMddHH"), type, mobile);
    }),
    /**
     * redis 短信每天发送缓存
     */
    SMS_LIMIT_DAY("短信每天发送缓存", (mobile, type) -> {
        return RedisKey.getKeyStr(SmsRedisKeyConstant.STR_SMS_MOBILE_TIME, DateUtils.getDate("yyyyMMdd"), type, mobile);
    }),
    /**
     * redis 短信设备发送缓存
     */
    SMS_LIMIT_DEVICE("短信设备发送缓存", (mobile, device) -> {
        return RedisKey.getKeyStr(SmsRedisKeyConstant.STR_SMS_DEV_TIME, mobile, device);
    }),
    /**
     * redis 短信发送锁
     */
    SMS_SEND_LOCK("短信发送锁", (mobile, bizId) -> {
        return RedisKey.getKeyStr(SmsRedisKeyConstant.STR_SMS_SEND_LOCK, mobile, bizId);
    }),
    /**
     * redis 短信发送状态
     */
    SMS_SEND_STATE("短信发送状态", (bizId, mobile) -> {
        return RedisKey.getKeyStr(SmsRedisKeyConstant.SMS_SMS_SEND_STATE, bizId, mobile);
    }),
    /**
     * redis 短信验证码缓存
     */
    SMS_CODE("短信验证码缓存", (mobile, type) -> {
        return RedisKey.getKeyStr(SmsRedisKeyConstant.STR_SMS_CODE, mobile, type);
    });

    private String desc;
    private BiFunction<Object, Object, String> function;

    SmsRedisKey(String desc, BiFunction<Object, Object, String> function) {
        this.desc = desc;
        this.function = function;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 1、获取全名称key
     *
     * @return
     */
    public String getKey() {
        return this.function.apply(null, null);
    }


    /**
     * 1、获取全名称key
     *
     * @param key1
     * @return
     */
    public String getKey(Object key1) {
        return this.function.apply(key1, null);
    }

    /**
     * 1、获取全名称key
     *
     * @param key1
     * @param key2
     * @return
     */
    public String getKey(Object key1, Object key2) {
        return this.function.apply(key1, key2);
    }

    public BiFunction<Object, Object, String> getFunction() {
        return function;
    }

}
