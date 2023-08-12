/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.dto;

import io.hiwepy.boot.api.dto.AbstractOrderedPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "RoleAllotUserPaginationDTO", description = "角色分配用户分页查询参数DTO")
@Getter
@Setter
@ToString
public class RoleAllotUserPaginationDTO extends AbstractOrderedPaginationDTO {

    /**
     * 角色id
     */
    @ApiModelProperty(name = "id", required = true, dataType = "String", value = "角色id")
    @NotBlank(message = "角色id必填")
    private Long id;
    /**
     * 启用状态
     */
    @ApiModelProperty(name = "status", required = true, dataType = "String", value = "启用状态", allowableValues = "0,1")
    private String status;

}
