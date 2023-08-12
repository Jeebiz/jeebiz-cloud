package io.hiwepy.cloud.authz.dbperms.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionGroupCheckedModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionGroupModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据权限组查询Dao
 */
@Mapper
public interface DataPermissionGroupMapper extends BaseMapper<DataPermissionGroupModel> {


    /**
     * 给数据权限组设置数据权限（添加SYS_DATA_PERMS_GROUP_REL关联数据）
     *
     * @param gid 数据权限组ID
     * @param pid 数据权限ID
     * @return 变更记录数
     */
    public int setRelation(@Param(value = "gid") String gid, @Param(value = "pid") String pid);

    /**
     * 删除数据权限组设置数据权限（添加SYS_DATA_PERMS_GROUP_REL关联数据）
     *
     * @param gid 数据权限组ID
     * @param pid 数据权限ID
     * @return 变更记录数
     */
    public int deleteRelation(@Param(value = "gid") String gid, @Param(value = "pid") String pid);


    /**
     * 给角色设置数据权限组（添加SYS_DATA_PERMS_GROUP_ROLE_REL关联数据）
     *
     * @param gid 数据权限组ID
     * @param rid 角色ID
     * @return 变更记录数
     */
    public int setRoleRelation(@Param(value = "gid") String gid, @Param(value = "rid") String rid);

    /**
     * 删除角色关联的数据权限组（删除SYS_DATA_PERMS_GROUP_ROLE_REL关联数据）
     *
     * @param gid 数据权限组ID
     * @param rid 角色ID
     * @return 变更记录数
     */
    public int deleteRoleRelation(@Param(value = "gid") String gid, @Param(value = "rid") String rid);

    /**
     * 删除角色关联的数据权限组（删除SYS_DATA_PERMS_GROUP_ROLE_REL关联数据）
     *
     * @param rid 角色ID
     * @return 变更记录数
     */
    public int deleteRoleRelations(@Param(value = "rid") String rid);

    /**
     * 查询角色关联的数据权限组（查询SYS_DATA_PERMS_GROUP_ROLE_REL关联数据）
     *
     * @param pid 角色ID
     * @return 变更记录数
     */
    public List<String> getRoleRelations(@Param(value = "rid") String rid);

    /**
     * 给用户设置数据权限组（添加SYS_DATA_PERMS_GROUP_USER_REL关联数据）
     *
     * @param gid 数据权限组ID
     * @param uid 用户ID
     * @return 变更记录数
     */
    public int setUserRelation(@Param(value = "gid") String gid, @Param(value = "uid") String uid);

    /**
     * 删除用户关联的数据权限组（删除SYS_DATA_PERMS_GROUP_USER_REL关联数据）
     *
     * @param gid 数据权限组ID
     * @param uid 用户ID
     * @return 变更记录数
     */
    public int deleteUserRelation(@Param(value = "gid") String gid, @Param(value = "uid") String uid);

    /**
     * 删除用户关联的数据权限组（删除SYS_DATA_PERMS_GROUP_USER_REL关联数据）
     *
     * @param uid 用户ID
     * @return 变更记录数
     */
    public int deleteUserRelations(@Param(value = "uid") String uid);

    /**
     * 查询用户关联的数据权限组（查询SYS_DATA_PERMS_GROUP_USER_REL关联数据）
     *
     * @param uid 用户ID
     * @return 变更记录数
     */
    public List<String> getUserRelations(@Param(value = "uid") String uid);

    /**
     * 根据gid查找对应的数据权限
     *
     * @param gid 数据权限组ID
     * @return
     */
    public List<DataPermissionModel> getDataPermissions(@Param("gid") String gid);

    /**
     * 根据gid查找对应的数据权限
     *
     * @param gid 数据权限组ID
     * @return
     */
    public List<String> getDataPermissionNames(@Param("gid") String gid);

    /**
     * 查询数据权限组并标记已经授权给指定用户的数据权限组为选择状态
     *
     * @param uid 用户ID
     * @return
     */
    public List<DataPermissionGroupCheckedModel> getGroupUserPairs(@Param(value = "uid") String uid);

    /**
     * 查询数据权限组并标记已经授权给指定角色的数据权限组为选择状态
     *
     * @param rid 角色ID
     * @return
     */
    public List<DataPermissionGroupCheckedModel> getGroupRolePairs(@Param(value = "rid") String rid);

}
