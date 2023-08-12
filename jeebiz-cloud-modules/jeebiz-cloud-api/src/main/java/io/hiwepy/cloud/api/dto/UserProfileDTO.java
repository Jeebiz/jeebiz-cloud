package io.hiwepy.cloud.api.dto;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import hitool.core.lang3.time.DateFormats;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
public class UserProfileDTO implements Serializable {

    /**
     * 用户详情Id
     */
    @ApiModelProperty(value = "用户详情Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 用户别名（昵称）
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    /**
     * 用户头像：图片路径或图标样式
     */
    @ApiModelProperty(value = "用户头像：图片路径或图标样式")
    private String avatar;
    /**
     * 手机号码国家码
     */
    @ApiModelProperty(value = "手机号码国家码")
    private String countryCode;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;
    /**
     * 性别：（M：男，F：女）
     */
    @ApiModelProperty(value = "性别：（M：男，F：女）")
    private String gender;
    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱")
    private String email;
    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    private String birthday;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String idcard;
    /**
     * 用户年龄
     */
    @ApiModelProperty(value = "用户年龄")
    private Integer age;
    /**
     *用户身高
     */
    @ApiModelProperty(value = "用户身高")
    private String height;
    /**
     *用户体重
     */
    @ApiModelProperty(value = "用户体重")
    private String weight;
    /**
     * 官方语言
     */
    @ApiModelProperty(value = "官方语言")
    private String language;
    /**
     * 用户简介
     */
    @ApiModelProperty(value = "用户简介")
    private String intro;
    /**
     * 个人照片（包含是否封面标记、序号、地址的JSON对象）
     */
    @ApiModelProperty(value = "个人照片（包含是否封面标记、序号、地址的JSON对象）")
    private JSONArray photos;
    /**
     * 用户位置：常驻省份
     */
    @ApiModelProperty(value = "用户位置：常驻省份")
    private String province;
    /**
     * 用户位置：常驻城市
     */
    @ApiModelProperty(value = "用户位置：常驻城市")
    private String city;
    /**
     * 用户位置：常驻区域
     */
    @ApiModelProperty(value = "用户位置：常驻区域")
    private String area;
    /**
     * 用户位置：常驻地经度
     */
    @ApiModelProperty(value = "用户位置：常驻地经度")
    private Double longitude;
    /**
     * 用户位置：常驻地纬度
     */
    @ApiModelProperty(value = "用户位置：常驻地纬度")
    private Double latitude;
    /**
     *用户信息完成度
     */
    @ApiModelProperty(value = "用户信息完成度")
    private Integer degree;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = DateFormats.DATE_LONGFORMAT, timezone = "GMT+8")
    private LocalDateTime createTime;

}
