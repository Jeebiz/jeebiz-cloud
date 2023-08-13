/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.org.dao.AuthzStaffMapper;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzStaffEntity;
import io.hiwepy.cloud.authz.org.service.IAuthzStaffService;
import org.springframework.stereotype.Service;

@Service
public class AuthzStaffServiceImpl extends BaseServiceImpl<AuthzStaffMapper, AuthzStaffEntity> implements IAuthzStaffService {

}
