package io.hiwepy.cloud.authz.dbperms.dao.entities;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;
import org.apache.mybatis.dbperms.annotation.Relational;

import java.util.List;

@Alias("DataPermissionOrganizModel")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionOrganizModel {

    /**
     * 父级数据权限ID
     */
    private String pid;
    /**
     * 数据权限ID
     */
    private String id;
    /**
     * 数据权限名称
     */
    private String name;
    /**
     * 数据权限值
     */
    private String value;
    /**
     * 数据对象（实体表名称）
     */
    private String table;
    /**
     * 受限表字段名称（实体表字段列名称）
     */
    private String column;
    /**
     * 数据权限是否授权(true:已授权|false:未授权)
     */
    private boolean checked;
    /**
     * 数据权限项关系 and/or
     */
    private String relation = Relational.AND.getOperator();
    /**
     * 子数据权限集合
     */
    private List<DataPermissionOrganizModel> children = Lists.newArrayList();

}
