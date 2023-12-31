/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@ApiModel(value = "AuthzOrganizationDTO", description = "机构信息DTO")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzOrganizationDTO implements Serializable {

    /**
     * 机构id编号
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "机构id编号")
    private String id;
    /**
     * 机构编码
     */
    @ApiModelProperty(name = "code", dataType = "String", value = "机构编码")
    private String code;
    /**
     * 机构名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "机构名称")
    private String name;
    /**
     * 机构简介
     */
    @ApiModelProperty(name = "intro", dataType = "String", value = "机构简介")
    private String intro;
    /**
     * 父级机构id编号
     */
    @ApiModelProperty(name = "parent", dataType = "String", value = "父级机构id编号")
    private String parent = "0";
    /**
     * 机构创建人id
     */
    @ApiModelProperty(name = "uid", dataType = "String", value = "机构创建人id")
    private String uid;
    /**
     * 机构状态（0:禁用|1:可用）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "机构状态（0:禁用|1:可用）")
    private String status;
    /**
     * 机构创建时间
     */
    @ApiModelProperty(name = "time24", dataType = "String", value = "机构创建时间")
    private String time24;

    public String getParent() {
        return StringUtils.defaultString(parent, "0");
    }

}
