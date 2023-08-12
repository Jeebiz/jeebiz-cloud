/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.dao.entities;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.boot.api.dao.entities.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;
import java.util.Map;

@Alias(value = "UserProfileEntity")
@Accessors(chain = true)
@SuppressWarnings("serial")
@TableName(value = "t_user_profile")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserProfileEntity extends BaseEntity<UserProfileEntity> {

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long userId;
    /**
     * 用户code（短号/工号）
     */
    @TableField(value = "code")
    private String userCode;
    /**
     * 用户别名（昵称）
     */
    @TableField(value = "nickname")
    private String nickname;
    /**
     * 用户头像：图片路径或图标样式
     */
    @TableField(value = "avatar")
    private String avatar;
    /**
     * 国家/地区编码
     */
    @TableField(value = "region_code")
    private String regionCode;
    /**
     * 手机号码
     */
    @TableField(value = "phone")
    private String phone;
    /**
     * 电子邮箱
     */
    @TableField(value = "email")
    private String email;
    /**
     * 出生日期
     */
    @TableField(value = "birthday")
    private LocalDate birthday;
    /**
     * 性别：（1：男，2：女）
     */
    @TableField(value = "gender")
    private Integer gender;
    /**
     * 身份证号码
     */
    @TableField(value = "idcard")
    private String idcard;
    /**
     * 用户年龄
     */
    @TableField(value = "age")
    private Integer age;
    /**
     * 用户身高
     */
    @TableField(value = "height")
    private String height;
    /**
     * 用户体重
     */
    @TableField(value = "weight")
    private String weight;
    /**
     * 官方语言
     */
    @TableField(value = "language")
    private String language;
    /**
     * 个人签名
     */
    @TableField(value = "signature")
    private String signature;
    /**
     * 用户备注
     */
    @TableField(value = "intro")
    private String intro;
    /**
     * 个人照片（包含是否封面标记、序号、地址的JSON字符串）
     */
    @TableField(value = "photos")
    private String photos;
    /**
     * 用户位置：常驻国家
     */
    @TableField(value = "country")
    private String country;
    /**
     * 用户位置：常驻省份
     */
    @TableField(value = "province")
    private String province;
    /**
     * 用户位置：常驻城市
     */
    @TableField(value = "city")
    private String city;
    /**
     * 用户位置：常驻区域
     */
    @TableField(value = "area")
    private String area;
    /**
     * 用户位置：常驻地经度
     */
    @TableField(value = "wgs84_lng")
    private Double longitude;
    /**
     * 用户位置：常驻地纬度
     */
    @TableField(value = "wgs84_lat")
    private Double latitude;
    /**
     * 用户状态（0:禁用|1:可用）
     */
    @TableField(value = "`status`")
    private Integer status;
    /**
     * 用户信息完成度
     */
    @TableField(value = "degree")
    private Integer degree;
    /**
     * 注册客户端应用id
     */
    @TableField(value = "app_id")
    private String appId;
    /**
     * 注册客户端应用渠道编码
     */
    @TableField(value = "app_channel")
    private String appChannel;
    /**
     * 注册客户端版本
     */
    @TableField(value = "app_version")
    private String appVer;


    public Map<String, Object> toMap() {
        return BeanUtil.beanToMap(this, false, true);
    }

}
