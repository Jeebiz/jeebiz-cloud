/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hitool.core.collections.CollectionUtils;
import hitool.core.lang3.RandomString;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.rbac.dao.UserAccountMapper;
import io.hiwepy.cloud.authz.rbac.dao.entities.*;
import io.hiwepy.cloud.authz.rbac.service.IRoleService;
import io.hiwepy.cloud.authz.rbac.service.IUserAccountService;
import io.hiwepy.cloud.authz.rbac.service.IUserProfileService;
import io.hiwepy.cloud.authz.rbac.service.IUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccountMapper, UserAccountEntity> implements IUserAccountService {

    protected RandomString randomString = new RandomString(8);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IUserProfileService userProfileService;

    // 加密方式
    private String algorithmName = "MD5";
    // 加密的次数,可以进行多次的加密操作
    private int hashIterations = 10;

    @Override
    public Long getCountByAccount(String account, Long userId) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<UserAccountEntity>()
                .eq(UserAccountEntity::getAccount, account)
                .ne(Objects.nonNull(userId), UserAccountEntity::getUserId, userId)
                .last("limit 1"));
    }

    @Override
    public List<UserAccountEntity> getAccountList() {
        return getBaseMapper().selectList(new QueryWrapper<UserAccountEntity>().orderByAsc("id"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(UserAccountEntity entity) {
        // 盐值，用于和密码混合起来用
        String salt = randomString.nextString();
        entity.setSalt(salt);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        int ct = getBaseMapper().insert(entity);


       // getRoleMapper().setUsers(entity.getRoleId(), Lists.newArrayList(entity.getId()));
        return ct > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<?> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return false;
        }
        //getBaseMapper().batchDeleteRole(idList);
        return getBaseMapper().deleteBatchIds(idList) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        // 1、查询用户信息
        UserAccountEntity entity = getBaseMapper().selectById(id);
        if (Objects.isNull(entity)) {
            return false;
        }
        // 2、删除用户角色关系
        getUserRoleService().removeByUserId(entity.getUserId());
        // 3、删除用户信息
        getUserProfileService().removeByUserId(entity.getUserId());
        // 4、删除用户账号信息
        return super.removeById(id);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPwd(Long userId, String oldPassword, String password) {
        // 查询用户信息
        UserAccountEntity entity = getBaseMapper().selectById(userId);
        String encodePassword = passwordEncoder.encode(password);
        if (!StringUtils.equals(encodePassword, entity.getPassword())) {
            return Boolean.FALSE;
        }
        entity.setPassword(passwordEncoder.encode(password));
        return super.updateById(entity);
    }

    @Override
    public UserAccountEntity getAccountByType(String type, String account) {
        return getBaseMapper().selectOne(new LambdaQueryWrapper<UserAccountEntity>().eq(UserAccountEntity::getType, type).eq(UserAccountEntity::getAccount, account));
    }

    @Override
    public UserAccountEntity getAccountByName(String channel, String account) {
        return null;
    }

    @Override
    public UserAccountEntity getAccountById(Long id) {
        return getBaseMapper().selectById(id);
    }

    @Override
    public UserAccountEntity getAccountByUcode(String userCode) {
        UserProfileEntity profileEntity = getUserProfileService().getOne(new LambdaQueryWrapper<UserProfileEntity>().eq(UserProfileEntity::getUserCode, userCode));
        if (Objects.nonNull(profileEntity)) {
            return getBaseMapper().selectOne(new LambdaQueryWrapper<UserAccountEntity>().eq(UserAccountEntity::getUserId, profileEntity.getUserId()));
        }
        return null;
    }

    @Override
    public UserAccountEntity getAccountByUserId(Long userId) {
        return getBaseMapper().selectOne(new LambdaQueryWrapper<UserAccountEntity>().eq(UserAccountEntity::getUserId, userId));
    }

    @Override
    public AccountStatusModel getAccountStatus(String account, String password) {
        return getBaseMapper().getAccountStatus(account, password);
    }

    @Override
    public AccountStatusModel getAccountStatusWithoutPwd(String account) {
        return getBaseMapper().getAccountStatusWithoutPwd(account);
    }

    @Override
    public AccountStatusModel getAccountStatusByUserId(Long userId) {
        return getBaseMapper().getAccountStatusByUserId(userId);
    }


    public IUserProfileService getUserProfileService() {
        return userProfileService;
    }

    public IUserRoleService getUserRoleService() {
        return userRoleService;
    }

    public IRoleService getRoleService() {
        return roleService;
    }

}
