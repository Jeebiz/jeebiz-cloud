/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.dict.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 基础数据字典信息Model
 */
@Alias("DictGroupEntity")
@SuppressWarnings("serial")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_data_pairgroup")
public class DictGroupEntity extends PaginationEntity<DictGroupEntity> {

    /**
     * 数据字典id
     */
    @TableId(value = "g_id", type = IdType.AUTO)
    private String id;
    /**
     * 数据字典键
     */
    @TableField(value = "g_key")
    private String key;
    /**
     * 数据字典值
     */
    @TableField(value = "g_text")
    private String value;
    /**
     * 数据字典简介
     */
    @TableField(value = "g_intro")
    private String intro;
    /**
     * 数据字典状态：0:不可用、1：可用
     */
    @TableField(value = "g_status")
    private String status;
    /**
     * 数据字典排序
     */
    @TableField(value = "g_order")
    private int orderBy;

}
