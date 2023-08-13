package io.hiwepy.cloud.oss.core.bo;

import io.hiwepy.cloud.oss.core.enums.OssChannel;
import io.hiwepy.cloud.oss.core.vo.FileInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.InputStream;

@ApiModel(value = "FileUploadResult", description = "文件上传结果")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResult {

    /**
     * 发起文件存储的用户uid
     */
    @ApiModelProperty(name = "userId", required = true, value = "发起文件存储的用户uid")
    private String userId;
    /**
     * 文件存储渠道
     */
    @ApiModelProperty(name = "channel", required = true, value = "文件存储渠道")
    private OssChannel channel;

    /**
     * 文件对象
     */
    @ApiModelProperty(name = "file", required = true, value = "文件对象")
    private FileInfoVO file;

    /*
     * 文件字节码
     */
    private byte[] bytes;
    /**
     * 文件流对象
     */
    private InputStream stream;

    /**
     * 文件上传状态（ 0：上传失败、1：上传成功）
     */
    @ApiModelProperty(name = "status", value = "文件上传送状态（ 0：上传失败、1：上传成功）")
    private Integer status;

}

