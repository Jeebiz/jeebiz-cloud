/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "UserNewDTO", description = "新增用户详细信息参数DTO")
@Data
public class UserNewDTO {

    /**
     * 机构id编号
     */
    @ApiModelProperty(name = "orgId", dataType = "String", value = "机构id编号")
    private Long orgId;
    /**
     * 部门id编号
     */
    @ApiModelProperty(name = "deptId", dataType = "String", value = "部门id编号")
    private Long deptId;
    /**
     * 岗位id编号
     */
    @ApiModelProperty(name = "postId", dataType = "String", value = "岗位id编号")
    private Long postId;
    /**
     * 用户名
     */
    @ApiModelProperty(name = "account", required = true, dataType = "String", value = "用户名")
    @NotBlank(message = "用户名必填")
    private String account;
    /**
     * 默认密码
     */
    @ApiModelProperty(name = "password", required = true, dataType = "String", value = "默认密码")
    @NotBlank(message = "默认密码必填")
    private String password;
    /**
     * 用户唯一编号（工号）
     */
    @ApiModelProperty(name = "ucode", required = true, dataType = "String", value = "用户唯一编号（工号）")
    @NotBlank(message = "用户唯一编号（工号）必填")
    private String ucode;
    /**
     * 角色id
     */
    @ApiModelProperty(name = "roleId", required = true, dataType = "String", value = "角色id")
    @NotNull(message = "角色必选")
    private Long roleId;
    /**
     * 用户详情信息
     */
    @ApiModelProperty(name = "profile", required = true, dataType = "UserProfileNewDTO", value = "用户详情信息")
    private UserProfileNewDTO profile;

}
