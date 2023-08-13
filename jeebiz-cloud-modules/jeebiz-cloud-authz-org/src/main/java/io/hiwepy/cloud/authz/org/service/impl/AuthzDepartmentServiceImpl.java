/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.org.dao.AuthzDepartmentMapper;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzDepartmentEntity;
import io.hiwepy.cloud.authz.org.service.IAuthzDepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthzDepartmentServiceImpl extends BaseServiceImpl<AuthzDepartmentMapper, AuthzDepartmentEntity> implements IAuthzDepartmentService {

    @Override
    public int getCountByCode(String code, String orgId, String deptId) {
        return getBaseMapper().getDeptCountByCode(code, orgId, deptId);
    }

    @Override
    public int getCountByName(String name, String orgId, String deptId) {
        return getBaseMapper().getDeptCountByName(name, orgId, deptId);
    }

    @Override
    public int getStaffCount(String id) {
        return getBaseMapper().getStaffCount(id);
    }

    @Override
    public List<AuthzDepartmentEntity> getEntityListByOrgId(String orgId) {
        return null;
    }

}
