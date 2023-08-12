/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.org.dao.AuthzTeamMapper;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzTeamEntity;
import io.hiwepy.cloud.authz.org.service.IAuthzTeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthzTeamServiceImpl extends BaseServiceImpl<AuthzTeamMapper, AuthzTeamEntity> implements IAuthzTeamService {

    @Override
    public int getTeamCountByName(String name, String deptId, String teamId) {
        return getBaseMapper().getTeamCountByName(name, deptId, teamId);
    }

    @Override
    public int getStaffCount(String id) {
        return getBaseMapper().getStaffCount(id);
    }

    @Override
    public List<AuthzTeamEntity> getEntityListByDeptId(String deptId) {
        return null;
    }

}
