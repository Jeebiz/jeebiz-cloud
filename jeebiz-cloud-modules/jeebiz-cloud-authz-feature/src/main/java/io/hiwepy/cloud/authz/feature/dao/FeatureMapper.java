/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.feature.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FeatureMapper extends BaseMapper<FeatureEntity> {

    /**
     * 查询所有的功能菜单
     *
     * @return
     */
    List<FeatureEntity> getFeatureList();

    /**
     * 查询指定父级菜单下所有可用的菜单
     *
     * @param id
     * @return
     */
    List<FeatureEntity> getChildFeatureList(@Param("id") String id);

    FeatureEntity getFeature(@Param("id") String id);

}
