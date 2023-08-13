package io.hiwepy.cloud.base.task.dto;

import io.hiwepy.boot.api.dto.AbstractPaginationDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 任务信息分页查询DTO
 */
@ApiModel(value = "TaskPaginationDTO", description = "任务信息分页查询DTO")
@Data
public class TaskPaginationDTO extends AbstractPaginationDTO {

    /**
     * 学校代码
     */
    @ApiModelProperty(value = "学校代码", required = true)
    @NotBlank(message = "学校代码不能为空")
    private String schoolCode;
    /**
     * 任务发起人ID
     */
    @ApiModelProperty(value = "任务发起人ID")
    private String userId;
    /**
     * 业务类型
     */
    @ApiModelProperty(value = "业务类型")
    @NotNull(message = "业务类型不能为空")
    private Integer bizType;
    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态")
    private Integer status;

}
