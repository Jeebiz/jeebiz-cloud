package io.hiwepy.cloud.message.core.strategy;

import io.hiwepy.cloud.message.core.bo.InformSendBO;
import io.hiwepy.cloud.message.core.bo.InformSendResult;
import io.hiwepy.cloud.message.core.emums.InformSendChannel;

import java.math.BigDecimal;

/**
 * 推送消息
 */
public interface MessageSendStrategy {

    public static final String PAYMENT_SUCCESS = "success";
    public static final String PAYMENT_FAIL = "fail";
    public static final BigDecimal HUNDRED = new BigDecimal(100);

    /**
     * 获取支付渠道
     *
     * @return
     */
    InformSendChannel getChannel();

    <R extends InformSendResult, O extends InformSendBO> R send(O sendBo, Class<R> rtType) throws Exception;

}
