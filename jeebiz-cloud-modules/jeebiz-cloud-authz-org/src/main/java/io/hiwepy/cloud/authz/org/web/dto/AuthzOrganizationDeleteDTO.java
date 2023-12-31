/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "AuthzOrganizationDeleteDTO", description = "机构删除参数DTO")
@SuppressWarnings("serial")
@Data
public class AuthzOrganizationDeleteDTO {

    /**
     * 机构id编号
     */
    @ApiModelProperty(name = "id", required = true, dataType = "String", value = "机构id编号")
    @NotBlank(message = "机构id编号必填")
    private String id;

}
