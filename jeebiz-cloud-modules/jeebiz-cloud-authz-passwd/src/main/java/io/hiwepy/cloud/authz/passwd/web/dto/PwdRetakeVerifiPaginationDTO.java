/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.passwd.web.dto;

import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "PwdRetakeVerifiPaginationDTO", description = "账号核实字段信息分页筛选条件DTO")
@Getter
@Setter
@ToString
public class PwdRetakeVerifiPaginationDTO extends AbstractPaginationDTO {

    /**
     * 账号核实字段启用状态标记，1：启用，0：停用
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "账号核实字段启用状态标记，1：启用，0：停用")
    protected String status;
    /**
     * 关键词搜索
     */
    @ApiModelProperty(name = "keywords", dataType = "String", value = "关键词")
    private String keywords;

}
