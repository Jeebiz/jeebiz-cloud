/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.sample.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.mybatis.dbperms.dto.DataPermission;
import org.apache.mybatis.dbperms.dto.DataPermissionColumn;
import org.apache.mybatis.dbperms.dto.DataSpecialPermission;

import java.util.List;

/**
 * 数据查询Dao
 */
@Mapper
public interface DataPermissionQueryMapper extends BaseMapper<DataPermission> {

    /**
     *根据角色ID,用户ID查询特殊数据权限设置
     * @param rid 角色ID
     * @param uid 用户ID
     * @return 变更记录数
     */
    List<DataSpecialPermission> getSpecialPermissions(@Param(value = "rid") String rid, @Param(value = "uid") String uid);

    /**
     *根据角色ID,用户ID查询数据权限设置
     * @param rid 角色ID
     * @param uid 用户ID
     * @return 变更记录数
     */
    List<DataPermission> getPermissions(@Param(value = "rid") String rid, @Param(value = "uid") String uid);

    /**
     * 根据pid查找对应的数据权限项
     * @param gid  数据权限ID
     * @return
     */
    List<DataPermissionColumn> getPermissionColumns(@Param("pid") String gid);

    /**
     * 根据SQL查询数据（返回结果必须是字符串数集合）
     * @param sql
     * @return
     */
    List<String> getDataList(@Param("sql") String sql);

}
