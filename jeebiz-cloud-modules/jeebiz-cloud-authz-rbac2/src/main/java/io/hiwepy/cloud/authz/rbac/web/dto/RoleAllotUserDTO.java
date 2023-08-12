/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ApiModel(value = "RoleAllotUserDTO", description = "角色分配用户参数DTO")
@Getter
@Setter
@ToString
public class RoleAllotUserDTO {

    /**
     * 角色id
     */
    @ApiModelProperty(name = "roleId", required = true, dataType = "String", value = "角色id")
    private Long roleId;
    /**
     * 用户id集合
     */
    @ApiModelProperty(name = "userIds", required = true, dataType = "java.util.List<String>", value = "用户id集合")
    private List<Long> userIds;

}
