/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.feature.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.feature.dao.entities.FeatureEntity;

import java.util.List;

public interface IFeatureService extends IBaseService<FeatureEntity> {

    /**
     * ��ѯ���еĹ��ܲ˵�
     *
     * @return
     */
    public List<FeatureEntity> getFeatureList();

    /**
     * ��ѯָ�������˵������п��õĲ˵�
     *
     * @param id
     * @return
     */
    public List<FeatureEntity> getChildFeatureList(String id);

    public FeatureEntity getFeature(String id);

}
