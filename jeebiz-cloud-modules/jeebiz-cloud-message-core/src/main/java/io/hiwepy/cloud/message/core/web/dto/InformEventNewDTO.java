/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.web.dto;

import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(value = "InformEventNewDTO", description = "新建消息通知事件DTO")
@Data
public class InformEventNewDTO {

    /**
     * 消息通知事件类型
     */
    @ApiModelProperty(value = "消息通知事件类型")
    @NotEmpty(message = "消息通知事件类型不能为空")
    private String type;

    /**
     * 消息通知事件行为
     */
    @ApiModelProperty(value = "消息通知事件行为")
    @NotNull(message = "消息通知事件行为不能为空")
    private InformSendChannel channel;

    /**
     * 消息通知事件关联模板id
     */
    @ApiModelProperty(value = "消息通知事件关联模板id")
    @NotEmpty(message = "消息通知事件关联模板不能为空")
    private String templateId;

    /**
     * 消息通知事件说明
     */
    @ApiModelProperty(value = "消息通知事件说明")
    private String intro;

    /**
     * 消息通知事件状态：（0:停用、1:启用）
     */
    @ApiModelProperty(value = "消息通知事件状态：（0:停用、1:启用）", allowableValues = "0,1", example = "1")
    private Integer status;

}
