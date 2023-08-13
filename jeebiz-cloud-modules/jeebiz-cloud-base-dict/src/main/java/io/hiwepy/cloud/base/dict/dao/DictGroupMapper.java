/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.base.dict.dao.entities.DictGroupEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictGroupMapper extends BaseMapper<DictGroupEntity> {

    /**
     * 查询分组数据
     * @return
     */
    public List<DictGroupEntity> getKeyGroupList();

}
