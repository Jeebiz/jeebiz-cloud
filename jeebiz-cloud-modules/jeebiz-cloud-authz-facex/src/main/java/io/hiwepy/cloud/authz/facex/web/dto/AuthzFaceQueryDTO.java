/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzFaceQueryDTO", description = "人脸识别照片数据下载查询参数")
@Getter
@Setter
@ToString
public class AuthzFaceQueryDTO {

    /**
     * 人脸识别数据分组ID
     */
    @ApiModelProperty(name = "gid", dataType = "String", value = "人脸识别数据分组ID")
    private String gid;
    /**
     * 人脸的类型：(LIVE:表示生活照;通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等|IDCARD:表示身份证芯片照;二代身份证内置芯片中的人像照片|WATERMARK:表示带水印证件照;一般为带水印的小图，如公安网小图|CERT:表示证件照片;如拍摄的身份证、工卡、护照、学生证等证件图片);
     */
    @ApiModelProperty(name = "type", dataType = "String", value = "人脸识别数据分组", allowableValues = ",LIVE,IDCARD,WATERMARK,CERT")
    private String type;
    /**
     * 是否有照片:（0:无照片|1：有照片）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "是否有照片:（0:无照片|1：有照片）", allowableValues = "0,1")
    private String status;
    /**
     * 数据采集起始时间
     */
    @ApiModelProperty(name = "begintime", dataType = "String", value = "数据采集起始时间")
    private String begintime;
    /**
     * 数据采集结束时间
     */
    @ApiModelProperty(name = "endtime", dataType = "String", value = "数据采集结束时间")
    private String endtime;
    /**
     * 关键词搜索
     */
    @ApiModelProperty(name = "keywords", dataType = "String", value = "关键词")
    private String keywords;

}
