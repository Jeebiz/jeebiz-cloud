package io.hiwepy.cloud.oss.core.bo;

import io.hiwepy.cloud.oss.core.enums.OssChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class FileStoreBO {

    /**
     * 支付调试（务必保证当前对象不能直接作为Controller参数对象，以免发生生产事故）
     */
    private boolean debug;
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 应用渠道编码
     */
    private String appChannel;
    /**
     * 应用版本号
     */
    private String appVer;
    /**
     * 文件存储来源IP地址
     */
    private String ipAddress;
    /**
     * 业务ID
     */
    private String bizId;
    /**
     * 发起文件存储的用户uid
     */
    private String userId;
    /**
     * 文件存储渠道
     */
    private OssChannel channel;
    /**
     * 存储文件名称
     */
    private String storeFileName;
    /**
     * 是否缩略图,默认false
     */
    private boolean thumb;
    /**
     * 缩放长度
     */
    private Integer thumbWidth;
    /**
     * 缩放高度
     */
    private Integer thumbHeight;
    /**
     * minio获取其他存储bucket
     */
    private String bucketName;
    /**
     * 文件Id
     */
    private String fileId;

}
