/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserProfileEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户详情管理DAO
 *
 * @author wandl
 */
@Mapper
public interface UserProfileMapper extends BaseMapper<UserProfileEntity> {

}
