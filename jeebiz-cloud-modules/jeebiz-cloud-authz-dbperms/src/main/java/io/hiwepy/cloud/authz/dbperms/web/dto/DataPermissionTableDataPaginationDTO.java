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

@ApiModel(value = "DataPermissionTableDataPaginationDTO", description = "数据权限对象分页查询参数DTO")
@Getter
@Setter
@ToString
public class DataPermissionTableDataPaginationDTO extends AbstractPaginationDTO {

    /**
     * 数据权限对象字段ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "数据权限对象字段ID")
    private String id;

}
