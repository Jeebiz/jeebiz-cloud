/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.feature.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureOptEntity;

import java.util.List;

public interface IFeatureOptService extends IBaseService<FeatureOptEntity> {

    List<FeatureOptEntity> getFeatureOptList();

    List<FeatureOptEntity> getFeatureOptList(Long featureId, boolean visible);

    Long getOptCountByName(String name, Long featureId, Long optId);

}
