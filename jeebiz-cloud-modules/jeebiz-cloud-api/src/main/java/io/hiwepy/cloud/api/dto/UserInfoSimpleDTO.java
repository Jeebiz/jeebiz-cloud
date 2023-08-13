package io.hiwepy.cloud.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class UserInfoSimpleDTO {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 用户号（工号、教职工号、学生号）
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
