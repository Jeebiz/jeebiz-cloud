/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.org.dao.AuthzOrganizationMapper;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzOrganizationEntity;
import io.hiwepy.cloud.authz.org.service.IAuthzOrganizationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthzOrganizationServiceImpl extends BaseServiceImpl<AuthzOrganizationMapper, AuthzOrganizationEntity> implements IAuthzOrganizationService {

    @Override
    public int getRootCount() {
        return getBaseMapper().getRootCount();
    }

    @Override
    public int getDeptCount(String id) {
        return getBaseMapper().getDeptCount(id);
    }

    @Override
    public List<AuthzOrganizationEntity> getOrgList() {
        return getBaseMapper().getOrgList();
    }

}
