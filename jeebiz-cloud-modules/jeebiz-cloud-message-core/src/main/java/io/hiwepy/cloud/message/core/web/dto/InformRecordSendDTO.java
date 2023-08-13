/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@ApiModel(value = "InformRecordSendDTO", description = "消息通知发送对象")
@Data
public class InformRecordSendDTO {

    /**
     * 消息通知模板ID
     */
    @ApiModelProperty(value = "消息通知模板ID")
    @NotBlank(message = "模板id不能为空")
    private Long templateId;
    /**
     * 消息通知模板参数
     */
    @ApiModelProperty(required = true, value = "消息通知模板参数")
    private Map<String, Object> templateParams;
    /**
     * 消息通知变量载体,JOSN格式的数据
     */
    @ApiModelProperty(value = "消息通知变量载体,JOSN格式的数据")
    private String payload;
    /**
     * 消息通知接收人ID集合（接口只提供发送到人的能力）
     */
    @ApiModelProperty(required = true, value = "消息通知接收人ID集合（接口只提供发送到人的能力）")
    @NotNull(message = "至少需要指定一个消息通知接收人")
    private List<String> toList;
    /**
     * 微应用的id
     */
    private Long agentId;
    /**
     * 企业cropId
     */
    private String corpId;
    /**
     * 企业应用Id/key
     */
    private String appKey;
    /**
     * 机器人ID
     */
    private String robotId;

}

