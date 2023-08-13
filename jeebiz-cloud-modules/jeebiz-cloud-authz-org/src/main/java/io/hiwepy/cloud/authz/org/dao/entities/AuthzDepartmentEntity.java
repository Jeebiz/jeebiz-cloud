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
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

@Alias(value = "AuthzDepartmentModel")
@SuppressWarnings("serial")
@TableName(value = "t_org_dept")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthzDepartmentEntity extends PaginationEntity<AuthzDepartmentEntity> {

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
    @TableId(value = "dept_id", type = IdType.AUTO)
    private String id;
    /**
     * 部门编码
     */
    @TableField(value = "dept_code")
    private String code;
    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    private String name;
    /**
     * 部门说明
     */
    @TableField(value = "dept_intro")
    private String intro;
    /**
     * 父级部门id编号
     */
    @TableField(value = "dept_parent")
    private String parent;
    /**
     * 部门创建人名称
     */
    @TableField(exist = false)
    private String uname;
    /**
     * 部门状态（0:禁用|1:可用）
     */
    @TableField(value = "dept_status")
    private String status;

    public String getParent() {
        return StringUtils.defaultString(parent, "0");
    }

}
