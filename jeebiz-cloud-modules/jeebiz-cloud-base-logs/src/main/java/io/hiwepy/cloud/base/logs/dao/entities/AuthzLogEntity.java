/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * 认证授权日志信息表Model
 */
@SuppressWarnings("serial")
@Alias(value = "AuthzLogEntity")
@TableName(value = "t_log_authz")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthzLogEntity extends PaginationEntity<AuthzLogEntity> {

    /**
     * 日志id
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long id;
    /**
     * 日志级别
     */
    @TableField(value = "log_level")
    private String level;
    /**
     * 认证授权类型（login:登录认证、logout:会话注销）
     */
    @TableField(value = "log_opt")
    private String opt;
    /**
     * 认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、Openid、SMAL等
     */
    @TableField(value = "log_protocol")
    private String protocol;
    /**
     * 认证请求来源IP地址
     */
    @TableField(value = "log_addr")
    private String addr;
    /**
     * 认证请求来源IP所在地点
     */
    @TableField(value = "log_location")
    private String location;
    /**
     * 认证授权结果：（fail:失败、success:成功）
     */
    @TableField(value = "log_status")
    private String status;
    /**
     * 认证授权请求信息
     */
    @TableField(value = "log_msg")
    private String msg;
    /**
     * 认证授权异常信息
     */
    @TableField(value = "log_excp")
    private String exception;
    /**
     * 设备记录ID
     */
    @TableField(value = "device_id")
    private String deviceId;
    /**
     * 应用ID
     */
    @TableField(value = "u_app_id")
    private String appId;
    /**
     * 应用渠道编码
     */
    @TableField(value = "u_app_channel")
    private String appChannel;
    /**
     * 应用版本号
     */
    @TableField(value = "u_app_version")
    private String appVersion;
    /**
     * 操作人
     */
    @TableField(value = "log_nickname")
    private String nickName;

}
