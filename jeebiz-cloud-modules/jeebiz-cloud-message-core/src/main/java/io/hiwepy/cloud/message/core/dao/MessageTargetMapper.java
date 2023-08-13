/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.dao;


import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.message.core.dao.entities.MessageTargetEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageTargetMapper extends BaseMapper<MessageTargetEntity> {

}
