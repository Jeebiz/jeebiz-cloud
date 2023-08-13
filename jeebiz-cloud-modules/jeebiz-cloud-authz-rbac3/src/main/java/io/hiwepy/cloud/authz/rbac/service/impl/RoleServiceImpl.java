/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.rbac.dao.RoleMapper;
import io.hiwepy.cloud.authz.rbac.dao.entities.RoleEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserRoleEntity;
import io.hiwepy.cloud.authz.rbac.service.IRolePermsService;
import io.hiwepy.cloud.authz.rbac.service.IRoleService;
import io.hiwepy.cloud.authz.rbac.service.IUserAccountService;
import io.hiwepy.cloud.authz.rbac.service.IUserRoleService;
import io.hiwepy.cloud.authz.rbac.utils.AuthzPermsUtils;
import io.hiwepy.cloud.authz.rbac.web.dto.RoleAllotUserDTO;
import io.hiwepy.cloud.authz.rbac.web.dto.RoleAllotUserPaginationDTO;
import io.hiwepy.cloud.authz.rbac.web.vo.UserAllocatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, RoleEntity> implements IRoleService {

    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRolePermsService rolePermsService;

    @Override
    public Long getCountByName(String name, Object roleId) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<RoleEntity>()
                .eq(RoleEntity::getName, name)
                .ne(Objects.nonNull(roleId), RoleEntity::getId, roleId)
                .last("limit 1"));
    }

    @Override
    public Long getCountByUid(Serializable roleId) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<RoleEntity>()
                .eq(RoleEntity::getId, roleId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(RoleEntity entity) {
        int rt = getBaseMapper().insert(entity);
        // 此次提交的授权标记
        List<String> perms = AuthzPermsUtils.distinct(entity.getPerms());
        // 有授权
        if (!CollectionUtils.isEmpty(perms)) {
            // 执行授权
            getRolePermsService().doPerms(entity.getId(), perms);
        }
        return SqlHelper.retBool(rt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(RoleEntity entity) {
        int rt = getBaseMapper().updateById(entity);
        // 查询已经授权标记
        List<String> oldperms = getRolePermsService().getPermissions(entity.getId());
        // 此次提交的授权标记
        List<String> perms = AuthzPermsUtils.distinct(entity.getPerms());
        // 之前没有权限
        if (CollectionUtils.isEmpty(oldperms)) {
            // 执行授权
            getRolePermsService().doPerms(entity.getId(), perms);
        }
        // 之前有权限,这里需要筛选出新增的权限和取消的权限
        else {
            // 授权标记增量
            List<String> increments = AuthzPermsUtils.increment(perms, oldperms);
            if (!CollectionUtils.isEmpty(increments)) {
                getRolePermsService().doPerms(entity.getId(), increments);
            }
            // 授权标记减量
            List<String> decrements = AuthzPermsUtils.decrement(perms, oldperms);
            if (!CollectionUtils.isEmpty(decrements)) {
                getRolePermsService().delPerms(entity.getId(), decrements);
            }
        }
        return SqlHelper.retBool(rt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        RoleEntity model = getBaseMapper().selectById(id);
        if (model.getUsers() > 0) {
            return Boolean.FALSE;
        }
        int rt = getBaseMapper().deleteById(id);
        // 删除授权
        getRolePermsService().delPerms(Long.parseLong(id.toString()), Lists.newArrayList());
        return SqlHelper.retBool(rt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setStatus(Serializable id, Serializable status) {
        return SqlHelper.retBool(getBaseMapper().updateById(new RoleEntity()
                .setId(Long.parseLong(id.toString()))
                .setStatus(Integer.parseInt(status.toString()))));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean doAllot(RoleAllotUserDTO model) {
        int rt = 0;
        for (Long userId : model.getUserIds()) {
            // 查询角色与用户是否已经有关联
            List<String> oldRoles = getUserRoleService().getRoleKeyListByUserId(userId);
            if (CollectionUtils.isEmpty(oldRoles) || !oldRoles.contains(model.getRoleId())) {
                //rt += getBaseMapper().setUsers(model.getRoleId(), Arrays.asList(userId));
            }
        }
        return SqlHelper.retBool(rt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean doUnAllot(RoleAllotUserDTO model) {
        return getUserRoleService().remove(new QueryWrapper<UserRoleEntity>()
                .eq("role_id", model.getRoleId())
                .in("user_id", model.getUserIds()));
    }

    @Override
    public List<RoleEntity> getRoleList() {
        return getBaseMapper().selectList(new LambdaQueryWrapper<RoleEntity>()
                .eq(RoleEntity::getStatus, 1));
    }

    @Override
    public Page<UserAllocatedVO> getPagedAllocatedList(RoleAllotUserPaginationDTO paginationDTO) {

        Page<UserAllocatedVO> page = new Page<>(paginationDTO.getPageNo(), paginationDTO.getLimit());
        if (!CollectionUtils.isEmpty(paginationDTO.getOrders())) {
            for (OrderItem orderBy : paginationDTO.getOrders()) {
                page.addOrder(orderBy);
            }
        }

        List<UserAllocatedVO> records = getBaseMapper().getPagedAllocatedList(page, paginationDTO);
        page.setRecords(records);

        return page;

    }

    @Override
    public Page<UserAllocatedVO> getPagedUnAllocatedList(RoleAllotUserPaginationDTO paginationDTO) {

        Page<UserAllocatedVO> page = new Page<>(paginationDTO.getPageNo(), paginationDTO.getLimit());
        if (!CollectionUtils.isEmpty(paginationDTO.getOrders())) {
            for (OrderItem orderBy : paginationDTO.getOrders()) {
                page.addOrder(orderBy);
            }
        }

        List<UserAllocatedVO> records = getBaseMapper().getPagedUnAllocatedList(page, paginationDTO);
        page.setRecords(records);

        return page;

    }

    public IUserAccountService getUserAccountService() {
        return userAccountService;
    }

    public IUserRoleService getUserRoleService() {
        return userRoleService;
    }

    public IRolePermsService getRolePermsService() {
        return rolePermsService;
    }


}
