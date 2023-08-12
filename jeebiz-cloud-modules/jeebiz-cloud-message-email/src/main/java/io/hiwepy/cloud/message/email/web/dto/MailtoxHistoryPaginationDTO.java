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

@ApiModel(value = "MailtoxHistoryPaginationDTO", description = "历史邮件数据筛选条件DTO")
@Getter
@Setter
@ToString
public class MailtoxHistoryPaginationDTO extends AbstractPaginationDTO {

    /**
     * 邮件发送请求来源IP地址
     */
    @ApiModelProperty(name = "addr", dataType = "String", value = "邮件发送请求来源IP地址")
    private String addr;
    /**
     * 邮件优先级(1:紧急 3:普通 5:低)
     */
    @ApiModelProperty(name = "priority", dataType = "String", value = "邮件优先级(1:紧急 3:普通 5:低)")
    protected String priority;
    /**
     * 筛选起始时间
     */
    @ApiModelProperty(name = "begintime", dataType = "String", value = "筛选起始时间")
    private String begintime;
    /**
     * 筛选结束时间
     */
    @ApiModelProperty(name = "endtime", dataType = "String", value = "筛选结束时间")
    private String endtime;

}
