/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.base.dict.dao.entities.DictGroupEntity;

import java.util.List;

public interface IDictGroupService extends IBaseService<DictGroupEntity> {

    /**
     * 查询分组数据
     * @return
     */
    public List<DictGroupEntity> getKeyGroupList();

}
