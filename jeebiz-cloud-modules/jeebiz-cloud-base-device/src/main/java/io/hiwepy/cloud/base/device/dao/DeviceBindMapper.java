/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.device.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.base.device.dao.entities.DeviceBindEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceBindMapper extends BaseMapper<DeviceBindEntity> {

}