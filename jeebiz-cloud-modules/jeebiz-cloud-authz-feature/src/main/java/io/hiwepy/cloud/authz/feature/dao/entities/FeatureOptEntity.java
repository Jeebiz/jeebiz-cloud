/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.feature.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("FeatureOptEntity")
@TableName(value = "t_feature_opts")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FeatureOptEntity extends Model<FeatureOptEntity> {

    /**
     * 功能菜单id
     */
    @TableField(value = "f_id")
    private Long featureId;
    /**
     * 功能操作id
     */
    @TableId(value = "opt_id", type = IdType.AUTO)
    private Long id;
    /**
     * 功能操作名称
     */
    @TableField(value = "opt_name")
    private String name;
    /**
     * 功能操作图标样式
     */
    @TableField(value = "opt_icon")
    private String icon;
    /**
     * 功能操作排序
     */
    @TableField(value = "opt_order")
    private String orderBy;
    /**
     * 功能操作是否可见(1:可见|0:不可见)
     */
    @TableField(value = "opt_visible")
    private String visible;
    /**
     * 功能操作是否授权(1:已授权|0:未授权)
     */
    @TableField(exist = false)
    private String checked;
    /**
     * 功能操作权限标记
     */
    @TableField(value = "opt_perms")
    private String perms;

}
