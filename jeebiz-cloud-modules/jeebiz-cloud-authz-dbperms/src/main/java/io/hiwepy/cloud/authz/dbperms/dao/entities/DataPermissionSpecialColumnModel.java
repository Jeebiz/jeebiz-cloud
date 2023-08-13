package io.hiwepy.cloud.authz.dbperms.dao.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

@Alias("DataPermissionSpecialColumnModel")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionSpecialColumnModel {

    /**
     * 数据权限专项ID
     */
    private String tid;
    /**
     * 数据权限专项字段ID
     */
    private String id;
    /**
     * 数据权限专项（实体表名称）
     */
    private String table;
    /**
     * 受数据权限专项字段限制的业务数据表字段名称（实体表字段列名称）
     */
    private String name;
    /**
     * 数据权限专项字段标签（页面显示名称）
     */
    private String label;
    /**
     * 受限数据项查询SQL
     */
    private String sql;
    /**
     * 数据权限专项字段可用状态:（0:不可用|1：可用）
     */
    private String status;
    /**
     * 数据权限专项字段排序
     */
    private int order;

}
