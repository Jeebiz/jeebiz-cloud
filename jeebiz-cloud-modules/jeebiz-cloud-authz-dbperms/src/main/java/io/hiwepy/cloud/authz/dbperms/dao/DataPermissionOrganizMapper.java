/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionOrganizModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据权限树dao接口
 */
@Mapper
public interface DataPermissionOrganizMapper extends BaseMapper<DataPermissionOrganizModel> {

    /**
     * 查询角色授权数据权限树
     * @param rid
     * @return
     */
    public List<DataPermissionOrganizModel> getOrganizRoleTree(@Param("rid") String rid);

    /**
     * 查询用户授权数据权限树
     * @param uid
     * @return
     */
    public List<DataPermissionOrganizModel> getOrganizUserTree(@Param("uid") String uid);

    /**
     * 查询数据权限规则授权数据权限树
     * @param rlid
     * @return
     */
    public List<DataPermissionOrganizModel> getOrganizRuleTree(@Param("rlid") String rlid);

}
