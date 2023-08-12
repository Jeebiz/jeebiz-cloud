/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzOrganizationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthzOrganizationMapper extends BaseMapper<AuthzOrganizationEntity> {

    int getRootCount();

    int getDeptCount(@Param("id") String id);

    List<AuthzOrganizationEntity> getOrgList();
}
