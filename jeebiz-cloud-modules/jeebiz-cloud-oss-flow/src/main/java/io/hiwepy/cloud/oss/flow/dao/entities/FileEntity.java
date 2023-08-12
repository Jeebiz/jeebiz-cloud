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
import io.hiwepy.cloud.oss.core.enums.OssChannel;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("FileEntity")
@TableName(value = "sys_data_files")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FileEntity extends BaseEntity<FileEntity> implements Comparable<FileEntity> {

    /**
     * 文件id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 客户端应用ID
     */
    @TableField(value = "app_id")
    private String appId;
    /**
     * 客户端应用渠道
     */
    @TableField(value = "app_channel")
    private String appChannel;
    /**
     * 客户端版本
     */
    @TableField(value = "app_version")
    private String appVer;
    /**
     * 业务ID
     */
    @TableField(value = "biz_id")
    private String bizId;
    /**
     * 请求来源IP地址
     */
    @TableField(value = "source_ip")
    private String ipAddress;
    /**
     * 请求来源国：根据请求IP地址解析
     */
    @TableField(value = "source_region")
    private String region;
    /**
     * 文件所属用户ID
     */
    @TableField(value = "user_id")
    private String userId;
    /**
     * 对象存储桶名称或一级目录名称
     */
    @TableField(value = "bucket_name")
    private String bucket;
    /**
     * 文件唯一ID
     */
    @TableField(value = "file_id")
    private String fileId;
    /**
     * 文件名称
     */
    @TableField(value = "file_name")
    private String fileName;
    /**
     * 文件存储相对路径
     */
    @TableField(value = "file_path")
    private String filePath;
    /**
     * 缩略图相对路径（图片类型文件）
     */
    @TableField(value = "file_thumb")
    private String fileThumb;
    /**
     * 文件大小，单位KB
     */
    @TableField(value = "file_size")
    private Long fileSize;
    /**
     * 文件类型
     */
    @TableField(value = "file_ext")
    private String fileExt;
    /**
     * 音视频文件的元数据
     */
    @TableField(value = "file_metadata")
    private String fileMetadata;
    /**
     * 文件同批次的顺序编号
     */
    @TableField(value = "file_order")
    private int orderBy;
    /**
     * 文件存储目标
     */
    @TableField(value = "channel")
    private OssChannel channel;

    @Override
    public int compareTo(FileEntity o) {
        return StringUtils.compare(this.getFileId().concat(String.valueOf(this.getOrderBy())), o.getFileId().concat(String.valueOf(o.getOrderBy())));
    }

}