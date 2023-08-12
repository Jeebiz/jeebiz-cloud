/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ApiModel(value = "UserAllocatedVO", description = "用户描述信息DTO")
@Getter
@Setter
@ToString
public class UserAllocatedVO {

    /**
     * 用户详情Id
     */
    @ApiModelProperty(value = "用户详情Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 用户别名（昵称）
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    /**
     * 用户头像：图片路径或图标样式
     */
    @ApiModelProperty(value = "用户头像：图片路径或图标样式")
    private String avatar;
    /**
     * 性别：（M：男，F：女）
     */
    @ApiModelProperty(value = "性别：（M：男，F：女）")
    private String gender;
    /**
     * 用户状态（0:禁用|1:可用）
     */
    @ApiModelProperty(value = "用户状态（0:禁用|1:可用）")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
