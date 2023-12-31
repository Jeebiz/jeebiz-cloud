/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.feature.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "FeatureOptNewDTO", description = "新增功能操作参数DTO")
@Getter
@Setter
@ToString
public class FeatureOptNewDTO implements Comparable<FeatureOptNewDTO> {

    /**
     * 功能菜单id
     */
    @ApiModelProperty(name = "featureId", required = true, dataType = "String", value = "功能菜单id")
    @NotBlank(message = "功能菜单id必填")
    private Long featureId;
    /**
     * 功能操作名称
     */
    @ApiModelProperty(name = "name", required = true, dataType = "String", value = "功能操作名称")
    @NotBlank(message = "功能操作名称必填")
    private String name;
    /**
     * 功能操作图标样式
     */
    @ApiModelProperty(name = "icon", required = true, dataType = "String", value = "功能操作图标样式")
    @NotBlank(message = "功能操作图标样式必填")
    private String icon;
    /**
     * 功能操作排序
     */
    @ApiModelProperty(name = "order", required = true, dataType = "String", value = "功能操作排序")
    @NotBlank(message = "功能操作排序必填")
    private String order;
    /**
     * 功能操作是否可见(1:可见|0:不可见)
     */
    @ApiModelProperty(name = "visible", required = true, dataType = "String", value = "功能操作是否可见(1:可见|0:不可见)", allowableValues = "1,0")
    @NotBlank(message = "功能操作是否可见必填")
    private String visible;
    /**
     * 功能操作权限标记
     */
    @ApiModelProperty(name = "perms", required = true, dataType = "String", value = "功能操作权限标记")
    @NotBlank(message = "功能操作权限标记必填")
    private String perms;

    @Override
    public int compareTo(FeatureOptNewDTO o) {
        return Integer.parseInt(order) - Integer.parseInt(o.getOrder());
    }

}
