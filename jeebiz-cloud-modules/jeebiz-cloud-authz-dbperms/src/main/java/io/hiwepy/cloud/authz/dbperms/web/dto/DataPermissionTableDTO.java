/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.web.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@ApiModel(value = "DataPermissionTableDTO", description = "数据权限对象参数DTO")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionTableDTO {

    /**
     * 数据权限对象ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "数据权限对象ID")
    private String id;
    /**
     * 数据权限对象名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "数据权限对象名称")
    private String name;
    /**
     *数据对象（实体表名称）
     */
    @ApiModelProperty(name = "table", dataType = "String", value = "数数据对象（实体表名称）")
    private String table;
    /**
     * 数据权限对象可用状态:（0:不可用|1：可用）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "可用状态:（0:不可用|1：可用）", allowableValues = "0,1")
    private String status;
    /**
     * 数据权限对象排序
     */
    @ApiModelProperty(name = "order", dataType = "Integer", value = "数据权限对象排序")
    private int order;
    /**
     * 数据权限对象字段集合
     */
    private List<DataPermissionTableColumnDTO> columns = Lists.newArrayList();

}
