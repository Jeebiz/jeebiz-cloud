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

@ApiModel(value = "DictRegionPaginationDTO", description = "国家地区编码分页查询参数DTO")
@Getter
@Setter
@ToString
public class DictRegionPaginationDTO extends AbstractPaginationDTO {

    /**
     * 地区编码模糊查询关键字
     */
    @ApiModelProperty(value = "地区编码模糊查询关键字")
    private String keywords;

}
