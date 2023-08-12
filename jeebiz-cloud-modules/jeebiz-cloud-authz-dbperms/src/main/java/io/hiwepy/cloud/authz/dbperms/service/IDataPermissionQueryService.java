/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.service;

import io.hiwepy.boot.api.service.IBaseService;
import org.apache.mybatis.dbperms.dto.DataPermission;
import org.apache.mybatis.dbperms.dto.DataPermissionColumn;
import org.apache.mybatis.dbperms.dto.DataSpecialPermission;

import java.util.List;

public interface IDataPermissionQueryService extends IBaseService<DataPermission> {

    /**
     *根据角色ID,用户ID查询数据权限设置
     * @param rid 角色ID
     * @param uid 用户ID
     * @return 变更记录数
     */
    public List<DataPermission> getPermissions(String rid, String uid);

    /**
     *根据角色ID,用户ID查询数据权限设置
     * @param rid 角色ID
     * @param uid 用户ID
     * @return 变更记录数
     */
    public List<DataSpecialPermission> getSpecialPermissions(String rid, String uid);

    /**
     * 根据pid查找对应的数据权限项
     * @param pid  数据权限ID
     * @return
     */
    public List<DataPermissionColumn> getPermissionColumns(String gid);

}
