/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.sessions.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.base.sessions.dao.entities.LastOnlineSessionModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ILastOnlineSessionDao extends BaseMapper<LastOnlineSessionModel> {


}
