/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "UserPwdResetDTO", description = "个人密码更新参数DTO")
@Data
public class UserPwdResetDTO {

    /**
     * 当前密码
     */
    @ApiModelProperty(name = "oldPassword", value = "当前密码")
    @NotNull(message = "密码不能为空")
    private String oldPassword;
    /**
     * 新密码
     */
    @ApiModelProperty(name = "password", value = "新密码")
    @NotNull(message = "密码不能为空")
    private String password;

}
