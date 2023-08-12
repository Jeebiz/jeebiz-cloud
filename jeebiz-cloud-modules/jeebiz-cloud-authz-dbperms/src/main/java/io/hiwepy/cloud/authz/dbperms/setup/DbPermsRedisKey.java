package io.hiwepy.cloud.authz.dbperms.setup;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.redis.core.RedisKey;

import java.util.function.BiFunction;

public enum DbPermsRedisKey {

    /**
     * redis 短信黑名单缓存
     */
    PERMS_CACHE("短信黑名单", (mobile, type) -> {
        return RedisKey.getKeyStr(DbPermsRedisKeyConstant.PERMS_CACHE_KEY);
    }),
    /**
     * redis 短信每分钟发送缓存
     */
    PERMS_CACHE_HASH("短信每秒发送缓存", (mobile, type) -> {
        String keyStr = MessageFormatter.format(DbPermsRedisKeyConstant.PERMS_CACHE_HASH, mobile, type).getMessage();
        return RedisKey.getKeyStr(keyStr);
    });

    private String desc;
    private BiFunction<String, String, String> function;

    DbPermsRedisKey(String desc, BiFunction<String, String, String> function) {
        this.desc = desc;
        this.function = function;
    }

    public String getDesc() {
        return desc;
    }

    public BiFunction<String, String, String> getFunction() {
        return function;
    }

}
