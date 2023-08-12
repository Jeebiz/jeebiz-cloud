/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.feature.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureOptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface FeatureOptMapper extends BaseMapper<FeatureOptEntity> {

    FeatureOptEntity getFeatureOpt(@Param(value = "id") String id);

    List<FeatureOptEntity> getFeatureOpts();

    List<FeatureOptEntity> getFeatureOptList(@Param(value = "featureId") String featureId, @Param(value = "visible") String visible);

    int getOptCountByName(@Param(value = "name") String name, @Param(value = "featureId") String featureId, @Param(value = "optId") String optId);

    int deleteByParent(@Param(value = "id") Serializable id);

}
