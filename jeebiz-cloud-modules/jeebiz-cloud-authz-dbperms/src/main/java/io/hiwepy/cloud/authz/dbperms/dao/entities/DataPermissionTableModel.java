package io.hiwepy.cloud.authz.dbperms.dao.entities;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.util.List;

@SuppressWarnings("serial")
@Alias("DataPermissionTableModel")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionTableModel extends PaginationEntity<DataPermissionTableModel> {

    /**
     * 数据权限对象ID
     */
    private String id;
    /**
     * 数据权限对象名称
     */
    private String name;
    /**
     * 数据对象（实体表名称）
     */
    private String table;
    /**
     * 数据权限对象可用状态:（0:不可用|1：可用）
     */
    private String status;
    /**
     * 数据权限对象排序
     */
    private int order;
    /**
     * 数据权限对象字段集合
     */
    private List<DataPermissionTableColumnModel> columns = Lists.newArrayList();

}
