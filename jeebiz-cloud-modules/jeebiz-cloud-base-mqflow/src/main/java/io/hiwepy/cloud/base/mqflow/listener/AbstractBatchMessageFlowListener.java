package io.hiwepy.cloud.base.mqflow.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.spring.boot.AbstractBatchMessageListener;
import io.hiwepy.cloud.base.mqflow.service.IMqFlowService;
import io.hiwepy.cloud.base.redis.BizRedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;

import java.time.Duration;
import java.util.List;

public abstract class AbstractBatchMessageFlowListener extends AbstractBatchMessageListener {

    @Autowired
    private IMqFlowService mqFlowService;
    @Autowired
    private RedisOperationTemplate redisOperation;

    @Override
    public int apply(Message message) {
        // 1、消息队列消息消费锁
        String mqLockKey = BizRedisKey.MQ_CONSUME_LOCK.getKey(message.getKey());
        // 2、尝试加锁，锁过期时间5分钟
        boolean locked = redisOperation.setNx(mqLockKey, message.getKey(), Duration.ofMinutes(5));
        // 3、如果加锁成功，表示当前消息在5分钟内是第一次进行消费
        if (locked) {
            // 3.1、为了防止因为锁过期导致的并发问题，这里二次进行数据库查重
            Long count = mqFlowService.countMqFlowByMqFlowId(message.getKey());
            return count.intValue();
        }
        // 4、没有加锁成功表示，同ID消息已经在被其他消费者消费，如果有并发问题，此处返回1则会把因并发导致的重复消息标记为已经消费
        return 1;
    }

    @Override
    public Action consume(List<Message> messages, ConsumeContext context) {
        // 1、调用父类接口执行消费消息
        Action action = super.consume(messages, context);
        // 2、如果返回状态为消费提交，表示成功完成消费，则记录流水
        if (Action.CommitMessage.compareTo(action) == 0) {
            for (Message message : messages) {
                mqFlowService.save(message.getKey(), new String(message.getBody()));
            }
        }
        // 3、如果返回状态为稍后重试，表示消费失败，则移除全局锁
        if (Action.ReconsumeLater.compareTo(action) == 0) {
            // 3.1、消息队列消息消费锁
            for (Message message : messages) {
                String mqLockKey = BizRedisKey.MQ_CONSUME_LOCK.getKey(message.getKey());
                redisOperation.del(mqLockKey);
            }
        }
        return action;
    }

}
