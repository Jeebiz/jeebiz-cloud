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

@ApiModel(value = "DataPermissionGroupPaginationDTO", description = "数据权限组分页查询参数DTO")
@Getter
@Setter
@ToString
public class DataPermissionGroupPaginationDTO extends AbstractPaginationDTO {

    /**
     * 数据权限组名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "数据权限组名称")
    private String name;

}
