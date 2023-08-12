package io.hiwepy.cloud.base.redis;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.hiwepy.cloud.api.UserProfiles;
import io.hiwepy.cloud.api.dto.UserInfoDTO;
import io.hiwepy.cloud.api.dto.UserInfoSimpleDTO;
import hitool.core.collections.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.redis.core.RedisOperationTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class BizRedisTemplate {

    private RedisOperationTemplate redisOperation;

    public BizRedisTemplate(RedisOperationTemplate redisOperation) {
        this.redisOperation = Objects.requireNonNull(redisOperation);
    }

    // 1、用户信息缓存-------------------------------------------------------------------------------------

    public int getGender(String uid) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid.toString());
        Object cacheValue = redisOperation.hGet(userKey, UserProfiles.GENDER);
        return Objects.isNull(cacheValue) ? 2 : Integer.parseInt(cacheValue.toString());
    }

    public String getUserAppId(String uid) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid.toString());
        Object cacheValue = redisOperation.hGet(userKey, UserProfiles.APP_ID);
        return Objects.isNull(cacheValue) ? "1.0.0" : cacheValue.toString();
    }

    public int getUserVip(String uid) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid);
        Object cacheValue = redisOperation.hGet(userKey, UserProfiles.VIP);
        return Objects.isNull(cacheValue) ? 0 : Integer.parseInt(cacheValue.toString());
    }

    public Object getUserFiled(String uid, String filed) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid);
        return redisOperation.hGet(userKey, filed);
    }

    public <T> T getUserFiled(String uid, String filed, Class<T> clazz) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid);
        return (T) redisOperation.hGet(userKey, filed);
    }

    public <T> T getUserFiled(String uid, String filed, Object def, Class<T> clazz) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid);
        Object obj = redisOperation.hGet(userKey, filed);
        return (T) (Objects.isNull(obj) ? def : obj);
    }

    public boolean setUserFiled(Long uid, String filed, Object value) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid.toString());
        return redisOperation.hSet(userKey, filed, value);
    }

    public boolean setUserFiled(String uid, String filed, Object value) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid);
        return redisOperation.hSet(userKey, filed, value);
    }

    public String getUserFiledString(String uid, String filed) {
        Object cacheValue = this.getUserFiled(uid, filed);
        return Objects.isNull(cacheValue) ? "" : cacheValue.toString();
    }

    public Map<Object, Object> getInfoMap(String uid, Collection<Object> fields) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid);
        Map<Object, Object> cacheMap = redisOperation.hmMultiGet(userKey, fields);
        return cacheMap;
    }

    public List<Map<Object, Object>> getInfoMaps(List<String> uids, Collection<Object> fields) {
        return batchGetUserFields(uids, fields);
    }

    public UserInfoDTO getUserInfoByUserId(Long userId, Function<Long, UserInfoDTO> function) {
        // 从redis中查询用户信息
        String userKey = BizRedisKey.USER_INFO.getKey(userId.toString());
        Map<Object, Object> userCache = redisOperation.hmGet(userKey);
        if (MapUtils.isNotEmpty(userCache)) {
            UserInfoDTO userInfoDTO = JSON.parseObject(JSON.toJSONString(userCache), UserInfoDTO.class);
            return userInfoDTO;
        }
        UserInfoDTO userInfoDTO = function.apply(userId);
        userCache = JSONObject.parseObject(JSON.toJSONString(userInfoDTO), Map.class);
        redisOperation.hmSet(userKey, userCache);
        return userInfoDTO;
    }

    /**
     * 大量用户时候不建议使用此方法，效率会降低
     */
    public List<UserInfoDTO> getUserInfoListByUserIds(Collection<String> userIds) {

        // 1、缓存获取用户信息
        List<Map<Object, Object>> userHashMaps = batchGetUserFields(userIds);
        List<UserInfoDTO> list = userHashMaps.stream()
                .filter(map -> MapUtils.getLong(map, UserProfiles.USER_ID) != null)
                .map(map -> {
                    UserInfoDTO userInfoDTO = JSON.parseObject(JSON.toJSONString(map), UserInfoDTO.class);
                    return userInfoDTO;
                }).collect(Collectors.toList());
        //
        List<Long> collect = list.stream().map(UserInfoDTO::getUserId).collect(Collectors.toList());
        return list;
    }

    /**
     * 大量用户时候不建议使用此方法，效率会降低
     */
    public List<UserInfoSimpleDTO> getUserInfoSimpleListByUserIds(Collection<Long> userIds) {
        // 1、缓存获取用户信息
        List<Map<Object, Object>> objects = batchGetUserFields(userIds);
        List<UserInfoSimpleDTO> list = objects.stream().filter(map -> MapUtils.getLong(map, UserProfiles.USER_ID) != null).map(map -> {
            UserInfoSimpleDTO userInfoDTO = JSON.parseObject(JSON.toJSONString(map), UserInfoSimpleDTO.class);
            return userInfoDTO;
        }).collect(Collectors.toList());
        return list;
    }

    public UserInfoSimpleDTO getUserInfoSimpleByUserId(Long userId, Function<Long, UserInfoSimpleDTO> function) {
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        Map<Object, Object> userCache = redisOperation.hmGet(userKey);
        if (MapUtils.isNotEmpty(userCache)) {
            return JSON.parseObject(JSON.toJSONString(userCache), UserInfoSimpleDTO.class);
        }
        UserInfoSimpleDTO userInfoDTO = function.apply(userId);
        userCache = JSONObject.parseObject(JSON.toJSONString(userInfoDTO), Map.class);
        redisOperation.hmSet(userKey, userCache);
        return userInfoDTO;
    }

    // ===============================batchGet=================================

    public <K> List<Map<Object, Object>> batchGetUserFields(Collection<K> userIds) {
        List<String> uKeys = userIds.stream().map(userId -> BizRedisKey.USER_INFO.getKey(Objects.toString(userId))).collect(Collectors.toList());
        return redisOperation.hmGet(uKeys);
    }

    public <K> Map<Object, Object> batchGetUserFields(K userId, Collection<Object> hashKeys) {
        String userKey = BizRedisKey.USER_INFO.getKey(Objects.toString(userId));
        if (CollectionUtils.isNotEmpty(hashKeys)) {
            return redisOperation.hmMultiGet(userKey, hashKeys);
        }
        return redisOperation.hmGet(userKey);
    }

    public <K> Map<Object, Object> batchGetUserFields(K userId, String... hashKeys) {
        String userKey = BizRedisKey.USER_INFO.getKey(Objects.toString(userId));
        if (ArrayUtils.isNotEmpty(hashKeys)) {
            return redisOperation.hmMultiGet(userKey, Stream.of(hashKeys).collect(Collectors.toList()));
        }
        return redisOperation.hmGet(userKey);
    }

    public <K> List<Map<Object, Object>> batchGetUserFields(Collection<K> userIds, String... hashKeys) {
        List<String> uKeys = userIds.stream().map(userId -> BizRedisKey.USER_INFO.getKey(Objects.toString(userId))).collect(Collectors.toList());
        if (ArrayUtils.isNotEmpty(hashKeys)) {
            return redisOperation.hmMultiGet(uKeys, Stream.of(hashKeys).collect(Collectors.toList()));
        }
        return redisOperation.hmGet(uKeys);
    }

    public <K> List<Map<Object, Object>> batchGetUserFields(Collection<K> userIds, Collection<Object> hashKeys) {
        List<String> uKeys = userIds.stream().map(userId -> BizRedisKey.USER_INFO.getKey(Objects.toString(userId))).collect(Collectors.toList());
        return redisOperation.hmMultiGet(uKeys, hashKeys);
    }

    public <K> Map<String, Map<Object, Object>> batchGetUserFields(Collection<K> userIds, String identityField,
                                                                   Collection<Object> hashKeys) {
        List<String> uKeys = userIds.stream().map(userId -> BizRedisKey.USER_INFO.getKey(Objects.toString(userId))).collect(Collectors.toList());
        return redisOperation.hmMultiGet(uKeys, identityField, hashKeys);
    }

}
