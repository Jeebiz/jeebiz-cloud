/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.feature.strategy;

import io.hiwepy.cloud.authz.feature.dao.entities.FeatureEntity;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.cloud.authz.feature.enums.FeatureNodeType;
import io.hiwepy.cloud.authz.feature.utils.FeatureNavUtils;
import io.hiwepy.cloud.authz.feature.web.dto.FeatureTreeNode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeatureTreeDataStrategy implements FeatureStrategy {

    @Override
    public FeatureNodeType getNodeType() {
        return FeatureNodeType.TREE;
    }

    @Override
    public List<FeatureTreeNode> handle(List<FeatureEntity> featureList) {
        return FeatureNavUtils.getFeatureTreeList(featureList);
    }

    @Override
    public List<FeatureTreeNode> handle(List<FeatureEntity> featureList, List<FeatureOptEntity> featureOptList) {
        return FeatureNavUtils.getFeatureTreeList(featureList, featureOptList);
    }

}
