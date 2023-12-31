package io.hiwepy.cloud.base.redis;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisOperationTemplate;
import org.springframework.data.redis.core.RedisOperationTemplate;
import redistpl.plus.spring.boot.RedisCachingConfiguration;

@Configuration
@AutoConfigureAfter(RedisCachingConfiguration.class)
@ConditionalOnBean({RedisOperationTemplate.class, ReactiveRedisOperationTemplate.class})
public class JeebizRedisCachingConfiguration {

    @Bean
    public BizRedisTemplate bizRedisTemplate(RedisOperationTemplate redisOperation) {
        return new BizRedisTemplate(redisOperation);
    }

    @Bean
    public BizReactiveRedisTemplate bizReactiveRedisTemplate(ReactiveRedisOperationTemplate reactiveRedisOperation) {
        return new BizReactiveRedisTemplate(reactiveRedisOperation);
    }

}
