package io.hiwepy.cloud.authz.dbperms.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.boot.api.utils.CollectionUtils;
import io.hiwepy.cloud.authz.dbperms.dao.DataPermissionItemMapper;
import io.hiwepy.cloud.authz.dbperms.dao.DataPermissionMapper;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionItemModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionService;
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
 * 数据权限service层接口实现
 */
@Service
public class DataPermissionServiceImpl extends BaseServiceImpl<DataPermissionMapper, DataPermissionModel>
        implements IDataPermissionService {

    @Autowired
    private RedisOperationTemplate redisOperation;
    @Autowired
    private DataPermissionItemMapper permsItemDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(DataPermissionModel model) {
        model.setType(StringUtils.defaultString(model.getType(), "1"));
        // 此次提交的数据权限项
        List<DataPermissionItemModel> items = model.getItems();
        // 情况1：新增所有的数据权限项
        if (CollectionUtils.isNotEmpty(items)) {
            for (DataPermissionItemModel itemModel : items) {
                itemModel.setPid(model.getId());
                if (StringUtils.isNotBlank(itemModel.getName())) {
                    itemModel.setPinyin(PingYinUtils.hanziToPinyin(itemModel.getName()));
                }
            }
        }
        // 新增数据权限
        getBaseMapper().insert(model);
        String dbPermsKey = DbPermsRedisKey.PERMS_CACHE.getFunction().apply("", "");
        redisOperation.del(dbPermsKey);
        return SqlHelper.retBool(1);
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
    public boolean updateById(DataPermissionModel model) {

        // 历史保存的数据权限项
        List<DataPermissionItemModel> oldItems = getBaseMapper().getDataPermissionItems(model.getId());
        // 此次提交的数据权限项
        List<DataPermissionItemModel> items = model.getItems();
        if (CollectionUtils.isEmpty(oldItems) && CollectionUtils.isEmpty(items)) {
            return false;
        }
        // 情况1：没有历史列：新增所有的数据权限项
        else if (CollectionUtils.isEmpty(oldItems) && CollectionUtils.isNotEmpty(items)) {
            for (DataPermissionItemModel itemModel : items) {
                itemModel.setPid(model.getId());
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
                itemModel.setPid(model.getId());
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
                itemModel.setPid(model.getId());
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
        // 更新数据权限
        model.setType(StringUtils.defaultString(model.getType(), "1"));
        String dbPermsKey = DbPermsRedisKey.PERMS_CACHE.getFunction().apply("", "");
        redisOperation.del(dbPermsKey);
        return SqlHelper.retBool(getBaseMapper().updateById(model));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int setStatus(String id, String status) {
        DataPermissionModel entity = new DataPermissionModel();
        entity.setStatus(status);
        entity.setId(id);
        int rt = getBaseMapper().updateById(entity);
        String dbPermsKey = DbPermsRedisKey.PERMS_CACHE.getFunction().apply("", "");
        redisOperation.del(dbPermsKey);
        return rt;
    }

    public DataPermissionItemMapper getPermsItemDao() {
        return permsItemDao;
    }
}
