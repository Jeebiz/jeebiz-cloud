package io.hiwepy.cloud.authz.dbperms.dao.entities;

import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;
import org.apache.mybatis.dbperms.annotation.Condition;

@SuppressWarnings("serial")
@Alias("DataPermissionItemModel")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionItemModel extends PaginationEntity<DataPermissionItemModel> {

    /**
     * 数据权限ID
     */
    private String pid;
    /**
     * 数据权限项ID
     */
    private String id;
    /**
     * 受限表字段名称（实体表字段列名称）
     */
    private String column;
    /**
     * 受限表字段与限制条件之间的关联条件
     */
    public Condition condition;
    /**
     * 受限表字段名称（实体表字段中文名称）
     */
    private String name;
    /**
     * 受限表字段中文拼音
     */
    private String pinyin;
    /**
     * 受限表字段限制条件
     */
    private String perms;
    /**
     * 受限表字段可用状态:（0:不可用|1：可用）
     */
    private String status;
    /**
     * 受限表字段排序
     */
    private int order;

}
