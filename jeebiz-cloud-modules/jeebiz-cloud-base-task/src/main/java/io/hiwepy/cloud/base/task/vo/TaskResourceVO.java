package io.hiwepy.cloud.base.task.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 任务资源VO
 */
@ApiModel(value = "TaskResourceVO", description = "任务资源VO")
@Data
@Accessors(chain = true)
public class TaskResourceVO implements Serializable {

    /**
     * 文件访问地址
     */
    @ApiModelProperty(value = "文件访问地址")
    private String fileUrl;

}
