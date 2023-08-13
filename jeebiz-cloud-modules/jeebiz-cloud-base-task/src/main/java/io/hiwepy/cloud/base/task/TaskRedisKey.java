package io.hiwepy.cloud.base.task;

import org.springframework.data.redis.core.RedisKey;

import java.util.function.BiFunction;

public enum TaskRedisKey {

    /**
     * 任务状态缓存
     */
    TASK_STATUS_KEY("任务状态缓存", (taskId, p2)->{
        return RedisKey.getKeyStr(TaskRedisKeyConstant.TASK_STATUS_KEY, taskId);
    }),

    ;

    private String desc;
    private BiFunction<Object, Object, String> function;

    TaskRedisKey(String desc, BiFunction<Object, Object, String> function) {
        this.desc = desc;
        this.function = function;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 1、获取全名称key
     *
     * @return
     */
    public String getKey() {
        return this.function.apply(null, null);
    }


    /**
     * 1、获取全名称key
     *
     * @param key
     * @return
     */
    public String getKey(Object key) {
        return this.function.apply(key, null);
    }

    /**
     * 1、获取全名称key
     *
     * @param key1
     * @return
     */
    public String getKey(Object key1, Object key2) {
        return this.function.apply(key1, key2);
    }

    public BiFunction<Object, Object, String> getFunction() {
        return function;
    }

}
