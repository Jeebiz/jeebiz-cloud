/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.flow.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.boot.api.dao.entities.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("FileObjectEntity")
@TableName(value = "sys_data_file_objects")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FileObjectEntity extends BaseEntity<FileObjectEntity> {

    /**
     * 文件对象id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 文件唯一ID
     */
    @TableField(value = "file_id")
    private String fileId;
    /**
     * 文件对象
     */
    @TableField(value = "file_object")
    private byte[] fileObject;

}