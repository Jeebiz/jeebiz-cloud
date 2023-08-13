package io.hiwepy.cloud.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务操作日志信息DTO
 */
@ApiModel(value = "BizLogNewDTO", description = "业务操作日志信息DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizLogNewDTO {

    /**
     * 功能模块
     */
    @ApiModelProperty(value = "功能模块名称")
    private String module;
    /**
     * 业务名称
     */
    @ApiModelProperty(value = "业务名称")
    private String business;
    /**
     * 操作类型
     */
    @ApiModelProperty(value = "操作类型")
    private String opt;
    /**
     * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
     */
    @ApiModelProperty(value = "日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）")
    private String level;
    /**
     * 功能操作请求来源IP
     */
    @ApiModelProperty(value = " 功能操作请求来源IP")
    private String addr;
    /**
     * 功能操作请求来源IP地址所在地
     */
    @ApiModelProperty(value = "功能操作请求来源IP地址所在地")
    private String location;
    /**
     * 操作描述
     */
    @ApiModelProperty(value = "操作描述")
    private String msg;
    /**
     * 功能操作异常信息
     */
    @ApiModelProperty(value = "功能操作异常信息")
    private String exception;
    /**
     * 操作人ID
     */
    @ApiModelProperty(value = "操作人ID")
    private String userId;
    /**
     * 操作发生时间
     */
    @ApiModelProperty(value = "操作发生时间")
    private String time24;
    /**
     * 设备激活记录ID
     */
    @ApiModelProperty(value = "设备激活记录ID")
    private String deviceId;
    /**
     * 用户登录的客户端应用ID
     */
    @ApiModelProperty(value = "用户登录的客户端应用ID")
    private String appId;
    /**
     * 用户登录的客户端应用渠道编码
     */
    @ApiModelProperty(value = "用户登录的客户端应用渠道编码")
    private String appChannel;
    /**
     * 用户登录的客户端版本
     */
    @ApiModelProperty(value = "用户登录的客户端版本")
    private String appVersion;

    /**
     * 操作结果： 0 ：成功 ；1：失败
     */
    @ApiModelProperty(value = "操作结果： 0 ：成功 ；1：失败")
    private Integer logType;

}
