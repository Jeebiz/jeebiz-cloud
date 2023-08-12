/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionOrganizModel;

import java.util.List;

/**
 * 数据权限树service层接口
 */
public interface IDataPermissionOrganizService extends IBaseService<DataPermissionOrganizModel> {

    /**
     * 查询角色授权数据权限树
     * @param rid
     * @return
     */
    public List<DataPermissionOrganizModel> getOrganizRoleTree(String rid);

    /**
     * 查询用户授权数据权限树
     * @param uid
     * @return
     */
    public List<DataPermissionOrganizModel> getOrganizUserTree(String uid);

    /**
     * 查询数据权限规则授权数据权限树
     * @param pid
     * @return
     */
    public List<DataPermissionOrganizModel> getOrganizRuleTree(String pid);

}
