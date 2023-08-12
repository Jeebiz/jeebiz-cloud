package io.hiwepy.cloud.oss.flow.web.param;

import com.github.hiwepy.validation.constraints.FileNotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@ApiModel(value = "ImagesUploadParam", description = "图片文件上传参数")
@Data
public class ImagesUploadParam {

    /**
     * 业务ID
     */
    @ApiModelProperty(name = "bizId", required = true, value = "业务ID")
    @NotNull(message = "业务ID不能为空")
    private String bizId;
    /**
     * 文件对象数组
     */
    @ApiModelProperty(name = "files", required = true, value = "文件对象数组")
    @FileNotEmpty(extensions = {"png", "jpg", "jpeg", "bmp", "webp"},
            maxSize = "10MB", message = "仅支持png、jpg、jpeg、bmp、webp格式图片，且文件大小不得超过10MB。")
    private MultipartFile[] files;
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
