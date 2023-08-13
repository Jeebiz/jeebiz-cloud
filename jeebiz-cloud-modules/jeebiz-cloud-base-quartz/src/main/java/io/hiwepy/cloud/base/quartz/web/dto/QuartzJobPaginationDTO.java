package io.hiwepy.cloud.base.quartz.web.dto;


import io.hiwepy.boot.api.annotation.AllowableValues;
import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "QuartzJobPaginationDTO", description = "定时任务分页查询参数DTO")
@Getter
@Setter
@ToString
public class QuartzJobPaginationDTO extends AbstractPaginationDTO {

    /**
     * 业务记录ID编号
     */
    @ApiModelProperty(name = "bizId", dataType = "String", value = "业务记录ID编号")

    private String bizId;
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
     * 任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败）", allowableValues = "0,1,2,3,4,5")
    @AllowableValues(allows = "0,1,2,3,4,5", message = "任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败）", nullable = true)

    private String status;

}
