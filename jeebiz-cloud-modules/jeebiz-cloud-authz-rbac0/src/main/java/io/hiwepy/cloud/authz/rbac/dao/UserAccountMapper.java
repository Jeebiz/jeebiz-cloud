/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.boot.api.dao.entities.BaseMap;
import io.hiwepy.cloud.authz.rbac.dao.entities.AccountStatusModel;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserAccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户管理DAO
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccountEntity> {

    /**
     * 根据用户ID和密码查询用户可否登录，角色数量等信息
     *
     * @param account  : 用户名
     * @param password : 密码，可不填
     * @return 用户账号状态信息
     */
    AccountStatusModel getAccountStatus(@Param(value = "account") String account,
                                        @Param(value = "password") String password);

    /**
     * 根据用户ID和密码查询用户可否登录，角色数量等信息
     *
     * @param account : 用户名
     * @return 用户账号状态信息
     */
    AccountStatusModel getAccountStatusWithoutPwd(@Param(value = "account") String account);

    /**
     * 根据用户ID和密码查询用户可否登录，角色数量等信息
     *
     * @param userId : 用户ID
     * @return 用户账号状态信息
     */
    AccountStatusModel getAccountStatusByUserId(@Param(value = "userId") Long userId);

    /**
     * 根据用户id和密码查询用户可否登录，角色数量等信息
     *
     * @param id : 用户名
     * @return 用户账号状态信息
     */
    AccountStatusModel getAccountStatusById(@Param(value = "id") Long id);

    /**
     * 查询用户个人信息
     * @param userId
     * @return
     */
    BaseMap getAccountProfile(@Param(value = "userid") Long userId);

}
