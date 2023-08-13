package io.hiwepy.cloud.oss.flow.web.param;

import com.github.hiwepy.validation.constraints.FileNotEmpty;
import io.hiwepy.cloud.oss.core.enums.OssChannel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@ApiModel(value = "FileReuploadParam", description = "文件上传参数")
@Data
public class FileReuploadParam {

    /**
     * 要重新上传文件的uuid
     */
    @ApiModelProperty(name = "uuid", value = "要重新上传的uuid")
    @NotNull(message = "文件UUID不能为空")
    private String uuid;
    /**
     * 存储目标
     */
    @ApiModelProperty(value = "存储目标", required = true, allowableValues = "LOCAL,FDFS,OSS_ALIYUN,OSS_TENCENT,OSS_BAIDU,OSS_HUAWEI,OSS_MINIO", example = "OSS_MINIO")
    @NotNull(message = "存储目标不能为空")
    private OssChannel channel;
    /**
     * 文件对象
     */
    @ApiModelProperty(name = "file", required = true, value = "文件对象")
    @FileNotEmpty(extensions = {"png", "jpg", "jpeg", "bmp", "webp", "xls", "xlsx", "doc", "docx", "ppt", "pptx", "pdf", "txt", "zip"},
            maxSize = "100MB", message = "仅支持png、jpg、jpeg、bmp、webp、xls、xlsx、doc、docx、ppt、pptx、pdf、txt、zip 格式文件，且文件大小不得超过100MB。")
    private MultipartFile file;
    /**
     * 是否缩略图,默认false
     */
    @ApiModelProperty(name = "thumb", value = "是否缩略图", example = "false")
    private boolean thumb;
    /**
     * 缩放长度
     */
    @ApiModelProperty(name = "width", value = "缩放长度", example = "0")
    private Integer width;
    /**
     * 缩放高度
     */
    @ApiModelProperty(name = "height", value = "缩放高度", example = "0")
    private Integer height;

}
