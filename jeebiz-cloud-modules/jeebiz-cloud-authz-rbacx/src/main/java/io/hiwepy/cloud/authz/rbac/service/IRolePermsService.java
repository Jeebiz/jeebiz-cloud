/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service;


import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.rbac.dao.entities.RolePermsEntity;

import java.util.List;

/**
 * @author <a href="https://github.com/hiwepy">wandl</a>
 */
public interface IRolePermsService extends IBaseService<RolePermsEntity> {

    /**
     * 查询角色具备的权限标记
     *
     * @param roleId 角色id
     * @return 角色具备的权限标记
     */
    List<String> getPermissions(Long roleId);

    /**
     * 执行角色分配权限逻辑操作
     *
     * @param roleId 角色id
     * @param perms  权限标记
     * @return
     */
    boolean doPerms(Long roleId, List<String> perms);

    /**
     * 取消已分配给指定角色的权限
     *
     * @param roleId 角色id
     * @param perms  权限标记
     * @return
     */
    boolean unPerms(Long roleId, List<String> perms);

    boolean delPerms(Long roleId, List<String> decrements);
}
