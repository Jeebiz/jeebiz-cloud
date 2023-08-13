/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.weixin;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import io.hiwepy.cloud.message.core.bo.InformSendBO;
import io.hiwepy.cloud.message.core.bo.InformSendResult;
import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import io.hiwepy.cloud.message.core.strategy.AbstractMessageSendStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 订阅消息.
 * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html
 */
@Component
@Slf4j
public class WxMaSubscribeMessageSendStrategy extends AbstractMessageSendStrategy {

    @Autowired
    private WxMaService wxMaService;

    @Override
    public InformSendChannel getChannel() {
        return InformSendChannel.WXMA_NOTICE;
    }

    @Override
    protected <R extends InformSendResult, O extends InformSendBO> R handleSend(O sendBo) throws Exception {

        WxMaSubscribeMessage inform = null;
        getWxMaService().getMsgService().sendSubscribeMsg(inform);

        return null;

    }

    public WxMaService getWxMaService() {
        return wxMaService;
    }
}
