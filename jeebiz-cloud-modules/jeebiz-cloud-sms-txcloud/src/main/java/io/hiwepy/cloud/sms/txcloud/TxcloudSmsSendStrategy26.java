package io.hiwepy.cloud.sms.txcloud;


import com.tencentcloud.spring.boot.sms.TencentSmsTemplate;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.cloud.sms.core.bo.SendSmsBO;
import io.hiwepy.cloud.sms.core.bo.SendSmsResult;
import io.hiwepy.cloud.sms.core.enums.SmsChannel;
import io.hiwepy.cloud.sms.core.exception.SmsSendException;
import io.hiwepy.cloud.sms.core.strategy.AbstractSmsSendStrategy;
import io.hiwepy.cloud.sms.txcloud.bo.TxcloudSendSmsBO0;
import io.hiwepy.cloud.sms.txcloud.bo.TxcloudSendSmsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Send Sms By Txcloud
 */
@Component
public class TxcloudSmsSendStrategy26 extends AbstractSmsSendStrategy {

    protected static final String CODE_OK = "OK";
    @Autowired
    private TencentSmsTemplate tencentSmsTemplate;

    @Override
    public SmsChannel getChannel() {
        return SmsChannel.ALIYUN;
    }

    @Override
    protected <R extends SendSmsResult, O extends SendSmsBO> R handleSend(O smsBo, Class<R> rtType)
            throws SmsSendException {

        try {

            TxcloudSendSmsBO0 subSmsBo = (TxcloudSendSmsBO0) smsBo;

            /*
             * 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号] 例如+8613711112222， 其中前面有一个+号，86为国家码，13711112222为手机号，最多不要超过200个手机号
             */
            String phoneNumber = "+".concat(String.valueOf(smsBo.getCountryCode())).concat(smsBo.getPhone());
            /*
             * 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的 返回的 res 是一个 SendSmsResponse
             * 类的实例，与请求对象对应
             */
            SendSmsResponse res = tencentSmsTemplate.send(phoneNumber, subSmsBo.getTemplateCode(), smsBo.getVcode());
            SendStatus status = res.getSendStatusSet()[0];
            if (!status.getCode().equals(CODE_OK)) {
                throw new TencentCloudSDKException(res.getSendStatusSet()[0].getMessage());
            }

            // 返回发送结果信息
            TxcloudSendSmsResult sendResult = TxcloudSendSmsResult.builder()
                    .bizId(smsBo.getBizId())
                    .channel(smsBo.getChannel())
                    .code(status.getCode())
                    .message(status.getMessage())
                    .requestId(res.getRequestId())
                    .status(status.getCode().equals(CODE_OK) ? 1 : 0)
                    .userId(smsBo.getUserId())
                    .build();

            return rtType.cast(sendResult);
        } catch (TencentCloudSDKException e) {
            throw new SmsSendException(ApiCode.SC_BAD_REQUEST, "sms.send.fail");
        }
    }

}