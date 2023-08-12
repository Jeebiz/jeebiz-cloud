/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.org.dao.AuthzPostMapper;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzPostEntity;
import io.hiwepy.cloud.authz.org.service.IAuthzPostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthzPostServiceImpl extends BaseServiceImpl<AuthzPostMapper, AuthzPostEntity> implements IAuthzPostService {

    @Override
    public List<AuthzPostEntity> getEntityListByDeptId(String deptId) {
        return null;
    }
}
