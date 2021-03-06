/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.feature.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.feature.dao.FeatureMapper;
import io.hiwepy.cloud.authz.feature.dao.FeatureOptMapper;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureEntity;
import io.hiwepy.cloud.authz.feature.service.IFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
public class FeatureServiceImpl extends BaseServiceImpl<FeatureMapper, FeatureEntity> implements IFeatureService {

    @Autowired
    private FeatureOptMapper featureOptMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        boolean rt = super.removeById(id);
        getFeatureOptMapper().deleteByParent(id);
        return rt;
    }

    @Override
    public FeatureEntity getFeature(String id) {
        return getBaseMapper().getFeature(id);
    }

    @Override
    public List<FeatureEntity> getFeatureList() {
        return getBaseMapper().getFeatureList();
    }

    @Override
    public List<FeatureEntity> getChildFeatureList(String id) {
        return getBaseMapper().getChildFeatureList(id);
    }

    public FeatureOptMapper getFeatureOptMapper() {
        return featureOptMapper;
    }

    public void setFeatureOptMapper(FeatureOptMapper featureOptMapper) {
        this.featureOptMapper = featureOptMapper;
    }

}
