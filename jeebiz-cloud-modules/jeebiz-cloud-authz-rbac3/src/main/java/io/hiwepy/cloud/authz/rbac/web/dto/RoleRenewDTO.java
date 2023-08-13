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
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "RoleRenewDTO", description = "角色信息更新参数DTO")
@Getter
@Setter
@ToString
public class RoleRenewDTO {

    /**
     * 角色id
     */
    @ApiModelProperty(name = "id", required = true, dataType = "String", value = "角色id")
    @NotNull(message = "角色id必填")
    private Long id;
    /**
     * 角色名称
     */
    @ApiModelProperty(name = "name", required = true, dataType = "String", value = "角色名称")
    @NotBlank(message = "角色名称必填")
    private String name;
    /**
     * 角色简介
     */
    @ApiModelProperty(name = "intro", required = true, dataType = "String", value = "角色简介")
    @NotBlank(message = "角色简介必填")
    private String intro;
    /**
     * 角色状态（0:禁用|1:可用）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "角色状态（0:禁用|1:可用）", allowableValues = "0,1")
    @NotBlank(message = "角色状态必填")
    @AllowableValues(allows = "0,1")
    private String status;
    /**
     * 角色授权的标记集合
     */
    @ApiModelProperty(name = "perms", required = true, dataType = "java.util.List<String>", value = "角色授权的标记集合")
    @NotNull(message = "至少需要一条角色授权标记")
    private List<String> perms;

}
