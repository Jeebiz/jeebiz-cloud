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

@ApiModel(value = "DictPairPaginationDTO", description = "基础数据分页查询参数DTO")
@Getter
@Setter
@ToString
public class DictPairPaginationDTO extends AbstractPaginationDTO {

    /**
     * 数据字典
     */
    @ApiModelProperty(name = "gkey", dataType = "String", value = "数据字典")
    private String gkey;
    /**
     * 数据状态：0:不可用|1：可用,可用值:0,1
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "数据状态：0:不可用|1：可用,可用值:0,1")
    private String status;
    /**
     * 基础数据值
     */
    @ApiModelProperty(name = "value", dataType = "String", value = "基础数据值")
    private String value;

}
