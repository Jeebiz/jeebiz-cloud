/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.autoconfigure.security.jwt;

import com.github.hiwepy.jwt.time.JwtTimeProvider;
import org.springframework.data.redis.core.RedisOperationTemplate;

public class JwtTimeRedisProvider implements JwtTimeProvider {

    private RedisOperationTemplate redisOperation;

    public JwtTimeRedisProvider(RedisOperationTemplate redisOperation) {
        super();
        this.redisOperation = redisOperation;
    }

    @Override
    public long now() {
        try {
            return redisOperation.timeNow();
            //return DateUtils.parseDate(nowString, "yyyy-MM-dd HH:mm:ss").getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

}
