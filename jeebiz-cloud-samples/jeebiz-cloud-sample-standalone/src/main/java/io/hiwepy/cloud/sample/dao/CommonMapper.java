/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.sample.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.boot.api.dao.entities.PairModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公共查询Dao
 */
@Mapper
public interface CommonMapper extends BaseMapper<PairModel> {

    /**
     * 数据库当前时间
     * @return
     */
    String getNow();

}
