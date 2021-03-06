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

/**
 * 功能菜单信息表
 */
@SuppressWarnings("serial")
@Alias(value = "FeatureEntity")
@TableName(value = "t_feature_list")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FeatureEntity extends Model<FeatureEntity> {

    /**
     * 功能菜单id
     */
    @TableId(value = "f_id", type = IdType.AUTO)
    private Long id;
    /**
     * 功能菜单名称
     */
    @TableField(value = "f_name")
    private String name;
    /**
     * 功能菜单简称
     */
    @TableField(value = "f_abb")
    private String abb;
    /**
     * 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
     */
    @TableField(value = "f_code")
    private String code;
    /**
     * 功能菜单URL
     */
    @TableField(value = "f_url")
    private String url;
    /**
     * 功能组件相对路径
     */
    @TableField(value = "f_path")
    private String path;
    /**
     * 菜单类型(1:原生|2:自定义)
     */
    @TableField(value = "f_type")
    private String type;
    /**
     * 菜单样式或菜单图标路径
     */
    @TableField(value = "f_icon")
    private String icon;
    /**
     * 菜单显示顺序
     */
    @TableField(value = "f_order")
    private String orderBy;
    /**
     * 父级功能菜单id
     */
    @TableField(value = "f_parent")
    private Long parent;
    /**
     * 菜单是否可见(1:可见|0:不可见)
     */
    @TableField(value = "f_visible")
    private String visible;
    /**
     * 菜单所拥有的权限标记
     */
    @TableField(exist = false)
    private String perms;
    /**
     * 菜单是否是根菜单
     */
    @TableField(exist = false)
    private boolean root;
    /**
     * 功能是否授权(1:已授权|0:未授权)
     */
    @TableField(exist = false)
    private String checked;
}
