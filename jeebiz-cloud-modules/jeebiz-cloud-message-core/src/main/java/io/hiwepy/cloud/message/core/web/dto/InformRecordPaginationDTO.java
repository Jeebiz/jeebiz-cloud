/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.web.dto;

import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "InformRecordPaginationDTO", description = "消息通知分页查询参数DTO")
@Getter
@Setter
@ToString
public class InformRecordPaginationDTO extends AbstractPaginationDTO {

    /**
     * 消息通知阅读状态：（0:未阅读、1:已阅读）
     */
    @ApiModelProperty(value = "消息通知阅读状态：（0:未阅读、1:已阅读）", allowableValues = "0,1")
    private Integer status;
    /**
     * 模糊搜索关键字
     */
    @ApiModelProperty(name = "keywords", dataType = "String", value = "模糊搜索关键字")
    private String keywords;

}
