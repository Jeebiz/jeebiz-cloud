package io.hiwepy.cloud.base.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 清理过期任务参数
 */
@Data
@Accessors(chain = true)
public class TaskExpiredCleanParam implements Serializable {

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
    private Long userId;
    /**
     * 任务对应的业务类型
     */
    @ApiModelProperty(value = "任务对应的业务类型")
    private Integer bizType;

}
