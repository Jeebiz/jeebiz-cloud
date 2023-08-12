/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.boot.api.dao.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

@Alias(value = "UserRoleEntity")
@Accessors(chain = true)
@TableName(value = "t_user_roles")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class UserRoleEntity extends BaseEntity<UserRoleEntity> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;
    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;
    /**
     * 优先级：用于默认登录角色
     */
    @TableField(value = "priority")
    private Integer priority;

}
