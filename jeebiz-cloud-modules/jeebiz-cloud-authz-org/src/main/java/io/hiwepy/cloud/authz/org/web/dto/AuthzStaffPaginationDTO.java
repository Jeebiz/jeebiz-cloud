/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.web.dto;

import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzStaffPaginationDTO", description = "员工信息分页查询参数")
@Getter
@Setter
@ToString
public class AuthzStaffPaginationDTO extends AbstractPaginationDTO {

    /**
     * 机构id编号
     */
    @ApiModelProperty(name = "orgId", dataType = "String", value = "机构id编号")
    private String orgId;
    /**
     * 部门id编号
     */
    @ApiModelProperty(name = "deptId", dataType = "String", value = "部门id编号")
    private String deptId;
    /**
     * 团队id编号
     */
    @ApiModelProperty(name = "teamId", dataType = "String", value = "团队id编号")
    private String teamId;
    /**
     * 岗位id编号
     */
    @ApiModelProperty(name = "postId", dataType = "String", value = "岗位id编号")
    private String postId;
    /**
     * 员工名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "员工名称")

    private String name;

}
