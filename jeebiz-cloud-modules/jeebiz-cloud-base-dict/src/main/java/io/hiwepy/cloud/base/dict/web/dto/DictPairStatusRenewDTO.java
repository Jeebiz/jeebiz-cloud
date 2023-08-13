/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "DictPairStatusRenewDTO", description = "基础数据状态更新传输对象")
@Data
public class DictPairStatusRenewDTO {

    /**
     * 基础数据id编号
     */
    @ApiModelProperty(name = "id", required = true, dataType = "String", value = "基础数据id编号")
    @NotBlank(message = "基础数据id必填")
    private String id;
    /**
     * 基础数据状态：0:不可用、1：可用
     */
    @ApiModelProperty(name = "status", required = true, dataType = "String", value = "数据状态：0:不可用|1：可用", allowableValues = "0,1")
    @NotBlank(message = "基础数据状态必填")
    private String status;

}
