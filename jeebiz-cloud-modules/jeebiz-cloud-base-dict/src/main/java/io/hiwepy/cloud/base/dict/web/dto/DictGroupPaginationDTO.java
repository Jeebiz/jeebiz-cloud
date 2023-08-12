/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.web.dto;

import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "DictGroupPaginationDTO", description = "基础数据字典分页查询参数DTO")
@Getter
@Setter
@ToString
public class DictGroupPaginationDTO extends AbstractPaginationDTO {

    /**
     * 数据字典
     */
    @ApiModelProperty(name = "value", dataType = "String", value = "数据字典")
    private String value;

}
