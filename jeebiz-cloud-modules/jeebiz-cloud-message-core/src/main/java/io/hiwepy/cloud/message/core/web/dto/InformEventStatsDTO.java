/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.web.dto;

import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "InformEventStatsDTO", description = "消息事件统计DTO")
@Data
public class InformEventStatsDTO {

    /**
     * 消息通知事件行为
     */
    @ApiModelProperty(value = "消息通知事件行为")
    private InformSendChannel channel;
    /**
     * 消息通知事件已发消息总数
     */
    @ApiModelProperty(value = "消息通知事件已发消息总数")
    private Integer total;
    /**
     * 消息通知事件已发消息未读总数
     */
    @ApiModelProperty(value = "消息通知事件已发消息未读总数")
    private Integer unread;

}
