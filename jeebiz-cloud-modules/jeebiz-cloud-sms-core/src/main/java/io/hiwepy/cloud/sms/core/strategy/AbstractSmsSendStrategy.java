package io.hiwepy.cloud.sms.core.strategy;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import hitool.core.lang3.time.CalendarUtils;
import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.boot.api.exception.BizRuntimeException;
import io.hiwepy.boot.api.sequence.Sequence;
import io.hiwepy.cloud.sms.core.SmsCountryTemplate;
import io.hiwepy.cloud.sms.core.SmsRedisKey;
import io.hiwepy.cloud.sms.core.bo.SendSmsBO;
import io.hiwepy.cloud.sms.core.bo.SendSmsResult;
import io.hiwepy.cloud.sms.core.enums.SmsState;
import io.hiwepy.cloud.sms.core.exception.SmsSendException;
import io.hiwepy.cloud.sms.core.exception.SmsSendRepeatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Objects;

@Slf4j
public abstract class AbstractSmsSendStrategy implements SmsSendStrategy, InitializingBean, ApplicationEventPublisherAware {

    private final String DEBUG_CODE = "000000";

    @Autowired
    private SmsFlowProvider smsFlowProvider;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Sequence sequence;
    @Autowired
    private SmsCountryTemplate countryTemplate;
    @Autowired
    private RedisOperationTemplate redisOperationTemplate;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <R extends SendSmsResult, O extends SendSmsBO> R send(O smsBo, Class<R> rtType) throws SmsSendException {

        log.info("smsBo ：{}", JSONObject.toJSONString(smsBo));
        // 1、验证请求，如果有不满足有要求则应抛出异常
        boolean flag = this.preCheck(smsBo);
        if (!flag) {
            return null;
        }
        // 2、钩子方法 用于扩展
        customizedMethod(smsBo);
        // 3、通过检查则继续处理预订单创建
        R sendRt = this.handleSend(smsBo, rtType);
        // 4、记录支付流水；短信发送状态（ 0：发送失败、1：发送成功）
        if (Objects.nonNull(sendRt) && sendRt.getStatus() == 1) {
            smsFlowProvider.recordFlow(this.getChannel(), smsBo, sendRt);
        }
        afterSend(smsBo, sendRt);
        // 5、返回支付结果
        return sendRt;
    }

    protected <O extends SendSmsBO> boolean preCheck(O smsBo) throws SmsSendException {
        log.debug("BizId : {}", smsBo.getBizId());

        // 1、初始化短信流水编号和短信内容
        smsBo.setSmsNo(Objects.toString(smsBo.getSmsNo(), getSequence().nextId().toString()));
        smsBo.setContent(Objects.toString(smsBo.getContent(), smsBo.getVcode()));

        // 2、检查短信发送流水
        boolean hasFlow = smsFlowProvider.hasFlow(this.getChannel(), smsBo);
        if (hasFlow) {
            throw new SmsSendRepeatException(ApiCode.SC_FAIL, "sms.send.repeat");
        }

        // 3、检查短信发送状态（进行幂等处理）
        String sendStateKey = SmsRedisKey.SMS_SEND_STATE.getKey(smsBo.getSmsNo(), Objects.toString(smsBo.getUserId(), smsBo.getUserId()));
        boolean lockable = getRedisOperationTemplate().setNx(sendStateKey, SmsState.SENDING.getState(), Duration.ofMinutes(10));
        if (!lockable) {
            throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.sending");
        }

        // 4、检查是否调试环境或忽略短信发送前的检查
        if (smsBo.isDebug() || smsBo.isIgnoreCheck()) {
            return true;
        }

        // 5、验证手机号的正确性
        PhoneNumber phoneNumber = new PhoneNumber().setCountryCode(smsBo.getCountryCode())
                .setNationalNumber(Long.parseLong(smsBo.getPhone()));
        boolean validNumberForRegion = PhoneNumberUtil.getInstance().isValidNumber(phoneNumber);
        if (!validNumberForRegion) {
            throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.phone.invalid");
        }

        // 6、检查短信发送权限
        // 短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时 ，累计10条/天。
        // 短信通知： 使用同一个签名和同一个短信模板id，对同一个手机号码发送短信通知，支持50条/日

        // 6.1、1条/分钟
        String phoneTimeMinuteKey = SmsRedisKey.SMS_LIMIT_MINUTE.getKey(smsBo.getPhone(), smsBo.getBizId());
        String timesOfMinute = redisOperationTemplate.getString(phoneTimeMinuteKey);
        if (timesOfMinute != null && Integer.parseInt(timesOfMinute) > smsBo.getChannel().getTimesOfMinute()) {
            throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.second.limit");
        }
        // 6.2、5条/小时
        String phoneTimeHourKey = SmsRedisKey.SMS_LIMIT_HOUR.getKey(smsBo.getPhone(), smsBo.getBizId());
        String timesOfHour = redisOperationTemplate.getString(phoneTimeHourKey);
        if (timesOfHour != null && Integer.parseInt(timesOfHour) > smsBo.getChannel().getTimesOfHour()) {
            throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.hour.limit");
        }
        // 6.3、10条/天
        String phoneTimeDayKey = SmsRedisKey.SMS_LIMIT_DAY.getKey(smsBo.getPhone(), smsBo.getBizId());
        String timesOfDay = redisOperationTemplate.getString(phoneTimeDayKey);
        if (timesOfDay != null && Integer.parseInt(timesOfDay) > smsBo.getChannel().getTimesOfDay()) {
            throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.day.limit");
        }
        // 6.4、黑名单
        String blacklistKey = SmsRedisKey.SMS_BLACKLIST.getKey(smsBo.getPhone(), smsBo.getBizId());
        if (redisOperationTemplate.sHasKey(blacklistKey, smsBo.getPhone())) {
            throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.backlist.limit");
        }

        return true;
    }

    protected <O extends SendSmsBO> void customizedMethod(O smsBo) throws SmsSendException {
        // TODO Auto-generated method stub
    }

    ;

    protected abstract <R extends SendSmsResult, O extends SendSmsBO> R handleSend(O smsBo, Class<R> rtType) throws SmsSendException;


    protected <R extends SendSmsResult, O extends SendSmsBO> void afterSend(O smsBo, R sendRt) throws SmsSendException {

        // 1、短信发送逻辑执行结束根据结果，更新发送状态
        if (Objects.nonNull(sendRt) && sendRt.getStatus() == 1) {
            // 1、短信发送成功，更新发送状态
            String sendStateKey = SmsRedisKey.SMS_SEND_STATE.getKey(smsBo.getSmsNo(), Objects.toString(smsBo.getUserId(), smsBo.getUserId()));
            getRedisOperationTemplate().set(sendStateKey, SmsState.SUCCESS.getState(), Duration.ofMinutes(10));
        } else {
            // 2、短信发送失败，记录失败状态
            String sendStateKey = SmsRedisKey.SMS_SEND_STATE.getKey(smsBo.getSmsNo(), Objects.toString(smsBo.getUserId(), smsBo.getUserId()));
            getRedisOperationTemplate().set(sendStateKey, SmsState.FAIL.getState(), Duration.ofMinutes(10));
        }

        // 检查是否调试环境或忽略短信发送后的处理
        if (smsBo.isDebug() || smsBo.isIgnoreCheck()) {
            return;
        }

        String phoneTimeMinuteKey = SmsRedisKey.SMS_LIMIT_MINUTE.getKey(smsBo.getPhone(), smsBo.getBizId());
        getRedisOperationTemplate().incr(phoneTimeMinuteKey, 1, Duration.ofMinutes(1));

        String phoneTimeHourKey = SmsRedisKey.SMS_LIMIT_HOUR.getKey(smsBo.getPhone(), smsBo.getBizId());
        getRedisOperationTemplate().incr(phoneTimeHourKey, 1, Duration.ofHours(1));

        String phoneTimeDayKey = SmsRedisKey.SMS_LIMIT_DAY.getKey(smsBo.getPhone(), smsBo.getBizId());
        getRedisOperationTemplate().incr(phoneTimeDayKey, 1, CalendarUtils.getSecondsNextEarlyMorning());

    }

    ;

    @Override
    public <O extends SendSmsBO> boolean check(O smsBo) throws SmsSendException {
        // 验证手机号
        PhoneNumber swissMobileNumber = new PhoneNumber().setCountryCode(smsBo.getCountryCode())
                .setNationalNumber(Long.parseLong(smsBo.getPhone()));
        boolean validNumberForRegion = PhoneNumberUtil.getInstance().isValidNumber(swissMobileNumber);
        if (!validNumberForRegion && !(smsBo.isDebug() && StringUtils.equals(smsBo.getVcode(), DEBUG_CODE))) {
            throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.phone.invalid");
        }
        String smsKey = SmsRedisKey.SMS_CODE.getKey(smsBo.getPhone(), smsBo.getBizId());
        String smsCode = (String) redisOperationTemplate.get(smsKey);
        if (!StringUtils.equals(smsBo.getVcode(), smsCode) && !(smsBo.isDebug() && StringUtils.equals(smsBo.getVcode(), DEBUG_CODE))) {
            throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.check.vcode.invalid");
        }
        redisOperationTemplate.del(smsKey);
        return true;
    }

    public ApplicationEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    protected Sequence getSequence() {
        return sequence;
    }

    public RedisOperationTemplate getRedisOperationTemplate() {
        return redisOperationTemplate;
    }

}
