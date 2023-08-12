package io.hiwepy.cloud.oss.flow.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "FileDeleteByPathParam", description = "文件删除DTO")
@Data
public class FileDeleteByPathParam {

    /**
     * 要删除文件的路径
     */
    @ApiModelProperty(name = "paths", value = "要删除文件的路径")
    @NotNull(message = "文件路径不能为空")
    private List<String> paths;

}
