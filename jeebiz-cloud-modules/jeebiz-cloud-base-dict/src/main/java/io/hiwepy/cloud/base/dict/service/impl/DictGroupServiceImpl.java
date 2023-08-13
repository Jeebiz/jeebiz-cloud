/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.base.dict.dao.DictGroupMapper;
import io.hiwepy.cloud.base.dict.dao.entities.DictGroupEntity;
import io.hiwepy.cloud.base.dict.service.IDictGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictGroupServiceImpl extends BaseServiceImpl<DictGroupMapper, DictGroupEntity> implements IDictGroupService {

    @Override
    public List<DictGroupEntity> getKeyGroupList() {
        return getBaseMapper().getKeyGroupList();
    }

}
