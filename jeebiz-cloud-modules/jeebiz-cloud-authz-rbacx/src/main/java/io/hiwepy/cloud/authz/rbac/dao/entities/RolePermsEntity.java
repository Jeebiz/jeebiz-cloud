/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

@Alias(value = "RolePermEntity")
@Accessors(chain = true)
@SuppressWarnings("serial")
@TableName(value = "t_role_perms")
@Data
@EqualsAndHashCode(callSuper = false)
public class RolePermsEntity extends Model<RolePermsEntity> {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Long roleId;
    /**
     * 角色Key
     */
    @TableField(value = "role_key")
    private String roleKey;
    /**
     * 角色授权的标记
     */
    @TableField(value = "perms")
    private String perms;

}
