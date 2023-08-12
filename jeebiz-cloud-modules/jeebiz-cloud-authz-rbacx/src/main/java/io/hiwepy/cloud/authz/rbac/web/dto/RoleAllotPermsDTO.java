/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ApiModel(value = "RoleAllotPermsDTO", description = "角色分配权限参数DTO")
@Getter
@Setter
@ToString
public class RoleAllotPermsDTO {

    /**
     * 角色id
     */
    @ApiModelProperty(name = "roleId", dataType = "String", value = "角色id")
    private Long roleId;
    /**
     * 角色授权的标记集合
     */
    @ApiModelProperty(name = "perms", required = true, dataType = "java.util.List<String>", value = "角色授权的标记集合")
    private List<String> perms = Lists.newArrayList();

}
