/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import hitool.core.lang3.time.DateFormats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel(value = "UserDTO", description = "用户信息参数DTO")
@Data
public class UserAccountVO {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 用户唯一编号（工号）
     */
    @ApiModelProperty(value = "用户唯一id（员工信息表id）")
    private String uid;
    /**
     * 用户唯一编号（工号）
     */
    @ApiModelProperty(value = "用户唯一编号（工号）")
    private String ucode;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String account;
    /**
     * 用户秘钥：用于用户JWT加解密
     */
    @ApiModelProperty(value = "用户秘钥：用于用户JWT加解密")
    private String secret;
    /**
     * 用户客户端应用id
     */
    @ApiModelProperty(value = "用户客户端应用id")
    private String appId;
    /**
     * 用户客户端应用渠道编码
     */
    @ApiModelProperty(value = "用户客户端应用渠道编码")
    private String appChannel;
    /**
     * 用户客户端版本
     */
    @ApiModelProperty(value = "用户客户端应用版本号")
    private String appVer;

    /**
     * 用户是否在线（1：是，0：否）
     */
    @ApiModelProperty(value = "用户是否在线（1：是，0：否）")
    private Integer online;
    /**
     * 用户最近一次登录时间
     */
    @ApiModelProperty(value = "用户最近一次登录时间")
    private String onlineLatest;

    /**
     * 角色id（可能多个组合，如：1,2）
     */
    @ApiModelProperty(value = "角色id（可能多个组合，如：1,2）")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;
    /**
     * 角色名称（可能多个组合，如：角色1,角色2）
     */
    @ApiModelProperty(value = "角色名称（可能多个组合，如：角色1,角色2）")
    private String roleName;
    /**
     * 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
     */
    @ApiModelProperty(value = "用户状态（0:禁用|1:可用|2:锁定|3:密码过期）")
    private Integer status;
    /**
     * 初始化时间
     */
    @ApiModelProperty(value = "初始化时间")
    @JsonFormat(pattern = DateFormats.DATE_LONGFORMAT, timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 用户详情信息
     */
    @ApiModelProperty(value = "用户详情信息")
    private UserProfileVO profile;

}
