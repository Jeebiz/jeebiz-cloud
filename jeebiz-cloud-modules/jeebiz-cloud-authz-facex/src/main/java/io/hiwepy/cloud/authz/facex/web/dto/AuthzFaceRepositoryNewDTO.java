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

@ApiModel(value = "AuthzFaceRepositoryNewDTO", description = "新增人脸识别数据分组DTO")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class AuthzFaceRepositoryNewDTO {

    @ApiModelProperty(name = "name", required = true, dataType = "String", value = "分组名称")
    @NotBlank(message = "分组名称必填")
    private String name;

}
