/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.web.dto;

import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "InformTemplatePaginationDTO", description = "消息通知模板分页查询参数DTO")
@Getter
@Setter
@ToString
public class InformTemplatePaginationDTO extends AbstractPaginationDTO {

    /**
     * 消息通知模板类型
     */
    @ApiModelProperty(value = "消息通知模板类型")
    private InformSendChannel channel;
    /**
     * 模糊搜索关键字
     */
    @ApiModelProperty(name = "keywords", value = "模糊搜索关键字")
    private String keywords;

}
