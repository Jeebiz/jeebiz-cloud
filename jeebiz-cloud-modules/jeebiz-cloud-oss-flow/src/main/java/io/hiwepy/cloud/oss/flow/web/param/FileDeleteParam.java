package io.hiwepy.cloud.oss.flow.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "FileDeleteParam", description = "文件删除DTO")
@Data
public class FileDeleteParam {

    /**
     * 要删除文件的uuid
     */
    @ApiModelProperty(name = "uuid", value = "要删除文件的uuid")
    @NotNull(message = "文件UUid不能为空")
    private String uuid;

}
