package io.hiwepy.cloud.oss.flow.web.dto;

import io.hiwepy.cloud.oss.core.bo.FileMetaData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@ApiModel(value = "FileStoreDTO", description = "文件存储信息DTO")
@Data
public class FileStoreDTO {

    @ApiModelProperty(name = "uuid", dataType = "String", value = "文件UUID")
    private String uuid;
    @ApiModelProperty(name = "name", dataType = "String", value = "文件名称")
    private String name;
    @ApiModelProperty(name = "path", dataType = "String", value = "文件存储路径")
    private String path;
    @ApiModelProperty(name = "thumb", dataType = "String", value = "缩略图存储路径（图片类型文件）")
    private String thumb;
    @ApiModelProperty(name = "url", dataType = "String", value = "文件访问地址")
    private String url;
    @ApiModelProperty(name = "thumbUrl", dataType = "String", value = "缩略图访问地址（图片类型文件）")
    private String thumbUrl;
    @ApiModelProperty(name = "ext", dataType = "String", value = "文件类型")
    private String ext;
    @ApiModelProperty(name = "metadata", dataType = "java.util.Set<FileMetaDataDTO>", value = "文件元信息")
    private Set<FileMetaData> metadata;

}

