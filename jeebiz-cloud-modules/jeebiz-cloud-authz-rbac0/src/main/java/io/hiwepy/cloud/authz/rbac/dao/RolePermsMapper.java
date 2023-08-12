/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.rbac.dao.entities.RolePermsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限管理Mapper
 *
 * @author wandl
 */
@Mapper
public interface RolePermsMapper extends BaseMapper<RolePermsEntity> {


}
