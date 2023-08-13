package io.hiwepy.cloud.base.quartz.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "TimerJobNewDTO", description = "新增任务参数DTO")
@Getter
@Setter
@ToString
public class TimerJobNewDTO {

    /**
     * 业务记录ID编号
     */
    @ApiModelProperty(name = "bizId", required = true, dataType = "String", value = "业务记录ID编号")
    @NotBlank(message = "业务记录ID编号必填")
    private String bizId;
    /**
     * 任务分组
     */
    @ApiModelProperty(name = "group", required = true, dataType = "String", value = "任务分组")
    @NotBlank(message = "任务分组必填")
    private String group;
    /**
     * 任务名称
     */
    @ApiModelProperty(name = "name", required = true, dataType = "String", value = "任务名称")
    @NotBlank(message = "任务名称必填")
    private String name;
    /**
     * 任务描述
     */
    @ApiModelProperty(name = "intro", required = true, dataType = "String", value = "任务描述")
    @NotBlank(message = "任务描述必填")
    private String intro;
    /**
     * 任务开始时间
     */
    @ApiModelProperty(name = "start", required = true, dataType = "String", value = "任务开始时间")
    @NotBlank(message = "任务开始时间必填")
    private String start;
    /**
     * 任务延时
     */
    @ApiModelProperty(name = "delay", dataType = "String", value = "任务延时")
    private long delay;
    /**
     * 任务周期
     */
    @ApiModelProperty(name = "period", dataType = "String", value = "任务周期")
    private long period;

}
