package io.hiwepy.cloud.base.quartz.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "QuartzJobDTO", description = "定时任务信息DTO")
@Getter
@Setter
@ToString
public class QuartzJobDTO {

    /**
     * 业务记录ID编号
     */
    @ApiModelProperty(name = "bizId", dataType = "String", value = "业务记录ID编号")
    private String bizId;
    /**
     * 任务记录ID编号
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "任务记录ID编号")
    private String id;
    /**
     * 任务分组
     */
    @ApiModelProperty(name = "group", dataType = "String", value = "任务分组")
    private String group;
    /**
     * 任务名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "任务名称")
    private String name;
    /**
     * 任务描述
     */
    @ApiModelProperty(name = "intro", dataType = "String", value = "任务描述")
    private String intro;
    /**
     * 任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败|6:删除）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败|6:删除）")
    private String status;
    /**
     * 任务cron表达式
     */
    @ApiModelProperty(name = "cron", dataType = "String", value = "任务cron表达式")
    private String cron;
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    @ApiModelProperty(name = "clazz", dataType = "String", value = "任务执行时调用哪个类的方法 包名+类名")
    private String clazz;
    /**
     * 任务创建时间
     */
    @ApiModelProperty(name = "time24", dataType = "String", value = "任务创建时间")
    private String time24;

}
