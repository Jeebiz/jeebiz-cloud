package io.hiwepy.cloud.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.hiwepy.boot.autoconfigure.jackson.annotation.Sensitive;
import io.hiwepy.boot.autoconfigure.jackson.annotation.SensitiveStrategy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserInfoDTO {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 用户号
     */
    @ApiModelProperty(value = "用户号（工号、教职工号、学生号）")
    private String userCode;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;
    /**
     * 性别 1男、2女
     */
    @ApiModelProperty(value = "性别 1男、2女")
    private Integer gender;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    @Sensitive(strategy = SensitiveStrategy.ID_CARD)
    private String idcard;
    /**
     * 出生日期
     */
    @ApiModelProperty(value = "生日")
    private Long birthday;
    /**
     * 用户年龄
     */
    @ApiModelProperty(value = "用户年龄")
    private Integer age;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String mobile;
    /**
     * 座机
     */
    @ApiModelProperty(value = "座机")
    private String landline;
    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址")
    private String email;
    /**
     * 签名
     */
    @ApiModelProperty(value = "签名")
    private String signature;
    /**
     * 用户状态 默认1（正常） 0异常
     */
    @ApiModelProperty(value = "用户状态 默认1（正常） 0异常")
    private Integer status;
    /**
     * 注册日期
     */
    @ApiModelProperty(value = "注册日期")
    private String createTime;

}
