/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.web.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 认证授权日志数据筛选条件DTO
 */
@ApiModel(value = "AuthzLogPaginationDTO", description = "认证授权日志数据筛选条件DTO")
@Data
public class AuthzLogPaginationDTO extends AbstractPaginationDTO {

    /**
     * 认证对象角色
     */
    @ApiModelProperty(name = "roleId", dataType = "String", value = "认证对象角色")
    private String roleId;
    /**
     * 认证授权类型（login:登录认证、logout:会话注销）
     */
    @ApiModelProperty(name = "opt", dataType = "String", value = "认证授权类型（login:登录认证、logout:会话注销）", allowableValues = "login,logout")
    private String opt;
    /**
     * 认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等
     */
    @ApiModelProperty(name = "protocol", dataType = "String", value = "认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等")
    private String protocol;
    /**
     * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
     */
    @ApiModelProperty(name = "level", dataType = "String", value = "日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）", allowableValues = "debug,info,warn,error,fetal")
    private String level;
    /**
     * 认证请求来源IP地址
     */
    @ApiModelProperty(name = "addr", dataType = "String", value = "认证请求来源IP地址", allowableValues = "1,0")
    private String addr;
    /**
     * 认证授权结果：（fail:失败、success:成功）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "认证授权结果：（fail:失败、success:成功）", allowableValues = "fail,success")
    private String status;
    /**
     * 开始日期时间 YYYY-mm-dd
     */
    @ApiModelProperty(name = "beginTime", value = "开始日期时间 YYYY-mm-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate beginDate;
    /**
     * 结束日期时间 YYYY-mm-dd
     */
    @ApiModelProperty(name = "endTime", value = "结束日期时间 YYYY-mm-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;
    /**
     * 关键词搜索
     */
    @ApiModelProperty(name = "keywords", dataType = "String", value = "关键词")
    private String keywords;

}
