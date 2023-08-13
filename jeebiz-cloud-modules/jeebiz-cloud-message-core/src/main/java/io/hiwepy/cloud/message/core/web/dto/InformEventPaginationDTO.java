/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.web.dto;

import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "InformEventPaginationDTO", description = "消息通知事件分页查询参数DTO")
@Data
public class InformEventPaginationDTO extends AbstractPaginationDTO {

    /**
     * 消息通知事件类型
     */
    @ApiModelProperty(value = "消息通知事件类型")
    private String type;
    /**
     * 模糊搜索关键字
     */
    @ApiModelProperty(name = "keywords", value = "模糊搜索关键字")
    private String keywords;

}