/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.facex.dao.AuthzFaceRepositoryMapper;
import io.hiwepy.cloud.authz.facex.dao.entities.AuthzFaceRepositoryEntity;
import io.hiwepy.cloud.authz.facex.service.IAuthzFaceRepositoryService;
import org.springframework.stereotype.Service;

@Service
public class AuthzFaceRepositoryServiceImpl extends BaseServiceImpl<AuthzFaceRepositoryMapper, AuthzFaceRepositoryEntity> implements IAuthzFaceRepositoryService {

}
