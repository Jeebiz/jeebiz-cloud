package io.hiwepy.cloud.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OssPutDTO {

    @ApiModelProperty(name = "avatar", required = true, dataType = "String", value = "用户头像：可网络访问的地址")
    private String avatar;

    @ApiModelProperty(name = "photos", required = true, dataType = "java.util.List<String>", value = "照片：可网络访问的地址")
    private List<String> photos;

}
