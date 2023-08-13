package io.hiwepy.cloud.oss.flow.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "FileDownloadByFileIdParam", description = "文件下载DTO")
@Data
public class FileDownloadByFileIdParam {

    /**
     * 要下载文件的uuid
     */
    @ApiModelProperty(name = "uuid", value = "要下载文件的uuid")
    @NotNull(message = "文件UUid不能为空")
    private String uuid;

}
