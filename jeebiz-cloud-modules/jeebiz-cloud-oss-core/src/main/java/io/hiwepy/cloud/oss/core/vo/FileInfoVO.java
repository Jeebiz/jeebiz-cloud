package io.hiwepy.cloud.oss.core.vo;

import io.hiwepy.cloud.oss.core.bo.FileMetaData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@ApiModel(value = "FileInfoVO", description = "文件存储信息")
@Data
public class FileInfoVO {

    @ApiModelProperty(name = "bucket", value = "对象存储桶名称或一级目录名称")
    private String bucket;

    @ApiModelProperty(name = "uuid", value = "文件uuid")
    private String uuid;

    @ApiModelProperty(name = "name", value = "文件名称")
    private String name;

    @ApiModelProperty(name = "path", value = "文件存储路径")
    private String path;

    @ApiModelProperty(value = "文件大小，单位KB")
    private Long size;

    @ApiModelProperty(name = "base64", value = "图片Base64值")
    private String base64;

    @ApiModelProperty(name = "url", value = "文件访问地址")
    private String url;

    @ApiModelProperty(name = "thumb", value = "缩略图存储路径（图片类型文件）")
    private String thumb;

    @ApiModelProperty(name = "thumbUrl", value = "缩略图访问地址（图片类型文件）")
    private String thumbUrl;

    @ApiModelProperty(name = "ext", value = "文件类型")
    private String ext;

    @ApiModelProperty(name = "index", value = "文件排序")
    private int index;

    @ApiModelProperty(name = "metadata", value = "文件元信息")
    private Set<FileMetaData> metadata;

}
