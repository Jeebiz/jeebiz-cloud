/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.cloud.authz.rbac.dao.entities.RoleEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserAccountEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色DAO
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

    /**
     * 获取用户已分配角色id
     *
     * @param userId 用户id
     * @return
     */
    List<String> getRoleKeyListByUserId(@Param(value = "userId") Long userId);

    /**
     * 获取用户已分配角色id
     *
     * @param userId 用户id
     * @return
     */
    List<PairModel> getRolePairList(@Param(value = "userId") Long userId);

    /**
     * 获取用户已分配角色id
     *
     * @param userId 用户id
     * @return
     */
    List<RoleEntity> getRoleListByUserId(@Param(value = "userId") Long userId);

    /**
     * 根据[ROLE_PERMISSION_RElatION]数据查询角色具备的权限信息
     *
     * @param userId 用户id
     * @return 角色具备的权限信息
     */
    List<String> getPermissionsByUserId(@Param(value = "userId") Long userId);

    /**
     * 分页查询用户已分配角色信息
     *
     * @param page
     * @param model
     * @return
     */
    List<RoleEntity> getPagedAllocatedList(Page<RoleEntity> page, UserAccountEntity model);

    /**
     * 分页查询用户未分配角色信息
     *
     * @param page
     * @param model
     * @return
     */
    List<RoleEntity> getPagedUnAllocatedList(Page<RoleEntity> page, UserAccountEntity model);

}
