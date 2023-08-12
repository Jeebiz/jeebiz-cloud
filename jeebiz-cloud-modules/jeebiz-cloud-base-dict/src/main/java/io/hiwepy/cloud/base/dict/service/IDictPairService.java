/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.base.dict.dao.entities.DictPairEntity;

import java.util.List;
import java.util.Map;

public interface IDictPairService extends IBaseService<DictPairEntity> {

    public Map<String, List<DictPairEntity>> getGroupPairValues(String[] gkeys);

}
