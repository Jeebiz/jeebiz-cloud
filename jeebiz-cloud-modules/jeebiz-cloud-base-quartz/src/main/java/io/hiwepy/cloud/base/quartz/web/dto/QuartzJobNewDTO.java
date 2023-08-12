package io.hiwepy.cloud.base.quartz.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "QuartzJobNewDTO", description = "新增定时任务参数DTO")
@Getter
@Setter
@ToString
public class QuartzJobNewDTO {

    /**
     * 业务记录ID编号
     */
    @ApiModelProperty(name = "bizId", required = true, dataType = "String", value = "业务记录ID编号")
    @NotBlank(message = "业务记录ID编号必填")
    private String bizId;
    /**
     * 任务分组
     */
    @ApiModelProperty(name = "group", dataType = "String", value = "任务分组")
    @NotBlank(message = "任务分组必填")
    private String group;
    /**
     * 任务名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "任务名称")
    @NotBlank(message = "任务名称必填")
    private String name;
    /**
     * 任务描述
     */
    @ApiModelProperty(name = "intro", dataType = "String", value = "任务描述")
    @NotBlank(message = "任务描述必填")
    private String intro;
    /**
     * 任务cron表达式
     */
    @ApiModelProperty(name = "cron", dataType = "String", value = "任务cron表达式")
    @NotBlank(message = "任务cron表达式必填")
    private String cron;

}
