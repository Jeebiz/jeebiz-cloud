package io.hiwepy.cloud.authz.dbperms.web.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.mybatis.dbperms.annotation.Relational;

import java.util.List;

/**
 * 组织架构树形结构信息表DTO
 */
@ApiModel(value = "DataPermissionOrganizDTO", description = "组织架构树形结构数据对象")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionOrganizDTO {

    /**
     * 父级数据权限ID
     */
    @ApiModelProperty(name = "pid", dataType = "String", value = "父级数据权限ID")
    private String pid;
    /**
     * 数据权限ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "数据权限ID")
    private String id;
    /**
     * 数据权限名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "数据权限名称")
    private String name;
    /**
     * 数据权限值
     */
    @ApiModelProperty(name = "value", dataType = "String", value = "数据权限值")
    private String value;
    /**
     * 数据对象（实体表名称）
     */
    @ApiModelProperty(name = "table", dataType = "String", value = "数据对象（实体表名称）")
    private String table;
    /**
     * 受限表字段名称（实体表字段列名称）
     */
    @ApiModelProperty(name = "column", dataType = "String", value = "受限表字段名称（实体表字段列名称）")
    private String column;
    /**
     * 数据权限是否授权(true:已授权|false:未授权)
     */
    @ApiModelProperty(name = "checked", dataType = "Boolean", value = "数据权限是否授权(true:已授权|false:未授权)")
    private boolean checked;
    /**
     * 数据权限项关系 and/or
     */
    @ApiModelProperty(name = "relation", dataType = "String", value = "数据权限项关系 and/or")
    private String relation = Relational.AND.getOperator();
    /**
     * 子数据权限集合
     */
    @ApiModelProperty(name = "children", dataType = "java.util.List", value = "子数据权限集合")
    private List<DataPermissionOrganizDTO> children = Lists.newArrayList();

}
