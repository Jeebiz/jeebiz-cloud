/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.feature.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.feature.dao.FeatureOptMapper;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.cloud.authz.feature.service.IFeatureOptService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FeatureOptServiceImpl extends BaseServiceImpl<FeatureOptMapper, FeatureOptEntity> implements IFeatureOptService {

    @Override
    public List<FeatureOptEntity> getFeatureOptList() {
        return getBaseMapper().selectList(new LambdaQueryWrapper<FeatureOptEntity>()
                .orderByAsc(FeatureOptEntity::getFeatureId, FeatureOptEntity::getOrderBy));
    }

    @Override
    public List<FeatureOptEntity> getFeatureOptList(Long featureId, boolean visible) {
        return getBaseMapper().selectList(new LambdaQueryWrapper<FeatureOptEntity>()
                .eq(FeatureOptEntity::getFeatureId, featureId)
                .eq(FeatureOptEntity::getVisible, visible ? "1" : "0")
                .orderByAsc(FeatureOptEntity::getFeatureId, FeatureOptEntity::getOrderBy));
    }

    @Override
    public Long getOptCountByName(String name, Long featureId, Long optId) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<FeatureOptEntity>()
                .eq(FeatureOptEntity::getName, name)
                .eq(FeatureOptEntity::getFeatureId, featureId)
                .ne(Objects.nonNull(optId), FeatureOptEntity::getId, optId)
                .last("limit 1"));
    }

}
