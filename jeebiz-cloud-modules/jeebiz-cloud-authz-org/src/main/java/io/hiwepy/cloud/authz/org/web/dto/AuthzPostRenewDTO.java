package io.hiwepy.cloud.authz.org.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzPostRenewDTO", description = "岗位信息更新参数DTO")
@Getter
@Setter
@ToString
public class AuthzPostRenewDTO implements Serializable {

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
     * 岗位id编号
     */
    @ApiModelProperty(name = "id", required = true, dataType = "String", value = "岗位id编号")
    private String id;
    /**
     * 岗位编码
     */
    @ApiModelProperty(name = "code", required = true, dataType = "String", value = "岗位编码")
    private String code;
    /**
     * 岗位名称
     */
    @ApiModelProperty(name = "name", required = true, dataType = "String", value = "岗位名称")

    private String name;
    /**
     * 岗位简介
     */
    @ApiModelProperty(name = "intro", dataType = "String", value = "岗位简介")

    private String intro;
    /**
     * 岗位状态（0:禁用|1:可用）
     */
    @ApiModelProperty(name = "status", required = true, dataType = "String", value = "岗位状态（0:禁用|1:可用）", allowableValues = "1,0")
    private String status;

}
