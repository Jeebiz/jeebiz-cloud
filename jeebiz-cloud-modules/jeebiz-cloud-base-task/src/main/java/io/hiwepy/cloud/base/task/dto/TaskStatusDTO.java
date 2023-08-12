package io.hiwepy.cloud.base.task.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 任务状态DTO
 */
@ApiModel(value = "TaskStatusDTO", description = "任务状态DTO")
@Data
@Accessors(chain = true)
public class TaskStatusDTO implements Serializable {

    /**
     * 任务主键ID
     */
    @ApiModelProperty(value = "任务主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 任务状态(0:未开始, 1:执行中, 2:成功, 3:失败, 4:取消, 4:已过期)
     */
    @ApiModelProperty(value = "任务状态(0:未开始, 1:执行中, 2:成功, 3:失败, 4:取消, 4:已过期)")
    private Integer status;
    /**
     * 待处理记录数（批量操作的任务中的待处理记录数）
     */
    @ApiModelProperty(value = "待处理记录数（批量操作的任务中的待处理记录数）")
    private Integer pending;
    /**
     * 处理完成记录数（批量操作的任务中的处理完成记录数）
     */
    @ApiModelProperty(value = "处理完成记录数（批量操作的任务中的处理完成记录数）")
    private Integer completed;
    /**
     * 总记录数（批量操作的任务中的总记录数）
     */
    @ApiModelProperty(value = "总记录数（批量操作的任务中的总记录数）")
    private Integer total;
    /**
     * 任务对应的业务数据
     */
    @ApiModelProperty(value = "任务对应的业务数据")
    private String bizData;
    /**
     * 文件存储地址
     */
    @ApiModelProperty(value = "文件存储地址")
    private String filePath;

}
