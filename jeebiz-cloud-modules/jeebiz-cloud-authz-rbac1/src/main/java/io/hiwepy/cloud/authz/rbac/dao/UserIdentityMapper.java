/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserIdentityEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户身份Mapper
 */
@Mapper
public interface UserIdentityMapper extends BaseMapper<UserIdentityEntity> {

}
