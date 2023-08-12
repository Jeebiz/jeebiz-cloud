package io.hiwepy.cloud.authz.dbperms.dao.entities;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;
import org.apache.mybatis.dbperms.annotation.Relational;

import java.util.List;

@SuppressWarnings("serial")
@Alias("DataPermissionModel")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionModel extends PaginationEntity<DataPermissionModel> {

    /**
     * 数据权限组ID
     */
    private String gid;
    /**
     * 数据权限ID
     */
    private String id;
    /**
     * 数据权限名称
     */
    private String name;
    /**
     * 数据权限类型(1:原生|2:继承|3:复制)
     */
    private String type;
    /**
     * 数据权限简介
     */
    private String intro;
    /**
     * 数据对象（实体表名称）
     */
    private String table;
    /**
     * 数据权限项集合
     */
    private List<DataPermissionItemModel> items = Lists.newArrayList();
    /**
     * 数据权限项关系 and/or
     */
    private Relational relation = Relational.AND;
    /**
     * 数据权限可用状态:（0:不可用|1：可用）
     */
    private String status;
    /**
     * 数据权限排序
     */
    private int order;


}
