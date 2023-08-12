package io.hiwepy.cloud.oss.flow.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "FileDeleteByFileIdParam", description = "文件删除DTO")
@Data
public class FileDeleteByFileIdParam {

    /**
     * 要删除文件的uuid
     */
    @ApiModelProperty(name = "uuids", value = "要删除文件的uuid")
    @NotNull(message = "文件UUid不能为空")
    private List<String> uuids;

}
