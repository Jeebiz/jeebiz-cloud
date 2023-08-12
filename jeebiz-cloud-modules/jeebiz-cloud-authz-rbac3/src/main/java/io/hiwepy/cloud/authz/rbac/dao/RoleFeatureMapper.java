/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureEntity;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureOptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 服务功能菜单
 */
@Mapper
public interface RoleFeatureMapper extends BaseMapper<FeatureEntity> {

    /**
     * 查询指定角色id拥有的功能菜单
     *
     * @param roleId
     * @return
     */
    List<FeatureEntity> getFeatures(@Param(value = "roleId") Long roleId);

    /**
     * 查找功能操作并标记指定角色拥有权限的功能操作选中状态
     *
     * @param roleId
     * @return
     */
    List<FeatureOptEntity> getFeatureOpts(@Param(value = "roleId") Long roleId);

    /**
     * 查询用户指定功能菜单下已经授权的功能菜单
     *
     * @return
     */
    List<FeatureEntity> getChildFeatures(@Param(value = "roleId") Long roleId, @Param("servId") Long servId);

    /**
     * 查找功能操作并标记指定角色拥有权限的功能操作选中状态
     *
     * @param roleId
     * @return
     */
    List<FeatureOptEntity> getChildFeatureOpts(@Param(value = "roleId") Long roleId);

}
