/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.base.dict.dao.entities.DictPairEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

@Mapper
public interface DictPairMapper extends BaseMapper<DictPairEntity> {

    public List<DictPairEntity> getDictPairList(@Param("gkeys") List<String> gkeys);

    /**
     * 根据给出的基础数据id集合查询所属的数据组集合
     *
     * @param gkeys
     * @return
     */
    public List<String> getGroupList(@Param("gkeys") Collection<?> gkeys);

}
