/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.collect.Maps;
import hitool.core.collections.CollectionUtils;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.base.dict.dao.DictPairMapper;
import io.hiwepy.cloud.base.dict.dao.entities.DictPairEntity;
import io.hiwepy.cloud.base.dict.service.IDictPairService;
import io.hiwepy.cloud.base.dict.setup.event.DictPairDeletedEvent;
import io.hiwepy.cloud.base.redis.BizRedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictPairServiceImpl extends BaseServiceImpl<DictPairMapper, DictPairEntity> implements IDictPairService {

    @Autowired
    private RedisOperationTemplate redisOperation;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(DictPairEntity model) {
        // 清理Redis缓存
        String dictRedisKey = BizRedisKey.APP_DICT.getKey(model.getGkey());
        getRedisOperation().del(dictRedisKey);
        return super.save(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<?> list) {
        List<String> groups = getBaseMapper().getGroupList(list);
        for (String gkey : groups) {
            getEventPublisher().publishEvent(new DictPairDeletedEvent(this, gkey));
        }
        return super.removeByIds(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setStatus(Serializable id, Serializable status) {
        List<String> groups = getBaseMapper().getGroupList(Arrays.asList(id));
        for (String gkey : groups) {
            getEventPublisher().publishEvent(new DictPairDeletedEvent(this, gkey));
        }
        DictPairEntity entity = new DictPairEntity();
        entity.setId(String.valueOf(id));
        entity.setStatus(String.valueOf(status));
        return SqlHelper.retBool(getBaseMapper().updateById(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<DictPairEntity> list) {
        List<String> groups = getBaseMapper().getGroupList(list.stream().map(m -> {
            return m.getId();
        }).collect(Collectors.toList()));
        for (String gkey : groups) {
            getEventPublisher().publishEvent(new DictPairDeletedEvent(this, gkey));
        }
        return super.updateBatchById(list);
    }

    @Override
    public Map<String, List<DictPairEntity>> getGroupPairValues(String[] gkeys) {
        List<DictPairEntity> keyValueList = getBaseMapper().getDictPairList(Arrays.asList(gkeys));
        if (CollectionUtils.isEmpty(keyValueList)) {
            return Maps.newHashMap();
        }

        Map<String, List<DictPairEntity>> kvMaps = keyValueList.stream()
                .collect(Collectors.groupingBy(DictPairEntity::getGkey, Collectors.toList()));

        return kvMaps;
    }

    @Override
    public List<PairModel> getPairValues(String gkey) {
        return getBaseMapper().getPairValues(gkey);
    }

    public RedisOperationTemplate getRedisOperation() {
        return redisOperation;
    }
}
