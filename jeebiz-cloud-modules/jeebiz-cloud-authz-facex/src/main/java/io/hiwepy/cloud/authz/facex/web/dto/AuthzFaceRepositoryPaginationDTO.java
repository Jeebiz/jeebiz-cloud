/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.web.dto;

import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzFaceRepositoryPaginationDTO", description = "人脸识别数据分组分页查询参数")
@Getter
@Setter
@ToString
public class AuthzFaceRepositoryPaginationDTO extends AbstractPaginationDTO {

    /**
     * 人脸识别数据分组
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "分组名称")
    private String name;
    /**
     * 人脸识别数据分组状态(0:不可用|1:正常)
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "分组状态：0:不可用|1:正常", allowableValues = "1,0")
    private String status;

}
