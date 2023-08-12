/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzOrganizationEntity;

import java.util.List;

public interface IAuthzOrganizationService extends IBaseService<AuthzOrganizationEntity> {

    int getRootCount();

    int getDeptCount(String id);

    List<AuthzOrganizationEntity> getOrgList();

}
