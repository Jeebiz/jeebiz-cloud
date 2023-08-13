/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

@Alias("AuthzFaceRepositoryEntity")
@Accessors(chain = true)
@Getter
@Setter
@SuppressWarnings("serial")
@ToString
@TableName(value = "t_user_face_repo")
@EqualsAndHashCode(callSuper = true)
public class AuthzFaceRepositoryEntity extends PaginationEntity<AuthzFaceRepositoryEntity> {

    /**
     * 分组ID
     */
    @TableId(value = "repo_id", type = IdType.AUTO)
    private String id;
    /**
     * 分组名称
     */
    @TableField(value = "repo_name")
    private String name;
    /**
     * 分组状态（0:禁用|1:可用）
     */
    @TableField(value = "repo_status")
    private String status;
    /**
     * 该分组下已有多少人脸识别数据
     */
    @TableField(exist = false)
    private int faces;

}
