/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "AuthzOrganizationStatusDTO", description = "机构状态更新参数DTO")
@SuppressWarnings("serial")
@Data
public class AuthzOrganizationStatusDTO {

    /**
     * 机构id编号
     */
    @ApiModelProperty(name = "id", required = true, dataType = "String", value = "机构id编号")
    @NotBlank(message = "机构id编号必填")
    private String id;
    /**
     * 机构状态（0:禁用|1:可用）
     */
    @ApiModelProperty(name = "status", required = true, dataType = "String", value = "机构状态（0:禁用|1:可用）", allowableValues = "1,0")
    @NotBlank(message = "机构状态必填")
    private String status;

}
