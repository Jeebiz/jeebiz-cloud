/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ApiModel(value = "DictGroupPairRenewDTO", description = "基础数据集合传输对象")
@Data
public class DictGroupPairRenewDTO {

    /**
     * 数据字典
     */
    @ApiModelProperty(name = "gkey", dataType = "String", value = "数据字典")
    @NotBlank(message = "基础数据字典必填")
    private String gkey;

    @ApiModelProperty(name = "datas", dataType = "java.util.List<KeyValueRenewDTO>", value = "批量更新的基础数据列表")
    private List<DictPairRenewDTO> datas;

}
