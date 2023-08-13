/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.rbac.dao.UserRoleMapper;
import io.hiwepy.cloud.authz.rbac.dao.entities.RoleEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserAccountEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserRoleEntity;
import io.hiwepy.cloud.authz.rbac.service.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRoleEntity>
        implements IUserRoleService {
    @Override
    public Long getCountByRoleId(Long roleId) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<UserRoleEntity>()
                .eq(UserRoleEntity::getRoleId, roleId)
                .last("limit 1"));
    }

    @Override
    public List<String> getRoleKeyListByUserId(Long userId) {
        return getBaseMapper().getRoleKeyListByUserId(userId);
    }

    @Override
    public List<RoleEntity> getRoleListByUserId(Long userId) {
        return getBaseMapper().getRoleListByUserId(userId);
    }

    @Override
    public List<String> getPermissionsByUserId(Long userId) {


        return getBaseMapper().getPermissionsByUserId(userId);
    }

    @Override
    public List<UserRoleEntity> getUserRoleListByUserId(Long userId) {
        List<UserRoleEntity> userRoleList = getBaseMapper().selectList(new LambdaQueryWrapper<UserRoleEntity>()
                .eq(UserRoleEntity::getUserId, userId));
        if (!CollectionUtils.isEmpty(userRoleList)){
            Collections.sort(userRoleList, Comparator.comparing(UserRoleEntity::getPriority));
        }
        return userRoleList;
    }

    @Override
    public boolean removeByUserId(Long userId) {
        return SqlHelper.retBool(getBaseMapper().delete(new LambdaQueryWrapper<UserRoleEntity>().eq(UserRoleEntity::getUserId, userId)));
    }


    @Override
    public Page<RoleEntity> getPagedAllocatedList(UserAccountEntity entity) {

        Page<RoleEntity> page = new Page<RoleEntity>(entity.getPageNo(), entity.getLimit());
        if (!hitool.core.collections.CollectionUtils.isEmpty(entity.getOrders())) {
            for (OrderItem orderBy : entity.getOrders()) {
                page.addOrder(orderBy);
            }
        }

        List<RoleEntity> records = getBaseMapper().getPagedAllocatedList(page, entity);
        page.setRecords(records);

        return page;

    }

    @Override
    public Page<RoleEntity> getPagedUnAllocatedList(UserAccountEntity entity) {

        Page<RoleEntity> page = new Page<RoleEntity>(entity.getPageNo(), entity.getLimit());
        if (!hitool.core.collections.CollectionUtils.isEmpty(entity.getOrders())) {
            for (OrderItem orderBy : entity.getOrders()) {
                page.addOrder(orderBy);
            }
        }

        List<RoleEntity> records = getBaseMapper().getPagedUnAllocatedList(page, entity);
        page.setRecords(records);

        return page;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean doAllot(UserRoleEntity entity) {
        // TODO Auto-generated method stub
        return Boolean.FALSE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean doUnAllot(UserRoleEntity entity) {
        // TODO Auto-generated method stub
        return Boolean.FALSE;
    }


}
