/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.Lists;
import hitool.core.lang3.time.DateFormats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "RoleUserDTO", description = "角色用户信息参数DTO")
@Getter
@Setter
@ToString
public class RoleUserVO {

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    private String key;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String name;
    /**
     * 角色简介
     */
    @ApiModelProperty(value = "角色简介")
    private String intro;
    /**
     * 角色类型（1:原生|2:继承|3:复制|4:自定义）
     */
    @ApiModelProperty(value = "角色类型(1:原生|2:继承|3:复制)", allowableValues = "1,2,3")
    private String type;
    /**
     * 角色状态（0:禁用|1:可用）
     */
    @ApiModelProperty(value = "角色状态（0:禁用|1:可用）")
    private Integer status;
    /**
     * 角色授权的标记集合
     */
    @ApiModelProperty(value = "角色授权的标记集合")
    private List<String> perms = Lists.newArrayList();
    /**
     * 角色已分配用户量
     */
    @ApiModelProperty(value = "角色已分配用户量")
    private Integer users;
    /**
     * 初始化时间
     */
    @ApiModelProperty(value = "初始化时间")
    @JsonFormat(pattern = DateFormats.DATE_LONGFORMAT)
    private LocalDateTime createTime;
    /**
     *角色下的用户
     */
    @ApiModelProperty(value = "角色下的用户")
    private List<UserAccountVO> children = Lists.newArrayList();

}
