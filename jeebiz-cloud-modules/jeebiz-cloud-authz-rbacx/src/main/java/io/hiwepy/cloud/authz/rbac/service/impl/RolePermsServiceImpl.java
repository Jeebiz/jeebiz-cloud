/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.rbac.dao.RolePermsMapper;
import io.hiwepy.cloud.authz.rbac.dao.entities.RolePermsEntity;
import io.hiwepy.cloud.authz.rbac.service.IRolePermsService;
import io.hiwepy.cloud.authz.rbac.utils.AuthzPermsUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermsServiceImpl extends BaseServiceImpl<RolePermsMapper, RolePermsEntity>
        implements IRolePermsService {

    @Override
    public List<String> getPermissions(Long roleId) {
        // 1、查询已经授权标记
        List<RolePermsEntity> entityList = getBaseMapper().selectList(new LambdaQueryWrapper<RolePermsEntity>()
                .select(RolePermsEntity::getPerms)
                .eq(RolePermsEntity::getRoleId, roleId));
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        // 2、返回授权标记
        return entityList.stream().map(RolePermsEntity::getPerms).distinct().collect(Collectors.toList());
    }

    @Override
    public boolean doPerms(Long roleId, List<String> perms) {
        // 此次提交的授权标记
        perms = AuthzPermsUtils.distinct(perms);
        // 有授权
        if (!CollectionUtils.isEmpty(perms)) {
            // 执行授权
            return this.saveBatch(roleId, perms);
        }
        return Boolean.FALSE;
    }

    protected boolean saveBatch(Long roleId, List<String> perms) {
        // 执行授权
        List<RolePermsEntity> entityList = perms.stream().map(perm -> {
            RolePermsEntity entity = new RolePermsEntity();
            entity.setRoleId(roleId);
            entity.setPerms(perm);
            return entity;
        }).collect(Collectors.toList());
        return super.saveBatch(entityList);
    }

    @Override
    public boolean unPerms(Long roleId, List<String> perms) {
        // 查询已经授权标记
        List<String> oldperms = this.getPermissions(roleId);
        // 此次提交的授权标记
        perms = AuthzPermsUtils.distinct(perms);
        // 之前没有权限
        if (CollectionUtils.isEmpty(oldperms)) {
            return this.saveBatch(roleId, perms);
        }
        // 之前有权限,这里需要筛选出新增的权限和取消的权限
        else {
            // 授权标记增量
            List<String> increments = AuthzPermsUtils.increment(perms, oldperms);
            if (!CollectionUtils.isEmpty(increments)) {
                this.saveBatch(roleId, increments);
            }
            // 授权标记减量
            List<String> decrements = AuthzPermsUtils.decrement(perms, oldperms);
            if (!CollectionUtils.isEmpty(decrements)) {
                this.delPerms(roleId, decrements);
            }
            return Boolean.TRUE;
        }
    }

    @Override
    public boolean delPerms(Long roleId, List<String> perms) {
        return super.remove(new LambdaQueryWrapper<RolePermsEntity>()
                .eq(RolePermsEntity::getRoleId, roleId)
                .ne(RolePermsEntity::getRoleKey, "admin")
                .or(wrapper -> wrapper.in(RolePermsEntity::getPerms, perms)));
    }

}
