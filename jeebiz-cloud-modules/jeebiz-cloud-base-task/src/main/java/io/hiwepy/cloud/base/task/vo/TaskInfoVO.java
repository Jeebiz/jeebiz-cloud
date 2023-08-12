package io.hiwepy.cloud.base.task.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 任务信息VO
 */
@ApiModel(value = "TaskInfoVO", description = "任务信息VO")
@Data
@Accessors(chain = true)
public class TaskInfoVO implements Serializable {

    /**
     * 任务主键ID
     */
    @ApiModelProperty(value = "任务主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
    private String name;
    /**
     * 任务描述
     */
    @ApiModelProperty(value = "任务描述")
    private String desc;
    /**
     * 任务对应的业务类型
     */
    @ApiModelProperty(value = "任务对应的业务类型")
    private Integer bizType;
    /**
     * 任务状态(0:未开始, 1:执行中, 2:成功, 3:失败, 4:取消, 4:已过期)
     */
    @ApiModelProperty(value = "任务状态(0:未开始, 1:执行中, 2:成功, 3:失败, 4:取消, 4:已过期)")
    private Integer status;
    /**
     * 文件访问地址
     */
    @ApiModelProperty(value = "文件访问地址")
    private String fileUrl;
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
     * 任务创建时间
     */
    @ApiModelProperty(value = "任务创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
