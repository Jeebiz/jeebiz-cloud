/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.flow.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.oss.flow.dao.entities.FileEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<FileEntity> {

}
