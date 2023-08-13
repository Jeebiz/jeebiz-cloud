/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzPostEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthzPostMapper extends BaseMapper<AuthzPostEntity> {

}
