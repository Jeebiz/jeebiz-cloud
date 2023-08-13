/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.jpush;

import cn.jpush.spring.boot.JPushTemplate;
import cn.jpush.spring.boot.PushObject;
import io.hiwepy.cloud.message.core.bo.InformSendBO;
import io.hiwepy.cloud.message.core.bo.InformSendResult;
import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import io.hiwepy.cloud.message.core.strategy.AbstractMessageSendStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class JPushMessageSendStrategy extends AbstractMessageSendStrategy {

    @Autowired
    protected JPushTemplate jPushTemplate;

    @Override
    public InformSendChannel getChannel() {
        return InformSendChannel.JPUSH;
    }

    @Override
    protected <R extends InformSendResult, O extends InformSendBO> R handleSend(O sendBo) throws Exception {


        PushObject pushObject = new PushObject();
        pushObject.setMsgContent(sendBo.getFlowNo());

        jPushTemplate.sendPush(Arrays.asList(JPushConstants.PUSH_PREFIX + sendBo.getUserId()), pushObject);

        return null;

    }

}
