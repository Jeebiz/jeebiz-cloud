/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "AuthzFaceRepositoryDTO", description = "人脸识别数据分组DTO")
@Getter
@Setter
public class AuthzFaceRepositoryDTO {

    /**
     * 分组ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "分组ID")
    private String id;
    /**
     * 分组名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "分组名称")
    private String name;
    /**
     * 分组状态（0:禁用|1:可用）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "分组状态（0:禁用|1:可用）")
    private String status;
    /**
     * 该分组下已有多少人脸识别数据
     */
    @ApiModelProperty(name = "faces", dataType = "int", value = "该分组下已有多少人脸识别数据")
    private int faces;
    /**
     * 初始化时间
     */
    @ApiModelProperty(name = "time24", dataType = "String", value = "初始化时间")
    private String time24;

}
