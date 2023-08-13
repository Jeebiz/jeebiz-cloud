/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.org.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Alias(value = "AuthzTeamModel")
@SuppressWarnings("serial")
@TableName(value = "t_org_team")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthzTeamEntity extends PaginationEntity<AuthzTeamEntity> {

    /**
     * 机构id编号
     */
    @TableField(value = "org_id")
    private String orgId;
    /**
     * 机构名称
     */
    @TableField(exist = false)
    private String orgName;
    /**
     * 部门id编号
     */
    @TableField(value = "dept_id")
    private String deptId;
    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;
    /**
     * 团队id编号
     */
    @TableId(value = "team_id", type = IdType.AUTO)
    private String id;
    /**
     * 团队名称
     */
    @TableField(value = "team_name")
    private String name;
    /**
     * 团队简介
     */
    @TableField(value = "team_intro")
    private String intro;
    /**
     * 团队创建人名称
     */
    @TableField(exist = false)
    private String uname;
    /**
     * 团队状态（0:禁用|1:可用）
     */
    @TableField(value = "team_status")
    private String status;

}
