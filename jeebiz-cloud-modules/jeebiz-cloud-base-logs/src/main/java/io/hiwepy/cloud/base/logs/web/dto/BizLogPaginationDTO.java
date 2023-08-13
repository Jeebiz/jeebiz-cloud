/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.web.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 功能操作数据筛选条件DTO
 */
@ApiModel(value = "BizLogPaginationDTO", description = "功能操作数据筛选条件DTO")
@Getter
@Setter
@ToString
public class BizLogPaginationDTO extends AbstractPaginationDTO {

    /**
     * 认证对象角色
     */
    @ApiModelProperty(name = "roleId", value = "认证对象角色")
    private String roleId;
    /**
     * 功能模块
     */
    @ApiModelProperty(name = "module", value = "功能模块")

    private String module;
    /**
     * 业务名称
     */
    @ApiModelProperty(name = "business", value = "业务名称")
    private String business;
    /**
     * 操作类型
     */
    @ApiModelProperty(name = "opt", value = "操作类型", allowableValues = "login,logout,insert,delete,update,select,upload,download")
    private String opt;
    /**
     * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
     */
    @ApiModelProperty(name = "level", value = "日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）", allowableValues = "debug,info,warn,error,fetal")
    private String level;
    /**
     * 功能操作请求来源IP地址
     */
    @ApiModelProperty(name = "addr", value = "功能操作请求来源IP地址")
    private String addr;
    /**
     * 功能操作发生起始时间
     */
    @ApiModelProperty(name = "beginTime", value = "开始日期时间 YYYY-mm-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate beginDate;
    /**
     * 功能操作发生结束时间
     */
    @ApiModelProperty(name = "endTime", value = "结束日期时间 YYYY-mm-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;
    /**
     * 关键词搜索
     */
    @ApiModelProperty(name = "keywords", value = "关键词")
    private String keywords;

}
