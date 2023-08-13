/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias(value = "RoleEntity")
@Accessors(chain = true)
@SuppressWarnings("serial")
@TableName(value = "t_role_list")
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleEntity extends PaginationEntity<RoleEntity> {

    /**
     * 角色id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 角色编码
     */
    @TableField(value = "`key`")
    private String key;
    /**
     * 角色名称
     */
    @TableField(value = "`name`")
    private String name;
    /**
     * 角色类型（1:原生|2:继承|3:复制|4:自定义）
     */
    @TableField(value = "type")
    private String type;
    /**
     * 角色简介
     */
    @TableField(value = "intro")
    private String intro;
    /**
     * 角色状态（0:禁用|1:可用）
     */
    @TableField(value = "`status`")
    private Integer status;
    /**
     * 角色授权的标记集合
     */
    @TableField(exist = false)
    private List<String> perms;
    /**
     * 角色已分配用户量
     */
    @TableField(exist = false)
    private Integer users;
    /**
     * 用户id集合
     */
    @TableField(exist = false)
    private List<String> userIds;

}
