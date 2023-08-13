package io.hiwepy.cloud.base.dict.setup.event.listener;

import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.cloud.base.dict.dao.DictGroupMapper;
import io.hiwepy.cloud.base.dict.dao.DictPairMapper;
import io.hiwepy.cloud.base.redis.BizRedisKey;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictPairInitForApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private DictGroupMapper keyGroupMapper;
    @Autowired
    private DictPairMapper keyValueMapper;
    @Autowired
    private RedisOperationTemplate redisOperation;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        List<PairModel> groups = getKeyGroupMapper().getPairList();
        for (PairModel group : groups) {
            String dictRedisKey = BizRedisKey.APP_DICT.getKey(group.getKey());
            List<PairModel> retList = getKeyValueMapper().getPairValues(group.getKey());
            if (CollectionUtils.isNotEmpty(retList)) {
                getRedisOperation().lLeftPush(dictRedisKey, retList);
                //getRedisOperationTemplate().set(Constants.KEY_PREFIX + group.getKey(), retList);
            }
        }

    }

    public DictGroupMapper getKeyGroupMapper() {
        return keyGroupMapper;
    }

    public DictPairMapper getKeyValueMapper() {
        return keyValueMapper;
    }

    public RedisOperationTemplate getRedisOperation() {
        return redisOperation;
    }

}
