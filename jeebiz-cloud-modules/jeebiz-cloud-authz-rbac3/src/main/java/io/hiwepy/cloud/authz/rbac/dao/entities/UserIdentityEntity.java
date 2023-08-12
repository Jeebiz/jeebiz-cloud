/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

@Alias(value = "UserIdentityEntity")
@Accessors(chain = true)
@SuppressWarnings("serial")
@TableName(value = "t_user_identity")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserIdentityEntity {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 1 教师 2 学生 3家长 4系统用户
     */
    @TableField(value = "identity_id")
    private Integer identityId;

    /**
     * 教师id   学生id
     */
    @TableField(value = "info_id")
    private Long infoId;

}
