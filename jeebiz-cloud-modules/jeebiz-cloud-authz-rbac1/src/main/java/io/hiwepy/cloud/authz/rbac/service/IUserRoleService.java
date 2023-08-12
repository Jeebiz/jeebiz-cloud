/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.rbac.dao.entities.RoleEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserAccountEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserRoleEntity;

import java.util.List;

/**
 * @author wandl
 */
public interface IUserRoleService extends IBaseService<UserRoleEntity> {

    /**
     * 根据角色ID获取用户数量
     * @param roleId
     * @return
     */
    Long getCountByRoleId(Long roleId);

    /**
     * 查询用户具备的角色标记
     * @param userId
     * @return
     */
    List<String> getRoleKeyListByUserId(Long userId);

    /**
     * 查询用户具备的角色
     * @param userId
     * @return
     */
    List<RoleEntity> getRoleListByUserId(Long userId);

    /**
     * 查询用户具备的权限标记
     * @param userId
     * @return
     */
    List<String> getPermissionsByUserId(Long userId);

    /**
     * 根据用户ID获取用户角色列表
     * @param userId
     * @return
     */
    List<UserRoleEntity> getUserRoleListByUserId(Long userId);

    boolean removeByUserId(Long userId);


    /**
     * 执行用户分配角色逻辑操作
     *
     * @param entity
     * @return
     * @author ： hiwepy（001）
     */
    boolean doAllot(UserRoleEntity entity);

    /**
     * 取消已分配给指定用户的角色
     *
     * @param entity
     * @return
     * @author ： hiwepy（001）
     */
    boolean doUnAllot(UserRoleEntity entity);

    /**
     * 分页查询用户已分配角色信息
     *
     * @param entity
     * @return
     */
    Page<RoleEntity> getPagedAllocatedList(UserAccountEntity entity);

    /**
     * 分页查询用户未分配角色信息
     *
     * @param entity
     * @return
     */
    Page<RoleEntity> getPagedUnAllocatedList(UserAccountEntity entity);
}
