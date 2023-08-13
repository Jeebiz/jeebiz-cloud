package io.hiwepy.cloud.authz.dbperms.dao.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

@Alias("DataPermissionTableColumnModel")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionTableColumnModel {

    /**
     * 数据权限对象ID
     */
    private String tid;
    /**
     * 数据权限对象字段ID
     */
    private String id;
    /**
     * 受数据权限对象字段限制的业务数据表字段名称（实体表字段列名称）
     */
    private String name;
    /**
     * 数据权限对象字段标签（页面显示名称）
     */
    private String label;
    /**
     * 受限数据项查询SQL
     */
    private String sql;
    /**
     * 数据权限对象字段可用状态:（0:不可用|1：可用）
     */
    private String status;
    /**
     * 数据权限对象字段排序
     */
    private int order;

}
