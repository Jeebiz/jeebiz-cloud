/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.strategy;

import io.hiwepy.cloud.message.core.bo.InformSendBO;
import io.hiwepy.cloud.message.core.bo.InformSendResult;
import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LetterMessageSendStrategy extends AbstractMessageSendStrategy {

    @Override
    public InformSendChannel getChannel() {
        return InformSendChannel.NOTICE;
    }

    @Override
    protected <R extends InformSendResult, O extends InformSendBO> R handleSend(O sendBo) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
