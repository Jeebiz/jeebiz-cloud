/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.weixin;

import io.hiwepy.cloud.message.core.bo.InformSendBO;
import io.hiwepy.cloud.message.core.bo.InformSendResult;
import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import io.hiwepy.cloud.message.core.strategy.AbstractMessageSendStrategy;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.subscribe.WxMpSubscribeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WxMpSubscribeMessageSendStrategy extends AbstractMessageSendStrategy {

    @Autowired
    private WxMpService wxMpService;

    @Override
    public InformSendChannel getChannel() {
        return InformSendChannel.WXMP_SUBSCRIBE;
    }

    @Override
    protected <R extends InformSendResult, O extends InformSendBO> R handleSend(O sendBo) throws Exception {

        try {
            WxMpSubscribeMessage inform = null;
            getWxMpService().getSubscribeMsgService().send(inform);
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
        }

        return null;

    }

    public WxMpService getWxMpService() {
        return wxMpService;
    }


}
