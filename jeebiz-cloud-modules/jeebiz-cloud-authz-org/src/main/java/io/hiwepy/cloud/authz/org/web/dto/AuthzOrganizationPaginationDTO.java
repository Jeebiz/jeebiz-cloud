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

@ApiModel(value = "AuthzOrganizationPaginationDTO", description = "机构信息分页查询参数")
@Getter
@Setter
@ToString
public class AuthzOrganizationPaginationDTO extends AbstractPaginationDTO {

    /**
     * 机构名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "机构名称")

    private String name;

}
