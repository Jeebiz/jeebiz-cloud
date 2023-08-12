package io.hiwepy.cloud.sms.aliyun;


import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.cloud.sms.aliyun.bo.AliyunSendSmsBO0;
import io.hiwepy.cloud.sms.aliyun.bo.AliyunSendSmsResult;
import io.hiwepy.cloud.sms.core.SmsRedisKey;
import io.hiwepy.cloud.sms.core.SmsRedisKeyConstant;
import io.hiwepy.cloud.sms.core.bo.SendSmsBO;
import io.hiwepy.cloud.sms.core.bo.SendSmsResult;
import io.hiwepy.cloud.sms.core.enums.SmsChannel;
import io.hiwepy.cloud.sms.core.exception.SmsSendException;
import io.hiwepy.cloud.sms.core.strategy.AbstractSmsSendStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * Send Sms By Aliyun
 */
@Component
@Slf4j
public class AliyunSmsSendStrategy extends AbstractSmsSendStrategy {

    protected static final String CODE_OK = "OK";
    protected static final String CODE_BUSINESS_LIMIT_CONTROL = "isv.BUSINESS_LIMIT_CONTROL";
    @Autowired
    private AliyunSmsTemplate aliyunSmsTemplate;

    @Override
    public SmsChannel getChannel() {
        return SmsChannel.ALIYUN;
    }

    @Override
    protected <R extends SendSmsResult, O extends SendSmsBO> R handleSend(O smsBo, Class<R> rtType)
            throws SmsSendException {

        try {

            AliyunSendSmsBO0 subSmsBo = (AliyunSendSmsBO0) smsBo;
            /*
             * 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号] 例如+8613711112222， 其中前面有一个+号，86为国家码，13711112222为手机号，最多不要超过200个手机号
             */
            String phoneNumber = "+".concat(String.valueOf(smsBo.getCountryCode())).concat(smsBo.getPhone());
            /*
             * 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的 返回的 res 是一个 SendSmsResponse
             * 类的实例，与请求对象对应
             */
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("code", smsBo.getVcode());
            if (MapUtils.isNotEmpty(smsBo.getTemplateParams())) {
                templateParams.putAll(smsBo.getTemplateParams());
            }
            SendSmsResponse res = aliyunSmsTemplate.send(phoneNumber, subSmsBo.getTemplateCode(), templateParams, smsBo.getSmsNo(), subSmsBo.getSignIndx());
            if (StringUtils.equalsIgnoreCase(res.getCode(), CODE_BUSINESS_LIMIT_CONTROL)) {
                throw new SmsSendException(ApiCode.SC_FAIL, "sms.send.max.limit");
            }

            // 4、发送短信并记录缓存
            if (res.getCode().equals(CODE_OK)) {
                String smsKey = SmsRedisKey.SMS_CODE.getKey(smsBo.getPhone(), smsBo.getBizId());
                getRedisOperationTemplate().set(smsKey, smsBo.getVcode(), SmsRedisKeyConstant.SMS_EXPIRE);
            } else {
                log.error("aliyun sms send fail, code : {}, error : {}", res.getCode(), res.getMessage());
            }
            // 返回发送结果信息
            AliyunSendSmsResult sendResult = AliyunSendSmsResult.builder()
                    .bizId(res.getBizId())
                    .channel(smsBo.getChannel())
                    .code(res.getCode())
                    .message(res.getMessage())
                    .requestId(res.getRequestId())
                    .status(res.getCode().equals(CODE_OK) ? 1 : 0)
                    .userId(smsBo.getUserId())
                    .build();

            return rtType.cast(sendResult);
        } catch (ClientException e) {
            throw new SmsSendException(ApiCode.SC_FAIL, "sms.send.fail");
        }

    }

}