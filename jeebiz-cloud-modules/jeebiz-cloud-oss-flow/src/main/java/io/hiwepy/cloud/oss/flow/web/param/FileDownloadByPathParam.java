package io.hiwepy.cloud.oss.flow.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "FileDownloadByPathParam", description = "文件下载DTO")
@Data
public class FileDownloadByPathParam {

    /**
     * 要下载文件的路径
     */
    @ApiModelProperty(name = "path", value = "要下载文件的路径")
    @NotNull(message = "文件路径不能为空")
    private String path;

}
