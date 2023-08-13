package io.hiwepy.cloud.oss.core;

import org.springframework.data.redis.core.RedisKey;

import java.util.function.BiFunction;

public enum OssRedisKey {

    /**
     * redis 文件上传黑名单缓存
     */
    UPLOAD_BLACKLIST("文件上传黑名单", (mobile, type) -> {
        return RedisKey.getKeyStr(OssRedisKeyConstant.UPLOAD_BLACK_LIST);
    }),

    /**
     * redis 文件路径与主键ID对照缓存
     */
    UPLOAD_FILE_PATHS("文件路径与ID对照缓存", (p1, p2) -> {
        return RedisKey.getKeyStr(OssRedisKeyConstant.UPLOAD_FILE_PATHS);
    }),
    /**
     * redis 文件ID与主键ID对照缓存
     */
    UPLOAD_FILE_IDS("文件路径与ID对照缓存", (p1, p2) -> {
        return RedisKey.getKeyStr(OssRedisKeyConstant.UPLOAD_FILE_IDS);
    }),
    /**
     * redis 图片缓存
     */
    UPLOAD_IMAGE_DATAS("图片缓存", (fileId, p2) -> {
        return RedisKey.getKeyStr(OssRedisKeyConstant.UPLOAD_IMAGE_DATAS, fileId);
    }),

    ;

    private String desc;
    private BiFunction<String, String, String> function;

    OssRedisKey(String desc, BiFunction<String, String, String> function) {
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
    public String getKey(String key1) {
        return this.function.apply(key1, null);
    }

    /**
     * 1、获取全名称key
     *
     * @param key
     * @return
     */
    public String getKey(String key1, String key2) {
        return this.function.apply(key1, key2);
    }

    public BiFunction<String, String, String> getFunction() {
        return function;
    }

}
