package io.hiwepy.cloud.authz.dbperms.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionItemModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据权限配置表dao接口
 */
@Mapper
public interface DataPermissionMapper extends BaseMapper<DataPermissionModel> {

    /**
     * 根据pid查找对应的数据权限项
     *
     * @param pid 数据权限ID
     * @return
     */
    public List<DataPermissionItemModel> getDataPermissionItems(@Param("pid") String gid);

    /**
     * 添加角色和数据权限关联信息（添加SYS_DATA_PERMS_ROLE_REL关联数据）
     *
     * @param rid 角色ID
     * @param pid 数据权限ID
     * @return 变更记录数
     */
    public int setRoleRelation(@Param(value = "rid") String rid, @Param(value = "pid") String pid);

    /**
     * 删除角色和数据权限关联信息（添加SYS_DATA_PERMS_ROLE_REL关联数据）
     *
     * @param rid 角色ID
     * @param pid 数据权限ID
     * @return 变更记录数
     */
    public int deleteRoleRelation(@Param(value = "rid") String rid, @Param(value = "pid") String pid);

    /**
     * 删除角色和数据权限关联信息（添加SYS_DATA_PERMS_ROLE_REL关联数据）
     *
     * @param rid 角色ID
     * @return 变更记录数
     */
    public int deleteRoleRelations(@Param(value = "rid") String rid);

    /**
     * 查询角色关联的数据权限（查询SYS_DATA_PERMS_ROLE_REL关联数据）
     *
     * @param pid 角色ID
     * @return 变更记录数
     */
    public List<String> getRoleRelations(@Param(value = "rid") String rid);

    /**
     * 添加角用户和数据权限关联信息（添加SYS_DATA_PERMS_USER_REL关联数据）
     *
     * @param uid 用户ID
     * @param pid 数据权限ID
     * @return 变更记录数
     */
    public int setUserRelation(@Param(value = "uid") String uid, @Param(value = "pid") String pid);

    /**
     * 删除角用户和数据权限关联信息（添加SYS_DATA_PERMS_USER_REL关联数据）
     *
     * @param uid 用户ID
     * @param pid 数据权限ID
     * @return 变更记录数
     */
    public int deleteUserRelation(@Param(value = "uid") String uid, @Param(value = "pid") String pid);

    /**
     * 删除角用户和数据权限关联信息（添加SYS_DATA_PERMS_USER_REL关联数据）
     *
     * @param uid 用户ID
     * @return 变更记录数
     */
    public int deleteUserRelations(@Param(value = "uid") String uid);

    /**
     * 查询用户关联的数据权限（查询SYS_DATA_PERMS_USER_REL关联数据）
     *
     * @param uid 用户ID
     * @return 变更记录数
     */
    public List<String> getUserRelations(@Param(value = "uid") String uid);

}
