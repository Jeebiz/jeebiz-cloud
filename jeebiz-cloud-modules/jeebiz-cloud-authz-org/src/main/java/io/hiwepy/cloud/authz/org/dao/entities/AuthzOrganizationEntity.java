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

@Alias(value = "AuthzOrganizationModel")
@SuppressWarnings("serial")
@TableName(value = "t_org_list")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthzOrganizationEntity extends PaginationEntity<AuthzOrganizationEntity> {

    /**
     * 机构id编号
     */
    @TableId(value = "org_id", type = IdType.AUTO)
    private String id;
    /**
     * 机构编码
     */
    @TableField(value = "org_code")
    private String code;
    /**
     * 机构名称
     */
    @TableField(value = "org_name")
    private String name;
    /**
     * 机构说明
     */
    @TableField(value = "org_intro")
    private String intro;
    /**
     * 父级机构id编号
     */
    @TableField(value = "org_parent")
    private String parent;
    /**
     * 机构创建人名称
     */
    @TableField(exist = false)
    private String uname;
    /**
     * 机构状态（0:禁用|1:可用）
     */
    @TableField(value = "org_status")
    private String status;

    public String getParent() {
        return StringUtils.defaultString(parent, "0");
    }

}
