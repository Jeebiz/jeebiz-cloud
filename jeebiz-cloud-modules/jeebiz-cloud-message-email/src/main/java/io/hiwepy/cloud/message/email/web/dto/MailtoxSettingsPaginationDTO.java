/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.email.web.dto;

import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "MailtoxSettingsPaginationDTO", description = "邮件服务设置数据筛选条件DTO")
@Getter
@Setter
@ToString
public class MailtoxSettingsPaginationDTO extends AbstractPaginationDTO {

    /**
     * 邮箱主机配置启用状态标记，1：启用，0：停用
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "邮箱主机配置启用状态标记，1：启用，0：停用", allowableValues = "0,1")
    private String status;
    /**
     * 关键词搜索
     */
    @ApiModelProperty(name = "keywords", dataType = "String", value = "关键词")
    private String keywords;

}
