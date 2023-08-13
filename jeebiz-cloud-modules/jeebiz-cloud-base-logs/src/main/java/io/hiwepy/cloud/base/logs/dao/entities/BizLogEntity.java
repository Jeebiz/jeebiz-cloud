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
 * 功能操作日志信息表Model
 */
@SuppressWarnings("serial")
@Alias("BizLogEntity")
@TableName(value = "t_log_biz")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BizLogEntity extends PaginationEntity<BizLogEntity> {

    /**
     * 日志id
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long id;
    /**
     * 功能模块
     */
    @TableField(value = "log_module")
    private String module;
    /**
     * 日志级别
     */
    @TableField(value = "log_level")
    private String level;
    /**
     * 业务名称
     */
    @TableField(value = "log_biz")
    private String business;
    /**
     * 操作类型
     */
    @TableField(value = "log_opt")
    private String opt;
    /**
     * 功能操作请求来源IP地址
     */
    @TableField(value = "log_addr")
    private String addr;
    /**
     * 功能操作请求来源IP所在地点
     */
    @TableField(value = "log_location")
    private String location;
    /**
     * 功能操作描述
     */
    @TableField(value = "log_msg")
    private String msg;
    /**
     * 功能操作异常信息
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

}
