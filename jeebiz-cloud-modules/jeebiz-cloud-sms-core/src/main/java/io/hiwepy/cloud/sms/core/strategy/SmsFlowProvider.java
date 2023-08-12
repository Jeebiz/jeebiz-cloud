package io.hiwepy.cloud.sms.core.strategy;

import io.hiwepy.cloud.sms.core.bo.SendSmsBO;
import io.hiwepy.cloud.sms.core.bo.SendSmsResult;
import io.hiwepy.cloud.sms.core.enums.SmsChannel;
import io.hiwepy.cloud.sms.core.exception.SmsSendException;

public interface SmsFlowProvider {

    default <R extends SendSmsResult, O extends SendSmsBO> boolean hasFlow(SmsChannel channel, O smsBo) throws SmsSendException {
        return Boolean.FALSE;
    }

    ;

    default <R extends SendSmsResult, O extends SendSmsBO> void recordFlow(SmsChannel channel, O smsBo, R sendRt) throws SmsSendException {

    }

    ;

}
