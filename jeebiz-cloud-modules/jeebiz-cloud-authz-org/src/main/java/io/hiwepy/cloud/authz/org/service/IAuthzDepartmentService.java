/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzDepartmentEntity;

import java.util.List;

public interface IAuthzDepartmentService extends IBaseService<AuthzDepartmentEntity> {

    /**
     * 根据编码获取记录数
     * @return
     */
    public int getCountByCode(String code, String orgId, String deptId);

    /**
     * 根据名称获取记录数
     * @return
     */
    public int getCountByName(String name, String orgId, String deptId);

    /**
     * 获取部门下成员梳理
     * @param id
     * @return
     */
    public int getStaffCount(String id);

    List<AuthzDepartmentEntity> getEntityListByOrgId(String orgId);
}
