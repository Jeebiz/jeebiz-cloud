package io.hiwepy.cloud.oss.flow.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "FileGetByPathParam", description = "文件查询参数")
@Data
public class FileGetByPathParam {

    /**
     * 要查询文件的路径
     */
    @ApiModelProperty(value = "要查询文件的路径")
    @NotBlank(message = "文件路径不能为空")
    private String path;
    /**
     * 是否需要获取文件的元信息
     */
    @ApiModelProperty(value = "是否需要获取文件的元信息")
    private boolean metadata;

}
