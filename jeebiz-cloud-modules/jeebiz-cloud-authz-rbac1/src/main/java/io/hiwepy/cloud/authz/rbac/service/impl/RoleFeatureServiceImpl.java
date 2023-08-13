/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service.impl;

import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.JdbcUtils;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.feature.dao.FeatureMapper;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureEntity;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.cloud.authz.feature.utils.FeatureNavUtils;
import io.hiwepy.cloud.authz.feature.web.dto.FeatureTreeNode;
import io.hiwepy.cloud.authz.rbac.dao.RoleFeatureMapper;
import io.hiwepy.cloud.authz.rbac.service.IRoleFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleFeatureServiceImpl extends BaseServiceImpl<RoleFeatureMapper, FeatureEntity>
        implements IRoleFeatureService {

    @Autowired
    private FeatureMapper featureMapper;
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Override
    public List<FeatureEntity> getFeatures(Long roleId) {
        // 数据库类型
        String dbType = JdbcUtils.getDbType(dataSourceProperties.getUrl(), null);
        // 角色拥有的功能菜单
        List<FeatureEntity> ownFeatures = getBaseMapper().getFeatures(roleId);
        // MySQL数据源，则手动构建树形结构数据
        if (JdbcConstants.MYSQL.equals(dbType)) {
            // 所有的功能菜单
            List<FeatureEntity> features = getFeatureMapper().getFeatureList();
            // 为用户拥有的功能菜单指定父级菜单
            return FeatureNavUtils.getFeatureMergedList(features, ownFeatures);
        }
        return ownFeatures;
    }

    @Override
    public List<FeatureOptEntity> getFeatureOpts(Long roleId) {
        return getBaseMapper().getFeatureOpts(roleId);
    }

    @Override
    public FeatureTreeNode getChildFeatures(Long roleId, Long servId) {
        // 服务对应的菜单
        List<FeatureEntity> featureList = getBaseMapper().getChildFeatures(roleId, servId);
        // 当前登录角色操作权限
        List<FeatureOptEntity> featureOptList = getBaseMapper().getChildFeatureOpts(roleId);
        // 树形结构处理后的数据
        return FeatureNavUtils.getFeatureTree(servId, featureList, featureOptList);
    }

    public FeatureMapper getFeatureMapper() {
        return featureMapper;
    }

}
