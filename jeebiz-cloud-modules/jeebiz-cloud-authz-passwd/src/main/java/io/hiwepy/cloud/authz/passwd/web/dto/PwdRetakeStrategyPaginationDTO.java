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

@ApiModel(value = "PwdRetakeStrategyPaginationDTO", description = "密码找回策略分页筛选条件DTO")
@Getter
@Setter
@ToString
public class PwdRetakeStrategyPaginationDTO extends AbstractPaginationDTO {

    /**
     * 是否启用，1：启用(该状态下系统必要有与name对应的策略实现,才能有效)，0：停用
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "是否启用，1：启用(该状态下系统必要有与name对应的策略实现,才能有效)，0：停用")
    protected String status;
    /**
     * 关键词搜索
     */
    @ApiModelProperty(name = "keywords", dataType = "String", value = "关键词")
    private String keywords;

}
