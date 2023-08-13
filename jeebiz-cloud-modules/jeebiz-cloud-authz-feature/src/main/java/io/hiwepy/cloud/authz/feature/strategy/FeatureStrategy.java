package io.hiwepy.cloud.authz.feature.strategy;

import io.hiwepy.cloud.authz.feature.dao.entities.FeatureEntity;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.cloud.authz.feature.enums.FeatureNodeType;
import io.hiwepy.cloud.authz.feature.web.dto.FeatureTreeNode;

import java.util.List;

/**
 * 数据格式策略
 */
public interface FeatureStrategy {

    /**
     * 获取发送渠道
     *
     * @return
     */
    FeatureNodeType getNodeType();

    List<FeatureTreeNode> handle(List<FeatureEntity> featureList);

    List<FeatureTreeNode> handle(List<FeatureEntity> featureList, List<FeatureOptEntity> featureOptList);

}
