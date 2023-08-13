/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzPostEntity;

import java.util.List;

public interface IAuthzPostService extends IBaseService<AuthzPostEntity> {

    List<AuthzPostEntity> getEntityListByDeptId(String deptId);
}
