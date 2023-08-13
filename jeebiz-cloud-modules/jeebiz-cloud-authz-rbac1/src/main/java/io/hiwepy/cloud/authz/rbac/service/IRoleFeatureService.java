/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureEntity;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.cloud.authz.feature.web.dto.FeatureTreeNode;

import java.util.List;

/**
 * 用户已授权功能菜单Service接口
 */
public interface IRoleFeatureService extends IBaseService<FeatureEntity> {

    /**
     * 查询指定角色id拥有的功能菜单
     *
     * @param roleId
     * @return
     */
    List<FeatureEntity> getFeatures(Long roleId);

    /**
     * 查找功能操作并标记指定角色拥有权限的功能操作选中状态
     *
     * @param roleId
     * @return
     */
    List<FeatureOptEntity> getFeatureOpts(Long roleId);

    /**
     * 根据功能菜单id查询子菜单
     *
     * @param roleId
     * @param id
     * @return
     */
    FeatureTreeNode getChildFeatures(Long roleId, Long id);

}

