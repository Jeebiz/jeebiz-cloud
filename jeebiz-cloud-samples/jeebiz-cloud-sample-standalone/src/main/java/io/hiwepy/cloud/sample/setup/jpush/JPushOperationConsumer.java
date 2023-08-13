/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.sample.setup.jpush;

import cn.jpush.spring.boot.JPushTemplate;
import cn.jpush.spring.boot.PushObject;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.spring.boot.AbstractMessageListener;
import com.aliyun.openservices.spring.boot.annotation.MessageConsumer;
import io.hiwepy.cloud.sample.setup.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@MessageConsumer(topic = Constants.JPUSH_TOPIC, tag = Constants.TAG_ALL)
public class JPushOperationConsumer extends AbstractMessageListener {

    @Autowired
    protected RedisOperationTemplate redisOperationTemplate;
    @Autowired
    protected JPushTemplate jPushTemplate;

    @Override
    public int apply(Message message) {
        return 0;
    }

    @Override
    public void consume(int count, Message message) throws Exception {

        log.info("{} {} ：剩余重试次数{}", Constants.JPUSH_TOPIC, Constants.TAG_ALL, message.getReconsumeTimes());

        PushObject pushObject = JSONObject.parseObject(message.getBody(), PushObject.class);

        jPushTemplate.sendPush(pushObject);


    }


}
