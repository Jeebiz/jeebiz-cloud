package io.hiwepy.cloud.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MpsDTO {

    @ApiModelProperty(name = "templateId", dataType = "String", value = "转码模版ID")
    private String templateId;

    @ApiModelProperty(name = "inputObject", dataType = "String", value = "输入OSS对象文件名称")
    private String inputObject;

    @ApiModelProperty(name = "outputObject", dataType = "String", value = "输出OSS对象文件名称")
    private String outputObject;
}
