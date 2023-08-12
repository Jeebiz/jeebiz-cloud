package io.hiwepy.cloud.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MessageDTO {

    @ApiModelProperty("IM发送方id")
    private Long sendUserId;

    @ApiModelProperty("IM接收方id")
    private Long receiveUserId;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("发送间隔时间  (单位：秒)")
    private Integer spaceTime;

    @ApiModelProperty("消息之间间隔时间")
    private Integer messageTime;

    @ApiModelProperty("会话之间间隔时间")
    private Integer contentTime;
}
