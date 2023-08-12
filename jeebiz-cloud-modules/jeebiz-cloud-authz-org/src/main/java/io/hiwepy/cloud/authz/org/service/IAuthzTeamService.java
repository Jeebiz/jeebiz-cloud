/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzTeamEntity;

import java.util.List;

public interface IAuthzTeamService extends IBaseService<AuthzTeamEntity> {

    /**
     * 根据名称获取记录数
     * @return
     */
    public int getTeamCountByName(String name, String deptId, String teamId);

    /**
     * 获取小组下成员梳理
     * @param id
     * @return
     */
    public int getStaffCount(String id);

    List<AuthzTeamEntity> getEntityListByDeptId(String deptId);
}
