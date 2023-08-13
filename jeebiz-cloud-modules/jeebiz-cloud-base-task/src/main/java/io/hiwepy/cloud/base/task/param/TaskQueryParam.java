package io.hiwepy.cloud.base.task.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 任务查询参数
 * </p>
 *
 * @author wandl
 * @since 2023.07.26
 */
@Data
@ApiOperation("任务查询参数")
public class TaskQueryParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学校代码", required = true)
    @NotBlank(message = "学校代码不能为空")
    private String schoolCode;

    @ApiModelProperty(value = "任务发起人ID")
    private String userId;

    @ApiModelProperty(value = "业务类型")
    @NotNull(message = "业务类型不能为空")
    private Integer bizType;

    @ApiModelProperty(value = "任务状态")
    private Integer status;

    @ApiModelProperty(value = "当前页码")
    private Integer pageNum;

    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;

}
