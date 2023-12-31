/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.web.dto;

import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "InformTemplateNewDTO", description = "新建消息通知模板DTO")
@Getter
@Setter
@ToString
public class InformTemplateNewDTO {

    /**
     * 消息通知模板名称
     */
    @ApiModelProperty(required = true, value = "消息模板名称")
    @NotEmpty(message = "消息模板名称不能为空")
    private String name;

    /**
     * 消息通知模板类型
     */
    @ApiModelProperty(required = true, value = "消息通知模板类型")
    @NotNull(message = "消息通知模板类型不能为空")
    private InformSendChannel channel;

    /**
     * 消息通知标题（可能包含变量）
     */
    @ApiModelProperty(value = "消息通知标题（可能包含变量）")
    private String title;

    /**
     * 消息通知签名（短信消息模板需要使用）
     */
    @ApiModelProperty(value = "消息通知签名（短信消息模板需要使用）")
    private String signature;

    /**
     * 消息通知内容（可能包含变量）
     */
    @ApiModelProperty(required = true, value = "消息通知内容（可能包含变量）")
    @NotEmpty(message = "消息通知内容不能为空")
    private String content;

    /**
     * 模板消息通知对应第三方平台内的模板id
     */
    @ApiModelProperty(value = "模板消息通知对应第三方平台内的模板id")
    private String templateId;

    /**
     * 模板消息通知变量载体,JOSN格式的数据
     */
    @ApiModelProperty(value = "模板消息通知变量载体,JOSN格式的数据")
    private String payload;

    /**
     * 消息通知内容中包含的路由跳转信息（JSON格式：[{"name":"路由名称","word":"路由文字","link":"路由跳转地址"}]）
     */
    @ApiModelProperty(value = "消息通知内容中包含的路由跳转信息")
    private List<InformRecordRouteDTO> routes;

    /**
     * 消息通知模板状态：（0:停用、1:启用）
     */
    @ApiModelProperty(value = "消息通知模板状态：（0:停用、1:启用）", allowableValues = "0,1", example = "1")
    private Integer status;

}
