package io.hiwepy.cloud.base.task.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 新建下载任务BO
 */
@Data
@Accessors(chain = true)
public class TaskCreateBO implements Serializable {

    @ApiModelProperty(value = "学校代码", required = true)
    @NotBlank(message = "学校代码不能为空")
    private String schoolCode;

    @ApiModelProperty(value = "任务发起人ID")
    private String userId;

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


}
