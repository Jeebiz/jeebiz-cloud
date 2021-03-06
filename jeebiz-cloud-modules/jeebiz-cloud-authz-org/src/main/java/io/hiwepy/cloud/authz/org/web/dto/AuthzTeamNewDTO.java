package io.hiwepy.cloud.authz.org.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzTeamNewDTO", description = "新增团队信息参数DTO")
@Getter
@Setter
@ToString
public class AuthzTeamNewDTO implements Serializable {

    /**
     * 机构id编号
     */
    @ApiModelProperty(name = "orgId", required = true, dataType = "String", value = "机构id编号")
    private String orgId;
    /**
     * 部门id编号
     */
    @ApiModelProperty(name = "deptId", required = true, dataType = "String", value = "部门id编号")
    private String deptId;
    /**
     * 团队名称
     */
    @ApiModelProperty(name = "name", required = true, dataType = "String", value = "团队名称")

    private String name;
    /**
     * 团队简介
     */
    @ApiModelProperty(name = "intro", dataType = "String", value = "团队简介")

    private String intro;

}
