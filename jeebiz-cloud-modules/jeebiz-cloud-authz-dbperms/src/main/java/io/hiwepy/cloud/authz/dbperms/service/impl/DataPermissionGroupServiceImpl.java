package io.hiwepy.cloud.authz.dbperms.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.boot.api.utils.CollectionUtils;
import io.hiwepy.cloud.authz.dbperms.dao.DataPermissionGroupMapper;
import io.hiwepy.cloud.authz.dbperms.dao.DataPermissionItemMapper;
import io.hiwepy.cloud.authz.dbperms.dao.DataPermissionMapper;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionGroupCheckedModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionGroupModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionItemModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionGroupService;
import io.hiwepy.cloud.authz.dbperms.setup.DbPermsRedisKey;
import io.hiwepy.cloud.authz.dbperms.utils.PingYinUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据权限组service层接口实现
 */
@Service
public class DataPermissionGroupServiceImpl extends BaseServiceImpl<DataPermissionGroupMapper, DataPermissionGroupModel>
        implements IDataPermissionGroupService {

    @Autowired
    private RedisOperationTemplate redisOperation;
    @Autowired
    private DataPermissionMapper permsDao;
    @Autowired
    private DataPermissionItemMapper permsItemDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(DataPermissionGroupModel model) {
        // 保存数据权限组
        int rt = getBaseMapper().insert(model);
        if (rt > 0) {
            for (DataPermissionModel permModel : model.getPerms()) {
                // 保存数据权限
                permModel.setGid(model.getId());
                getPermsDao().insert(permModel);
                // 保存数据权限组和数据权限关联
                getBaseMapper().setRelation(model.getId(), permModel.getId());
            }
        }
        String dbPermsKey = DbPermsRedisKey.PERMS_CACHE.getFunction().apply("", "");
        redisOperation.del(dbPermsKey);
        return SqlHelper.retBool(rt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        int rt = getBaseMapper().deleteById(id);
        String dbPermsKey = DbPermsRedisKey.PERMS_CACHE.getFunction().apply("", "");
        redisOperation.del(dbPermsKey);
        return SqlHelper.retBool(rt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeBatchByIds(Collection<?> ids) {
        int rt = getBaseMapper().deleteBatchIds(ids);
        String dbPermsKey = DbPermsRedisKey.PERMS_CACHE.getFunction().apply("", "");
        redisOperation.del(dbPermsKey);
        return SqlHelper.retBool(rt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(DataPermissionGroupModel model) {

        // 历史保存的数据权限
        List<DataPermissionModel> oldPerms = getBaseMapper().getDataPermissions(model.getId());
        // 此次提交的数据权限
        List<DataPermissionModel> perms = model.getPerms();
        if (CollectionUtils.isEmpty(oldPerms) && CollectionUtils.isEmpty(perms)) {
            return false;
        }
        // 情况1：没有历史列：新增所有的数据权限
        else if (CollectionUtils.isEmpty(oldPerms) && CollectionUtils.isNotEmpty(perms)) {
            for (DataPermissionModel permModel : perms) {
                // 保存数据权限
                permModel.setGid(model.getId());
                for (DataPermissionItemModel itemModel : permModel.getItems()) {
                    if (StringUtils.isNotBlank(itemModel.getName())) {
                        itemModel.setPinyin(PingYinUtils.hanziToPinyin(itemModel.getName()));
                    }
                }
                getPermsDao().insert(permModel);
                // 保存数据权限组和数据权限关联
                getBaseMapper().setRelation(model.getId(), permModel.getId());
            }
        }
        // 情况2：有历史列：没有新增的数据权限
        else if (CollectionUtils.isNotEmpty(oldPerms) && CollectionUtils.isEmpty(perms)) {
            for (DataPermissionModel permModel : oldPerms) {
                // 删除数据权限组和数据权限关联
                getBaseMapper().deleteRelation(model.getId(), permModel.getId());
            }
        }
        // 情况3：有历史列、有提交数据
        else {

            // 此次增加的数据权限
            List<DataPermissionModel> incrementPerms = perms.stream()
                    .filter(column -> !oldPerms.stream().anyMatch(oldPerm -> StringUtils.equalsIgnoreCase(oldPerm.getId(), column.getId())))
                    .collect(Collectors.toList());
            for (DataPermissionModel permModel : incrementPerms) {
                // 保存数据权限
                permModel.setGid(model.getId());
                for (DataPermissionItemModel itemModel : permModel.getItems()) {
                    if (StringUtils.isNotBlank(itemModel.getName())) {
                        itemModel.setPinyin(PingYinUtils.hanziToPinyin(itemModel.getName()));
                    }
                }
                getPermsDao().insert(permModel);
                // 保存数据权限组和数据权限关联
                getBaseMapper().setRelation(model.getId(), permModel.getId());
            }
            // 此次更新的数据权限
            List<DataPermissionModel> updatedPerms = perms.stream()
                    .filter(column -> oldPerms.stream().anyMatch(oldPerm -> StringUtils.equalsIgnoreCase(oldPerm.getId(), column.getId())))
                    .collect(Collectors.toList());
            for (DataPermissionModel permModel : updatedPerms) {

                // 更新数据权限
                permModel.setGid(model.getId());
                getPermsDao().updateById(permModel);

                // 历史保存的数据权限项
                List<DataPermissionItemModel> oldItems = getPermsDao().getDataPermissionItems(permModel.getId());
                // 此次提交的数据权限项
                List<DataPermissionItemModel> items = permModel.getItems();
                if (CollectionUtils.isEmpty(oldItems) && CollectionUtils.isEmpty(items)) {
                    return false;
                }
                // 情况1：没有历史列：新增所有的数据权限项
                else if (CollectionUtils.isEmpty(oldItems) && CollectionUtils.isNotEmpty(items)) {
                    for (DataPermissionItemModel itemModel : items) {
                        itemModel.setPid(permModel.getId());
                        if (StringUtils.isNotBlank(itemModel.getName())) {
                            itemModel.setPinyin(PingYinUtils.hanziToPinyin(itemModel.getName()));
                        }
                        getPermsItemDao().insert(itemModel);
                    }
                }
                // 情况2：有历史列：没有新增的数据权限项
                else if (CollectionUtils.isNotEmpty(oldItems) && CollectionUtils.isEmpty(items)) {
                    for (DataPermissionItemModel itemModel : oldItems) {
                        // 删除数据权限项
                        getPermsItemDao().deleteById(itemModel.getId());
                    }
                }
                // 情况3：有历史列、有提交数据
                else {

                    // 此次增加的数据权限项
                    List<DataPermissionItemModel> incrementItems = items.stream()
                            .filter(column -> !oldItems.stream().anyMatch(oldColumn -> StringUtils.equalsIgnoreCase(oldColumn.getId(), column.getId())))
                            .collect(Collectors.toList());
                    for (DataPermissionItemModel itemModel : incrementItems) {
                        itemModel.setPid(permModel.getId());
                        if (StringUtils.isNotBlank(itemModel.getName())) {
                            itemModel.setPinyin(PingYinUtils.hanziToPinyin(itemModel.getName()));
                        }
                        getPermsItemDao().insert(itemModel);
                    }
                    // 此次更新的数据权限项
                    List<DataPermissionItemModel> updatedItems = items.stream()
                            .filter(column -> oldItems.stream().anyMatch(oldColumn -> StringUtils.equalsIgnoreCase(oldColumn.getId(), column.getId())))
                            .collect(Collectors.toList());
                    for (DataPermissionItemModel itemModel : updatedItems) {
                        itemModel.setPid(permModel.getId());
                        if (StringUtils.isNotBlank(itemModel.getName())) {
                            itemModel.setPinyin(PingYinUtils.hanziToPinyin(itemModel.getName()));
                        }
                        getPermsItemDao().updateById(itemModel);
                    }
                    // 此次移除的数据权限项
                    List<DataPermissionItemModel> decrementItems = oldItems.stream()
                            .filter(oldColumn -> !items.stream().anyMatch(column -> StringUtils.equalsIgnoreCase(oldColumn.getId(), column.getId())))
                            .collect(Collectors.toList());
                    for (DataPermissionItemModel itemModel : decrementItems) {
                        // 删除数据权限项
                        getPermsItemDao().deleteById(itemModel.getId());
                    }
                }

            }
            // 此次移除的数据权限
            List<DataPermissionModel> decrementPerms = oldPerms.stream()
                    .filter(oldPerm -> !perms.stream().anyMatch(column -> StringUtils.equalsIgnoreCase(oldPerm.getId(), column.getId())))
                    .collect(Collectors.toList());
            for (DataPermissionModel permModel : decrementPerms) {
                // 删除数据权限组和数据权限关联
                getBaseMapper().deleteRelation(model.getId(), permModel.getId());
            }
        }
        String dbPermsKey = DbPermsRedisKey.PERMS_CACHE.getFunction().apply("", "");
        redisOperation.del(dbPermsKey);
        // 更新数据权限组
        return SqlHelper.retBool(getBaseMapper().updateById(model));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setStatus(Serializable id, Serializable status) {
        DataPermissionGroupModel entity = new DataPermissionGroupModel();
        entity.setStatus(String.valueOf(status));
        entity.setId(String.valueOf(id));
        int rt = getBaseMapper().updateById(entity);
        String dbPermsKey = DbPermsRedisKey.PERMS_CACHE.getFunction().apply("", "");
        redisOperation.del(dbPermsKey);
        return SqlHelper.retBool(rt);
    }

    @Override
    public List<DataPermissionGroupCheckedModel> getGroupUserPairs(String userid) {
        return getBaseMapper().getGroupUserPairs(userid);
    }

    @Override
    public List<DataPermissionGroupCheckedModel> getGroupRolePairs(String roleid) {
        return getBaseMapper().getGroupRolePairs(roleid);
    }

    public DataPermissionMapper getPermsDao() {
        return permsDao;
    }

    public DataPermissionItemMapper getPermsItemDao() {
        return permsItemDao;
    }

}
