package io.hiwepy.cloud.base.task.dto;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 新建任务DTO
 */
@Data
@Accessors(chain = true)
public class TaskCreateDTO implements Serializable {

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
    @NotBlank(message = "任务发起人ID不能为空")
    private String userId;
    /**
     * 任务名称（不传则后台自动拼装）
     */
    @ApiModelProperty(value = "任务名称（不传则后台自动拼装）")
    private String name;
    /**
     * 任务描述（不传则为空）
     */
    @ApiModelProperty(value = "任务描述（不传则为空）")
    private String desc;
    /**
     * 任务对应的业务类型
     */
    @ApiModelProperty(value = "任务对应的业务类型")
    private Integer bizType;
    /**
     * 任务参数
     */
    @ApiModelProperty(value = "任务参数")
    private JSONObject params;

}
