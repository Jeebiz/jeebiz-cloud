/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service.impl;


import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.rbac.dao.UserIdentityMapper;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserIdentityEntity;
import io.hiwepy.cloud.authz.rbac.service.IUserIdentityService;
import org.springframework.stereotype.Service;

@Service
public class UserIdentityServiceImpl extends BaseServiceImpl<UserIdentityMapper, UserIdentityEntity>
        implements IUserIdentityService {

}
