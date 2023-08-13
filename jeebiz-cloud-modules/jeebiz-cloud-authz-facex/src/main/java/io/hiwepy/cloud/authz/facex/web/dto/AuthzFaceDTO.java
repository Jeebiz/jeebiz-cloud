/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "AuthzFaceDTO", description = "人脸识别数据参数DTO")
@Getter
@Setter
public class AuthzFaceDTO {

    /**
     * 用户ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "用户ID")
    private String id;
    /**
     * 人脸识别数据分组ID
     */
    @ApiModelProperty(name = "rid", dataType = "String", value = "人脸识别数据分组ID")
    private String rid;
    /**
     * 分组名称
     */
    @ApiModelProperty(name = "fid", dataType = "String", value = "分组名称")
    private String rname;
    /**
     * 人脸识别数据ID
     */
    @ApiModelProperty(name = "fid", dataType = "String", value = "人脸识别数据ID")
    private String fid;
    /**
     * 人脸识别图片base64编码后的图片数据（图片的base64编码不包含图片头的，如data:image/jpg;base64,）
     */
    @ApiModelProperty(name = "face", dataType = "String", value = "人脸识别图片base64编码后的图片数据（图片的base64编码不包含图片头的，如data:image/jpg;base64,）")
    private String face;
    /**
     * 人脸的类型：(LIVE:表示生活照;通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等|IDCARD:表示身份证芯片照;二代身份证内置芯片中的人像照片|WATERMARK:表示带水印证件照;一般为带水印的小图，如公安网小图|CERT:表示证件照片;如拍摄的身份证、工卡、护照、学生证等证件图片);
     * 默认LIVE
     */
    @ApiModelProperty(name = "type", dataType = "String", value = "人脸的类型：(LIVE:表示生活照;通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等|IDCARD:表示身份证芯片照;二代身份证内置芯片中的人像照片|WATERMARK:表示带水印证件照;一般为带水印的小图，如公安网小图|CERT:表示证件照片;如拍摄的身份证、工卡、护照、学生证等证件图片); 默认LIVED")
    private String type;
    /**
     * 人脸图片的唯一标识
     */
    @ApiModelProperty(name = "token", dataType = "String", value = "人脸图片的唯一标识")
    private String token;
    /**
     * 是否有照片:（0:无照片|1：有照片）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "是否有照片:（0:无照片|1：有照片）", allowableValues = "0,1")
    private String status;
    /**
     *采集时间
     */
    @ApiModelProperty(name = "time24", dataType = "String", value = "采集时间")
    private String time24;

}
