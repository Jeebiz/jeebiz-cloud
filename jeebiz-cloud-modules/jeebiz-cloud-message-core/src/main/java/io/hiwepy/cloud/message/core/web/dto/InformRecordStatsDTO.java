/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.web.dto;

import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "InformRecordStatsDTO", description = "我的消息通知统计DTO")
@Data
public class InformRecordStatsDTO {

    /**
     * 消息通知发送通道
     */
    @ApiModelProperty(name = "channel", value = "消息通知发送通道")
    private InformSendChannel channel;
    /**
     * 该类型通知总数
     */
    @ApiModelProperty(value = "该类型通知总数")
    private String total;
    /**
     * 该类型未读通知总数
     */
    @ApiModelProperty(value = "该类型未读通知总数")
    private String unread;

}
