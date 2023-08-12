package io.hiwepy.cloud.oss.flow.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "FileListByFileIdParam", description = "文件查询DTO")
@Data
public class FileListByFileIdParam {

    /**
     * 要查询文件的uuid
     */
    @ApiModelProperty(name = "uuids", value = "要查询文件的uuid")
    @NotNull(message = "文件UUid不能为空")
    private List<String> uuids;
    /**
     * 是否需要获取文件的元信息
     */
    @ApiModelProperty(value = "是否需要获取文件的元信息")
    private boolean metadata;

}
