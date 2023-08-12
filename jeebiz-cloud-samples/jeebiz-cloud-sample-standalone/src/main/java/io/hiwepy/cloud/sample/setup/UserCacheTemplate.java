package io.hiwepy.cloud.sample.setup;

import com.github.dozermapper.core.Mapper;
import com.tencentcloud.spring.boot.tim.TencentTimTemplate;
import io.hiwepy.cloud.api.UserProfiles;
import io.hiwepy.cloud.authz.rbac.service.IUserProfileService;
import io.hiwepy.cloud.base.redis.BizRedisKey;
import io.hiwepy.cloud.sample.setup.config.CommonProperteis;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * 用户数据缓存操作模板，统一进行缓存的查找、更新、移除等操作
 */
@Slf4j
@Component
@SuppressWarnings("unchecked")
public class UserCacheTemplate {

    @Autowired
    private Mapper beanMapper;
    @Autowired
    private RedisOperationTemplate redisOperationTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private CommonProperteis commonProperteis;
    @Autowired
    private TencentTimTemplate tencentTimTemplate;
    @Autowired
    private IUserProfileService userProfileService;

    // 1、用户信息缓存-------------------------------------------------------------------------------------

    public int getGender(Long userId) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        Object cacheValue = getRedisOperationTemplate().hGet(userKey, UserProfiles.GENDER);
        return Objects.isNull(cacheValue) ? 2 : Integer.parseInt(cacheValue.toString());
    }

    public String getUserAppId(Long userId) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        Object cacheValue = getRedisOperationTemplate().hGet(userKey, UserProfiles.APP_ID);
        return Objects.isNull(cacheValue) ? "1.0.0" : cacheValue.toString();
    }

    public int getUserVip(Long userId) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        Object cacheValue = getRedisOperationTemplate().hGet(userKey, UserProfiles.VIP);
        return Objects.isNull(cacheValue) ? 0 : Integer.parseInt(cacheValue.toString());
    }

    public Object getUserFiled(Long userId, String filed) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        return getRedisOperationTemplate().hGet(userKey, filed);
    }

    public <T> T getUserFiled(Long userId, String filed, Class<T> clazz) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        return (T) getRedisOperationTemplate().hGet(userKey, filed);
    }

    public <T> T getUserFiled(Long userId, String filed, Object def, Class<T> clazz) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        Object obj = getRedisOperationTemplate().hGet(userKey, filed);
        return (T) (Objects.isNull(obj) ? def : obj);
    }

    public boolean setUserFiled(Long userId, String filed, Object value) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        return getRedisOperationTemplate().hSet(userKey, filed, value);
    }

    public String getUserFiledString(Long userId, String filed) {
        Object cacheValue = this.getUserFiled(userId, filed);
        return Objects.isNull(cacheValue) ? "" : cacheValue.toString();
    }

    /**
     * 1.1、重新加载缓存
     *
     * @param userId
     * @return
     */
    public boolean recacheInfo(Long userId) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        // 2、从数据库查询
        Map<Object, Object> infoMap = getUserProfileService().getAccountProfile(userId);
        if (Objects.nonNull(infoMap)) {
            // 2.2、更新Redis缓存
            getRedisOperationTemplate().hmSet(userKey, infoMap);
            log.info("updateUserInfo Cache >> userId:{},UpdateUserInfoMap:{}", userId, infoMap);
            return Boolean.TRUE.booleanValue();
        }
        return Boolean.FALSE.booleanValue();
    }

    public int recacheInfo(Long userId, Function<Map<Object, Object>, Integer> consumer) {
        // 1、查询是否缓存，并更新缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        Map<Object, Object> infoMap = getUserProfileService().getAccountProfile(userId);
        // 1.1、调用回调方法
        int rt = consumer.apply(infoMap);
        if (rt > 0) {
            // 1.2、更新Redis缓存
            getRedisOperationTemplate().hmSet(userKey, infoMap);
            log.info("updateUserInfo Cache >> userId:{},UpdateUserInfoMap:{}", userId, infoMap);
        }
        return rt;
    }

    public Map<Object, Object> getInfoMap(Long userId) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        Map<Object, Object> cacheMap = getRedisOperationTemplate().hmGet(userKey);
        if (MapUtils.isEmpty(cacheMap) || commonProperteis.isRefreshCache()) {
            // 2、从数据库查询
            cacheMap = getUserProfileService().getAccountProfile(userId);
            // 2.1、更新Redis缓存
            getRedisOperationTemplate().hmSet(userKey, cacheMap);
        }
        cacheMap.put(UserProfiles.IM_USER, tencentTimTemplate.genUserSig(userId.toString()));
        return cacheMap;
    }

    public Map<Object, Object> getInfoMapFromDb(Long userId) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        Map<Object, Object> cacheMap = getUserProfileService().getAccountProfile(userId);
        if (Objects.nonNull(cacheMap)) {
            // 2、更新Redis缓存
            getRedisOperationTemplate().hmSet(userKey, cacheMap);
        }
        cacheMap.put(UserProfiles.IM_USER, tencentTimTemplate.genUserSig(userId.toString()));
        return cacheMap;
    }

    public RedisOperationTemplate getRedisOperationTemplate() {
        return redisOperationTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public IUserProfileService getUserProfileService() {
        return userProfileService;
    }

    public Mapper getBeanMapper() {
        return beanMapper;
    }

}
