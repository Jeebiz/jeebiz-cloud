/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.rbac.dao.entities.AccountStatusModel;
import io.hiwepy.cloud.authz.rbac.dao.entities.UserAccountEntity;

import java.util.List;


/**
 * 用户管理Service接口
 */
public interface IUserAccountService extends IBaseService<UserAccountEntity> {

    /**
     * 查询账号是否已经存在
     * @param account
     * @param userId
     * @return
     */
    Long getCountByAccount(String account, Long userId);

    /**
     * 查询系统所有用户
     *
     * @return
     */
    List<UserAccountEntity> getAccountList();

    /**
     * 当前用户设置密码
     *
     * @param userId      用户id
     * @param oldPassword 旧密码
     * @param password    新密码
     * @return
     */
    boolean resetPwd(Long userId, String oldPassword, String password);

    /**
     * 根据用户ID和密码查询用户可否登录，角色数量等信息
     *
     * @param account  : 用户名
     * @param password : 密码，可不填
     * @return 用户账号状态信息
     */
    AccountStatusModel getAccountStatus(String account, String password);

    /**
     * 根据用户ID和密码查询用户可否登录，角色数量等信息
     *
     * @param account : 用户名
     * @return 用户账号状态信息
     */
    AccountStatusModel getAccountStatusWithoutPwd(String account);

    /**
     * 根据用户ID和密码查询用户可否登录，角色数量等信息
     *
     * @param userId : 用户ID
     * @return 用户账号状态信息
     */
    AccountStatusModel getAccountStatusByUserId(Long userId);

    /***
     * 根据用户id无密码查询用户信息；用于单点登录
     * @param type : 登录方式
     * @param account : 用户名
     * @return 用户登录信息
     */
    UserAccountEntity getAccountByType(String type, String account);

    UserAccountEntity getAccountByName(String channel, String account);
    /**
     * 根据用户表id查询当前系统对应的用户信息
     *
     * @param id 账号表ID
     * @return
     */
    UserAccountEntity getAccountById(Long id);

    /**
     * 根据用户业务id查询当前系统对应的用户信息
     *
     * @param userCode 用户业务id
     * @return
     */
    UserAccountEntity getAccountByUcode(String userCode);

    /**
     * 根据用户id查询当前系统对应的用户信息
     *
     * @param userId 用户ID
     * @return
     */
    UserAccountEntity getAccountByUserId(Long userId);

}
