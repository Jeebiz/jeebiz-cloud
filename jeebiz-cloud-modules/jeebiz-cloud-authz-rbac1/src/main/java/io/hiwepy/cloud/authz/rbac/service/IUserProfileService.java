/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserProfileEntity;
import io.hiwepy.cloud.authz.rbac.web.dto.UserResetDTO;


/**
 * 用户详情管理Service接口
 */
public interface IUserProfileService extends IBaseService<UserProfileEntity> {

    /**
     * 根据用户ID + 身份ID 查询用户详情
     * @param userId 用户ID
     * @param identityId 身份ID
     * @return
     */
    UserProfileEntity getEntityByIdentity(Long userId, Integer identityId);

    /**
     * 根据手机号查询相同手机号数量
     *
     * @param phone 手机号码
     * @return
     */
    Long getCountByPhone(String phone, Long userId);

    /**
     * 根据邮箱查询相同手机号数量
     *
     * @param email 手机号码
     * @return
     */
    Long getCountByEmail(String email, Long userId);

    /**
     * 根据idcard查询相同身份证号数量
     *
     * @param idcard 身份证号
     * @return
     */
    Long getCountByIdcard(String idcard, Long userId);

    /**
     * 根据用户ID修改用户详情
     * @param userId
     * @return
     */
    boolean resetProfile(Long userId, UserResetDTO infoDTO);

    /**
     * 根据用户ID删除用户详情
     * @param userId
     * @return
     */
    boolean removeByUserId(Long userId);
}
