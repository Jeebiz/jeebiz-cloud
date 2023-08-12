package io.hiwepy.cloud.authz.passwd.setup.provider.def;

import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeCaptcha;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeTime;
import io.hiwepy.cloud.authz.passwd.setup.provider.CaptchaProvider;
import io.hiwepy.cloud.authz.passwd.setup.provider.DatetimeProvider;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.text.SimpleDateFormat;
import java.time.Duration;

/**
 * 基于缓存服务的验证码服务提供实现
 */
public class CaptchaRedisProvider implements CaptchaProvider, InitializingBean {

    // 时间戳格式化格式
    protected String format = "yyyy-MM-dd HH:mm:ss";
    protected SimpleDateFormat sdf = new SimpleDateFormat(format);
    // 验证码过期时间
    protected int effectTime = 1000 * 30;

    // 缓存服务提供对象
    protected final RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOperations;
    // 时间戳提供对象
    protected final DatetimeProvider timeProvider;

    public CaptchaRedisProvider(RedisTemplate<String, Object> redisTemplate, DatetimeProvider timeProvider) {
        this.redisTemplate = redisTemplate;
        this.timeProvider = timeProvider;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.valueOperations = getRedisTemplate().opsForValue();
    }

    @Override
    public String name() {
        return this.getClass().getName();
    }

    @Override
    public PwdRetakeCaptcha input(PwdRetakeDto data) {
        // 查询缓存对象
        String key = String.format("passwd:captcha:%s", data.getUuid());
        Object wrapper = getValueOperations().get(key);
        if (wrapper != null) {
            return (PwdRetakeCaptcha) wrapper;
        }
        return null;
    }

    /**
     * 只要能从缓存服务中获取到验证码就表示该验证码尚未失效，肯定是有效的值
     * return ： -1 : 验证码对象为空；, 0 : 验证码过期, 1：验证码在有效期内
     */
    @Override
    public String valid(PwdRetakeDto data, PwdRetakeCaptcha captcha) {
        if (captcha == null) {
            return "-1";
        }
        //当前时间
        PwdRetakeTime now = getTimeProvider().dateTime(getFormat(), getEffectTime());
        //验证码发送时间
        PwdRetakeTime send = new PwdRetakeTime(captcha.getTimestamp(), getFormat(), getEffectTime());
        //验证码对象不为空且发送时间在有效时间内
        if (send.compareTo(now) > 0) {
            return "1";
        }
        this.evict(data);
        return "0";
    }

    @Override
    public PwdRetakeCaptcha gen(PwdRetakeDto data) {
        // 当前时间
        PwdRetakeTime now = getTimeProvider().dateTime(getFormat(), getEffectTime());
        return new PwdRetakeCaptcha(data.getUuid(), RandomStringUtils.random(6), now.getTimestamp());
    }

    @Override
    public boolean store(PwdRetakeDto data, PwdRetakeCaptcha captcha) {
        // 存储缓存对象
        String key = String.format("passwd:captcha:%s", data.getUuid());
        getValueOperations().set(key, captcha, Duration.ofMillis(effectTime));
        return true;
    }

    @Override
    public boolean evict(PwdRetakeDto data) {
        // 删除缓存中的过期值
        String key = String.format("passwd:captcha:%s", data.getUuid());
        getRedisTemplate().delete(key);
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

    public DatetimeProvider getTimeProvider() {
        return timeProvider;
    }

    public ValueOperations<String, Object> getValueOperations() {
        return valueOperations;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }


}
