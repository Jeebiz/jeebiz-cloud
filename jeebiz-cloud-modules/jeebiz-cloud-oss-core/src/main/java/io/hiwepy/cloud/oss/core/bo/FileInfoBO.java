package io.hiwepy.cloud.oss.core.bo;

import io.hiwepy.cloud.oss.core.enums.OssChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@SuppressWarnings("serial")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileInfoBO implements Comparable<FileInfoBO> {

    /**
     * 文件id
     */
    private String id;
    /**
     * 客户端应用ID
     */
    private String appId;
    /**
     * 客户端应用渠道
     */
    private String appChannel;
    /**
     * 客户端版本
     */
    private String appVer;
    /**
     * 业务ID
     */
    private String bizId;
    /**
     * 请求来源IP地址
     */
    private String ipAddress;
    /**
     * 请求来源国：根据请求IP地址解析
     */
    private String region;
    /**
     * 文件所属用户ID
     */
    private String userId;
    /**
     * 对象存储桶名称或一级目录名称
     */
    private String bucket;
    /**
     * 文件唯一ID
     */
    private String fileId;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件存储相对路径
     */
    private String filePath;
    /**
     * 缩略图相对路径（图片类型文件）
     */
    private String fileThumb;
    /**
     * 文件大小，单位KB
     */
    private Long fileSize;
    /**
     * 文件类型
     */
    private String fileExt;
    /**
     * 音视频文件的元数据
     */
    private String fileMetadata;
    /**
     * 文件同批次的顺序编号
     */
    private int orderBy;
    /**
     * 文件存储目标
     */
    private OssChannel channel;
    /**
     * 创建人ID
     */
    private String creator;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @Override
    public int compareTo(FileInfoBO o) {
        return StringUtils.compare(this.getFileId().concat(String.valueOf(this.getOrderBy())), o.getFileId().concat(String.valueOf(o.getOrderBy())));
    }

}