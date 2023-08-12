/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.web.dto;

import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "DataPermissionPaginationDTO", description = "数据权限分页查询参数DTO")
@Getter
@Setter
@ToString
public class DataPermissionPaginationDTO extends AbstractPaginationDTO {

    /**
     * 数据权限名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "数据权限名称")
    private String name;
    /**
     *数据对象（实体表名称）
     */
    @ApiModelProperty(name = "table", dataType = "String", value = "数据对象（实体表名称）")
    private String table;

}
