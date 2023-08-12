/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzDepartmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthzDepartmentMapper extends BaseMapper<AuthzDepartmentEntity> {

    /**
     * 根据编码获取记录数
     * @return
     */
    public int getDeptCountByCode(@Param("code") String code, @Param("orgId") String orgId, @Param("deptId") String deptId);

    /**
     * 根据名称获取记录数
     * @return
     */
    public int getDeptCountByName(@Param("name") String name, @Param("orgId") String orgId, @Param("deptId") String deptId);

    /**
     * 获取部门下成员梳理
     * @param id
     * @return
     */
    public int getStaffCount(@Param("id") String id);

}
