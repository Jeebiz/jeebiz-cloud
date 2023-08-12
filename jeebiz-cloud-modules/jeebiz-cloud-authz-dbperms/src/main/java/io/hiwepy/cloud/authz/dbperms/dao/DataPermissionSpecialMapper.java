package io.hiwepy.cloud.authz.dbperms.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionSpecialCheckedModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionSpecialModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据权限专项查询Dao
 */
@Mapper
public interface DataPermissionSpecialMapper extends BaseMapper<DataPermissionSpecialModel> {

    /**
     * 给角色设置数据权限专项（添加SYS_DATA_PERMS_SPCL_ROLE_REL关联数据）
     *
     * @param tid 数据权限专项ID
     * @param rid 角色ID
     * @return 变更记录数
     */
    public int setRoleRelation(@Param(value = "tid") String tid, @Param(value = "rid") String rid);

    /**
     * 删除角色关联的数据权限专项（删除SYS_DATA_PERMS_SPCL_ROLE_REL关联数据）
     *
     * @param tid 数据权限专项ID
     * @param rid 角色ID
     * @return 变更记录数
     */
    public int deleteRoleRelation(@Param(value = "tid") String tid, @Param(value = "rid") String rid);

    /**
     * 删除角色关联的数据权限专项（删除SYS_DATA_PERMS_SPCL_ROLE_REL关联数据）
     *
     * @param rid 角色ID
     * @return 变更记录数
     */
    public int deleteRoleRelations(@Param(value = "rid") String rid);

    /**
     * 查询角色关联的数据权限专项（查询SYS_DATA_PERMS_SPCL_ROLE_REL关联数据）
     *
     * @param pid 角色ID
     * @return 变更记录数
     */
    public List<String> getRoleRelations(@Param(value = "rid") String rid);

    /**
     * 给用户设置数据权限专项（添加SYS_DATA_PERMS_SPCL_USER_REL关联数据）
     *
     * @param tid 数据权限专项ID
     * @param uid 用户ID
     * @return 变更记录数
     */
    public int setUserRelation(@Param(value = "tid") String tid, @Param(value = "uid") String uid);

    /**
     * 删除用户关联的数据权限专项（删除SYS_DATA_PERMS_SPCL_USER_REL关联数据）
     *
     * @param tid 数据权限专项ID
     * @param uid 用户ID
     * @return 变更记录数
     */
    public int deleteUserRelation(@Param(value = "tid") String tid, @Param(value = "uid") String uid);

    /**
     * 删除用户关联的数据权限专项（删除SYS_DATA_PERMS_SPCL_USER_REL关联数据）
     *
     * @param uid 用户ID
     * @return 变更记录数
     */
    public int deleteUserRelations(@Param(value = "uid") String uid);

    /**
     * 查询用户关联的数据权限专项（查询SYS_DATA_PERMS_SPCL_USER_REL关联数据）
     *
     * @param uid 用户ID
     * @return 变更记录数
     */
    public List<String> getUserRelations(@Param(value = "uid") String uid);

    /**
     * 查询数据权限专项并标记已经授权给指定用户的数据权限专项为选择状态
     *
     * @param uid 用户ID
     * @return
     */
    public List<DataPermissionSpecialCheckedModel> getSpecialUserPairs(@Param(value = "uid") String uid);

    /**
     * 查询数据权限专项并标记已经授权给指定角色的数据权限专项为选择状态
     *
     * @param rid 角色ID
     * @return
     */
    public List<DataPermissionSpecialCheckedModel> getSpecialRolePairs(@Param(value = "rid") String rid);

}
