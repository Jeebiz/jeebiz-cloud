/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.goeasy;

import io.goeasy.GoEasy;
import io.hiwepy.cloud.message.core.bo.InformSendBO;
import io.hiwepy.cloud.message.core.bo.InformSendResult;
import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import io.hiwepy.cloud.message.core.strategy.AbstractMessageSendStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class GoEasyMessageSendStrategy extends AbstractMessageSendStrategy {

    protected GoEasy goEasy = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        goEasy = new GoEasy("hangzhou.goeasy.io", "BC-de8c5bb452a4496f8c2e6eb81c6b3309");
        //goEasy.otp(secretKey)
    }

    @Override
    public InformSendChannel getChannel() {
        return InformSendChannel.NOTICE;
    }

    @Override
    protected <R extends InformSendResult, O extends InformSendBO> R handleSend(O sendBo) throws Exception {

        try {

            Map<String, Object> msg = new HashMap<>();
			
			/*
			msg.put("title", inform.getTitle());
			msg.put("content", inform.getContent());
			msg.put("bid", inform.getBid());
			msg.put("payload", StringUtils.hasText(inform.getPayload()) ? JSONObject.parse(inform.getPayload()) : Maps.newHashMap());
			
			goEasy.publish(inform.getToUid(), JSONObject.toJSONString(msg), new PublishListener(){
			   
				@Override
			    public void onSuccess() {
			        System.out.print("消息发布成功。");
			    }
				
			    @Override
			    public void onFailed(GoEasyError error) {
			        String rtMsg = "消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent();
			        System.out.print(rtMsg);
			        throw new BizRuntimeException(rtMsg);
			    }
			    
			});*/
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
