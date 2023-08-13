/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import hitool.core.lang3.RandomString;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.rbac.dao.UserProfileMapper;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserIdentityEntity;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserProfileEntity;
import io.hiwepy.cloud.authz.rbac.service.IUserIdentityService;
import io.hiwepy.cloud.authz.rbac.service.IUserProfileService;
import io.hiwepy.cloud.authz.rbac.web.dto.UserResetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class UserProfileServiceImpl extends BaseServiceImpl<UserProfileMapper, UserProfileEntity> implements IUserProfileService {

    protected RandomString randomString = new RandomString(8);

    @Autowired
    private IUserIdentityService userIdentityService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(UserProfileEntity entity) {
        // Uid检查重复
        String code = randomString.nextNumberString();
        while (getBaseMapper().getCountByCode(code, null) != 0) {
            code = randomString.nextNumberString();
        }
        entity.setUserCode(code);
        return super.save(entity);
    }

    @Override
    public Long getCountByCode(String code, Object userId) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<UserProfileEntity>()
                .eq(UserProfileEntity::getUserCode, code)
                .ne(Objects.nonNull(userId), UserProfileEntity::getUserId, userId)
                .last("limit 1"));
    }

    @Override
    public Long getCountByName(String name, Object userId) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<UserProfileEntity>()
                .eq(UserProfileEntity::getNickname, name)
                .ne(Objects.nonNull(userId), UserProfileEntity::getUserId, userId)
                .last("limit 1"));
    }
    @Override
    public Long getCountByPhone(String phone, Long userId) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<UserProfileEntity>()
                .eq(UserProfileEntity::getPhone, phone)
                .ne(Objects.nonNull(userId), UserProfileEntity::getUserId, userId)
                .last("limit 1"));
    }

    @Override
    public Long getCountByEmail(String email, Long userId) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<UserProfileEntity>()
                .eq(UserProfileEntity::getEmail, email)
                .ne(Objects.nonNull(userId), UserProfileEntity::getUserId, userId)
                .last("limit 1"));
    }

    @Override
    public Long getCountByIdcard(String idcard, Long userId) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<UserProfileEntity>()
                .eq(UserProfileEntity::getIdcard, idcard)
                .ne(Objects.nonNull(userId), UserProfileEntity::getUserId, userId)
                .last("limit 1"));
    }

    @Override
    public UserProfileEntity getEntityByIdentity(Long userId, Integer identityId) {
        // 1、根据用户ID + 身份ID 查询用户身份对象
        UserIdentityEntity identityEntity = getUserIdentityService().getOne(new LambdaQueryWrapper<UserIdentityEntity>()
                .eq(UserIdentityEntity::getUserId, userId)
                .eq(UserIdentityEntity::getIdentityId, identityId)
                .last("limit 1"));
        if (Objects.nonNull(identityEntity)){
            return getBaseMapper().selectById(identityEntity.getUserId());
        }
        return getBaseMapper().selectById(identityEntity.getUserId());
    }

    @Override
    public Map<Object, Object> getAccountProfile(Long userId) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetProfile(Long userId, UserResetDTO infoDTO) {
        UserProfileEntity profileModel = new UserProfileEntity()
                .setUserId(userId)
                .setAvatar(infoDTO.getAvatar())
                .setNickname(infoDTO.getNickname());
        return SqlHelper.retBool(getBaseMapper().updateById(profileModel));
    }

    @Override
    public boolean removeByUserId(Long userId) {
        return SqlHelper.retBool(getBaseMapper().delete(new LambdaQueryWrapper<UserProfileEntity>().eq(UserProfileEntity::getUserId, userId)));
    }

    public IUserIdentityService getUserIdentityService() {
        return userIdentityService;
    }
}
