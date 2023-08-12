package io.hiwepy.cloud.base.guard.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "AntisamyDTO", description = "消息通知传输对象")
public class AntisamyDTO {

    /**
     * 消息通知ID编号
     */
    @ApiModelProperty(value = "id", dataType = "String", notes = "消息通知ID编号")
    private String id;
    /**
     * 消息通知通知对象ID
     */
    @ApiModelProperty(value = "userid", dataType = "String", notes = "消息通知对象ID")
    private String userid;
    /**
     * 消息通知标题
     */
    @ApiModelProperty(value = "title", required = true, dataType = "String", notes = "消息通知标题")
    @NotBlank(message = "消息通知标题必填")
    private String title;
    /**
     * 消息通知内容
     */
    @ApiModelProperty(value = "detail", required = true, dataType = "String", notes = "消息通知内容")
    @NotBlank(message = "消息通知内容必填")
    private String detail;
    /**
     * 消息通知阅读状态：0:未阅读|1:已阅读
     */
    @ApiModelProperty(value = "status", dataType = "String", notes = "消息通知阅读状态：0:未阅读|1:已阅读", allowableValues = "0,1")
    private String status;
    /**
     * 消息通知送达时间
     */
    private String timestamp;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
