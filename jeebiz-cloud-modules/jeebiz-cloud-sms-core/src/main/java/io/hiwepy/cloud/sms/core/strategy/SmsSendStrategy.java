package io.hiwepy.cloud.sms.core.strategy;


import io.hiwepy.cloud.sms.core.bo.SendSmsBO;
import io.hiwepy.cloud.sms.core.bo.SendSmsResult;
import io.hiwepy.cloud.sms.core.enums.SmsChannel;
import io.hiwepy.cloud.sms.core.exception.SmsSendException;

/**
 * 短信发送策略
 */
public interface SmsSendStrategy {

    /**
     * 获取发送渠道
     *
     * @return
     */
    SmsChannel getChannel();

    <R extends SendSmsResult, O extends SendSmsBO> R send(O smsBo, Class<R> rtType) throws SmsSendException;

    <O extends SendSmsBO> boolean check(O smsBo) throws SmsSendException;

}
