/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.facex.dao.entities.AuthzFaceRepositoryEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthzFaceRepositoryMapper extends BaseMapper<AuthzFaceRepositoryEntity> {

}
