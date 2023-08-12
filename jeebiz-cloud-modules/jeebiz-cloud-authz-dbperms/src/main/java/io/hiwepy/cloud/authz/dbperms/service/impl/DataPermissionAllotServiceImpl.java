package io.hiwepy.cloud.authz.dbperms.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.boot.api.utils.CollectionUtils;
import io.hiwepy.cloud.authz.dbperms.dao.*;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionAllotModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionAllotService;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionService;
import io.hiwepy.cloud.authz.dbperms.setup.DbPermsRedisKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据授权service层接口实现
 */
@Service
public class DataPermissionAllotServiceImpl extends BaseServiceImpl<DataPermissionAllotMapper, DataPermissionAllotModel>
        implements IDataPermissionAllotService {

    @Autowired
    private RedisOperationTemplate redisOperation;
    @Autowired
    private IDataPermissionService permsService;
    @Autowired
    private DataPermissionGroupMapper permsGroupDao;
    @Autowired
    private DataPermissionMapper permsDao;
    @Autowired
    private DataPermissionItemMapper permsItemDao;
    @Autowired
    private DataPermissionSpecialMapper permsSpecialDao;

    @Override
    @Transactional
    public int allot(DataPermissionAllotModel model) {

        // 用户授权
        if (StringUtils.isNotBlank(model.getUid())) {
            // 数据权限组授权
            if (CollectionUtils.isNotEmpty(model.getGroups())) {
                // 查询用户关联的权限组
                List<String> oldGroups = getPermsGroupDao().getUserRelations(model.getUid());
                // 情况1：没有历史列：新增所有的数据权限
                if (CollectionUtils.isEmpty(oldGroups)) {
                    for (String gid : model.getGroups()) {
                        // 新增用户关联的数据权限组
                        getPermsGroupDao().setUserRelation(gid, model.getUid());
                    }
                }
                // 情况2：有历史列、有提交数据
                else {
                    // 此次增加的数据权限组
                    List<String> incrementGroups = model.getGroups().stream()
                            .filter(groupId -> !oldGroups.stream().anyMatch(oldGroupId -> StringUtils.equalsIgnoreCase(oldGroupId, groupId)))
                            .collect(Collectors.toList());
                    for (String gid : incrementGroups) {
                        // 新增用户关联的数据权限组
                        getPermsGroupDao().setUserRelation(gid, model.getUid());
                    }
                    // 此次移除的数据权限组
                    List<String> decrementGroups = oldGroups.stream()
                            .filter(oldGroupId -> !model.getGroups().stream().anyMatch(groupId -> StringUtils.equalsIgnoreCase(oldGroupId, groupId)))
                            .collect(Collectors.toList());
                    for (String gid : decrementGroups) {
                        // 删除用户关联的数据权限组
                        getPermsGroupDao().deleteUserRelation(gid, model.getUid());
                    }
                }
            } else {
                // 删除与用户关联的所有权限组
                getPermsGroupDao().deleteUserRelations(model.getUid());
            }
            // 数据权限专项授权
            if (CollectionUtils.isNotEmpty(model.getSpecials())) {
                // 查询用户关联的数据权限项
                List<String> oldSpecials = getPermsSpecialDao().getUserRelations(model.getUid());
                // 情况1：没有历史列：新增所有的数据权限项
                if (CollectionUtils.isEmpty(oldSpecials)) {
                    for (String tid : model.getSpecials()) {
                        // 新增用户关联的数据权限项
                        getPermsSpecialDao().setUserRelation(tid, model.getUid());
                    }
                }
                // 情况2：有历史列、有提交数据
                else {
                    // 此次增加的数据权限项
                    List<String> incrementSpecials = model.getSpecials().stream()
                            .filter(specialId -> !oldSpecials.stream().anyMatch(oldSpecialId -> StringUtils.equalsIgnoreCase(oldSpecialId, specialId)))
                            .collect(Collectors.toList());
                    for (String tid : incrementSpecials) {
                        // 新增用户关联的数据权限组
                        getPermsSpecialDao().setUserRelation(tid, model.getUid());
                    }
                    // 此次移除的数据权限项
                    List<String> decrementSpecials = oldSpecials.stream()
                            .filter(oldSpecialId -> !model.getSpecials().stream().anyMatch(specialId -> StringUtils.equalsIgnoreCase(oldSpecialId, specialId)))
                            .collect(Collectors.toList());
                    for (String tid : decrementSpecials) {
                        // 删除用户关联的数据权限项
                        getPermsSpecialDao().deleteUserRelation(tid, model.getUid());
                    }
                }
            } else {
                // 删除与用户关联的所有权限项
                getPermsSpecialDao().deleteUserRelations(model.getUid());
            }
            // 自定义数据权限授权
            if (CollectionUtils.isNotEmpty(model.getPerms())) {
                // 查询角色关联的数据权限
                List<String> oldPerms = getPermsDao().getUserRelations(model.getUid());
                // 情况：有历史列、有提交数据
                if (CollectionUtils.isNotEmpty(oldPerms)) {
                    // 此次移除的数据权限
                    List<String> decrementPerms = oldPerms.stream()
                            .filter(oldPermsId -> !model.getPerms().stream().anyMatch(perms -> StringUtils.equalsIgnoreCase(oldPermsId, perms.getId())))
                            .collect(Collectors.toList());
                    for (String pid : decrementPerms) {
                        // 删除用户关联的数据权限
                        getPermsDao().deleteUserRelation(pid, model.getUid());
                    }
                }
                for (DataPermissionModel permModel : model.getPerms()) {
                    // 之前的权限
                    if (StringUtils.isNotBlank(permModel.getId())) {
                        // 更新自定义数据权限
                        getPermsService().updateById(permModel);
                    } else {
                        // 添加自定义数据权限
                        getPermsService().save(permModel);
                        // 添加角色和数据权限关联信息
                        getPermsDao().setUserRelation(model.getUid(), permModel.getId());
                    }
                }
            } else {
                // 删除与用户关联的所有权限项
                getPermsDao().deleteUserRelations(model.getUid());
            }
            String dbPermsKey = DbPermsRedisKey.PERMS_CACHE.getFunction().apply("", "");
            redisOperation.del(dbPermsKey);
            return 1;
        }
        // 角色授权
        else if (StringUtils.isNotBlank(model.getRid())) {
            // 数据权限组授权
            if (CollectionUtils.isNotEmpty(model.getGroups())) {
                // 查询角色关联的权限组
                List<String> oldGroups = getPermsGroupDao().getRoleRelations(model.getRid());
                // 情况1：没有历史列：新增所有的数据权限
                if (CollectionUtils.isEmpty(oldGroups)) {
                    for (String gid : model.getGroups()) {
                        // 新增角色关联的数据权限组
                        getPermsGroupDao().setRoleRelation(gid, model.getRid());
                    }
                }
                // 情况2：有历史列、有提交数据
                else {
                    // 此次增加的数据权限组
                    List<String> incrementGroups = model.getGroups().stream()
                            .filter(groupId -> !oldGroups.stream().anyMatch(oldGroupId -> StringUtils.equalsIgnoreCase(oldGroupId, groupId)))
                            .collect(Collectors.toList());
                    for (String gid : incrementGroups) {
                        // 新增角色关联的数据权限组
                        getPermsGroupDao().setRoleRelation(gid, model.getRid());
                    }
                    // 此次移除的数据权限组
                    List<String> decrementGroups = oldGroups.stream()
                            .filter(oldGroupId -> !model.getGroups().stream().anyMatch(groupId -> StringUtils.equalsIgnoreCase(oldGroupId, groupId)))
                            .collect(Collectors.toList());
                    for (String gid : decrementGroups) {
                        // 删除角色关联的数据权限组
                        getPermsGroupDao().deleteRoleRelation(gid, model.getRid());
                    }
                }
            } else {
                // 删除与角色关联的所有权限组
                getPermsGroupDao().deleteRoleRelations(model.getUid());
            }
            // 数据权限专项授权
            if (CollectionUtils.isNotEmpty(model.getSpecials())) {
                // 查询用户关联的数据权限项
                List<String> oldSpecials = getPermsSpecialDao().getRoleRelations(model.getRid());
                // 情况1：没有历史列：新增所有的数据权限项
                if (CollectionUtils.isEmpty(oldSpecials)) {
                    for (String tid : model.getSpecials()) {
                        // 新增用户关联的数据权限项
                        getPermsSpecialDao().setRoleRelation(tid, model.getRid());
                    }
                }
                // 情况2：有历史列、有提交数据
                else {
                    // 此次增加的数据权限项
                    List<String> incrementSpecials = model.getSpecials().stream()
                            .filter(specialId -> !oldSpecials.stream().anyMatch(oldSpecialId -> StringUtils.equalsIgnoreCase(oldSpecialId, specialId)))
                            .collect(Collectors.toList());
                    for (String tid : incrementSpecials) {
                        // 新增用户关联的数据权限组
                        getPermsSpecialDao().setRoleRelation(tid, model.getRid());
                    }
                    // 此次移除的数据权限项
                    List<String> decrementSpecials = oldSpecials.stream()
                            .filter(oldSpecialId -> !model.getSpecials().stream().anyMatch(specialId -> StringUtils.equalsIgnoreCase(oldSpecialId, specialId)))
                            .collect(Collectors.toList());
                    for (String tid : decrementSpecials) {
                        // 删除用户关联的数据权限项
                        getPermsSpecialDao().deleteRoleRelation(tid, model.getRid());
                    }
                }
            } else {
                // 删除与用户关联的所有权限项
                getPermsSpecialDao().deleteRoleRelations(model.getRid());
            }
            // 自定义数据权限授权
            if (CollectionUtils.isNotEmpty(model.getPerms())) {
                // 查询角色关联的数据权限
                List<String> oldPerms = getPermsDao().getRoleRelations(model.getRid());
                // 情况：有历史列、有提交数据
                if (CollectionUtils.isNotEmpty(oldPerms)) {
                    // 此次移除的数据权限
                    List<String> decrementPerms = oldPerms.stream()
                            .filter(oldPermsId -> !model.getPerms().stream().anyMatch(perms -> StringUtils.equalsIgnoreCase(oldPermsId, perms.getId())))
                            .collect(Collectors.toList());
                    for (String pid : decrementPerms) {
                        // 删除用户关联的数据权限
                        getPermsDao().deleteRoleRelation(pid, model.getRid());
                    }
                }
                for (DataPermissionModel permModel : model.getPerms()) {
                    // 之前的权限
                    if (StringUtils.isNotBlank(permModel.getId())) {
                        // 更新自定义数据权限
                        getPermsService().updateById(permModel);
                    } else {
                        // 添加自定义数据权限
                        getPermsService().save(permModel);
                        // 添加角色和数据权限关联信息
                        getPermsDao().setRoleRelation(model.getRid(), permModel.getId());
                    }
                }
            } else {
                // 删除与用户关联的所有权限项
                getPermsDao().deleteRoleRelations(model.getRid());
            }
            String dbPermsKey = DbPermsRedisKey.PERMS_CACHE.getFunction().apply("", "");
            redisOperation.del(dbPermsKey);
            return 1;
        }
        return 0;
    }

    public IDataPermissionService getPermsService() {
        return permsService;
    }

    public DataPermissionGroupMapper getPermsGroupDao() {
        return permsGroupDao;
    }

    public DataPermissionMapper getPermsDao() {
        return permsDao;
    }

    public DataPermissionItemMapper getPermsItemDao() {
        return permsItemDao;
    }

    public DataPermissionSpecialMapper getPermsSpecialDao() {
        return permsSpecialDao;
    }


}
