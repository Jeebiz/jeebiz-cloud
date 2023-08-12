/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.dto;

import io.hiwepy.boot.api.annotation.AllowableValues;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "RoleStatusRenewDTO", description = "角色状态更新参数DTO")
@Getter
@Setter
@ToString
public class RoleStatusRenewDTO {

    /**
     * 角色id
     */
    @ApiModelProperty(name = "id", required = true, dataType = "String", value = "角色id")
    @NotBlank(message = "角色id必填")
    private Long id;
    /**
     * 角色状态（0:禁用|1:可用）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "角色状态（0:禁用|1:可用）", allowableValues = "0,1")
    @NotBlank(message = "角色状态必填")
    @AllowableValues(allows = "0,1")
    private Integer status;

}
