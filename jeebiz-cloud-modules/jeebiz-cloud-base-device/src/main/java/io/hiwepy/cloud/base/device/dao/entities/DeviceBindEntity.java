/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.device.dao.entities;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@SuppressWarnings("serial")
@Alias(value = "DeviceBindModel")
@TableName(value = "device_users", keepGlobalPrefix = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeviceBindEntity extends Model<DeviceBindEntity> implements Cloneable {

    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
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
     *应用版本号
     */
    @TableField(value = "u_app_version")
    private String appVersion;
    /**
     *设备记录ID
     */
    @TableField(value = "device_id")
    private String deviceId;
    /**
     * 是否删除 0未删除 1已删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;
    /**
     * 创建人id
     */
    @TableField(value = "creator")
    private String creator;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新人id
     */
    @TableField(value = "modifyer")
    private String modifyer;
    /**
     * 更新时间
     */
    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

}