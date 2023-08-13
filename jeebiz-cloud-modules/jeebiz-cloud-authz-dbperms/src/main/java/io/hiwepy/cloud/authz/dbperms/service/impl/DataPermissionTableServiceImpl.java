package io.hiwepy.cloud.authz.dbperms.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.boot.api.utils.CollectionUtils;
import io.hiwepy.cloud.authz.dbperms.dao.DataPermissionTableMapper;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionTableColumnModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionTableModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionTableService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据权限对象service层接口实现
 */
@Service
public class DataPermissionTableServiceImpl extends BaseServiceImpl<DataPermissionTableMapper, DataPermissionTableModel>
        implements IDataPermissionTableService {

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(DataPermissionTableModel model) {

        // 历史保存的数据权限对象字段
        List<DataPermissionTableColumnModel> oldColumns = getBaseMapper().getTableColumns(model.getId());
        // 此次提交的数据权限对象字段
        List<DataPermissionTableColumnModel> items = model.getColumns();
        if (CollectionUtils.isEmpty(oldColumns) && CollectionUtils.isEmpty(items)) {
            return false;
        }
        // 情况1：没有历史列：新增所有的数据权限对象字段
        else if (CollectionUtils.isEmpty(oldColumns) && CollectionUtils.isNotEmpty(items)) {
            for (DataPermissionTableColumnModel columnModel : items) {
                columnModel.setTid(model.getId());
                getBaseMapper().insertColumn(columnModel);
            }
        }
        // 情况2：有历史列：没有新增的数据权限对象字段
        else if (CollectionUtils.isNotEmpty(oldColumns) && CollectionUtils.isEmpty(items)) {
            for (DataPermissionTableColumnModel columnModel : oldColumns) {
                // 删除数据权限对象字段
                getBaseMapper().deleteColumn(columnModel.getId());
            }
        }
        // 情况3：有历史列、有提交数据
        else {

            // 此次增加的数据权限对象字段
            List<DataPermissionTableColumnModel> incrementItems = items.stream()
                    .filter(column -> !oldColumns.stream().anyMatch(oldColumn -> StringUtils.equalsIgnoreCase(oldColumn.getId(), column.getId())))
                    .collect(Collectors.toList());
            for (DataPermissionTableColumnModel columnModel : incrementItems) {
                columnModel.setTid(model.getId());
                getBaseMapper().insertColumn(columnModel);
            }
            // 此次更新的数据权限对象字段
            List<DataPermissionTableColumnModel> updatedItems = items.stream()
                    .filter(column -> oldColumns.stream().anyMatch(oldColumn -> StringUtils.equalsIgnoreCase(oldColumn.getId(), column.getId())))
                    .collect(Collectors.toList());
            for (DataPermissionTableColumnModel columnModel : updatedItems) {
                columnModel.setTid(model.getId());
                getBaseMapper().updateColumn(columnModel);
            }
            // 此次移除的数据权限对象字段
            List<DataPermissionTableColumnModel> decrementItems = oldColumns.stream()
                    .filter(oldColumn -> !items.stream().anyMatch(column -> StringUtils.equalsIgnoreCase(oldColumn.getId(), column.getId())))
                    .collect(Collectors.toList());
            for (DataPermissionTableColumnModel columnModel : decrementItems) {
                // 删除数据权限对象字段
                getBaseMapper().deleteById(columnModel.getId());
            }
        }
        return SqlHelper.retBool(getBaseMapper().updateById(model));
    }

    @Override
    public List<PairModel> getDataList(DataPermissionTableColumnModel model) {
        DataPermissionTableColumnModel columnModel = getBaseMapper().getColumnModel(model.getId());
        if (null == columnModel) {
            return Lists.newArrayList();
        }
        return getBaseMapper().getDataList(columnModel);
    }

}
