/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ApiModel(value = "DataPermissionTableColumnDTO", description = "数据权限对象字段参数DTO")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionTableColumnDTO {

    /**
     * 数据权限对象字段ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "数据权限对象字段ID")
    private String id;
    /**
     * 受数据权限对象字段限制的业务数据表字段名称（实体表字段列名称）
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "受数据权限对象字段限制的业务数据表字段名称（实体表字段列名称）")
    private String name;
    /**
     *  数据权限对象字段标签（页面显示名称）
     */
    @ApiModelProperty(name = "label", dataType = "String", value = "数据权限对象字段标签（页面显示名称）")
    private String label;
    /**
     * 受限数据项查询SQL
     */
    @ApiModelProperty(name = "sql", dataType = "String", value = "受限数据项查询SQL")
    private String sql;
    /**
     * 数据权限对象字段可用状态:（0:不可用|1：可用）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "可用状态:（0:不可用|1：可用）", allowableValues = "0,1")
    private String status;
    /**
     *  数据权限对象字段排序
     */
    @ApiModelProperty(name = "order", dataType = "Integer", value = " 数据权限对象字段排序")
    private int order;

}
