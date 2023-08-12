/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "AuthzFaceRepositoryRenewDTO", description = "人脸识别数据分组更新DTO")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class AuthzFaceRepositoryRenewDTO {

    @ApiModelProperty(name = "id", required = true, dataType = "String", value = "分组ID")
    @NotBlank(message = "分组ID必填")
    private String id;

    @ApiModelProperty(name = "name", required = true, dataType = "String", value = "分组名称")
    @NotBlank(message = "分组名称必填")
    private String name;

}
