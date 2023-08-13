package io.hiwepy.cloud.base.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import hitool.core.lang3.time.DateFormats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 任务信息DTO
 */
@ApiModel(value = "TaskInfoDTO", description = "任务信息DTO")
@Data
@Accessors(chain = true)
public class TaskInfoDTO implements Serializable {

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
     * 任务状态(0:未开始, 1:执行中, 2:成功, 3:失败, 4:取消, 4:已过期)
     */
    @ApiModelProperty(value = "任务状态(0:未开始, 1:执行中, 2:成功, 3:失败, 4:取消, 4:已过期)")
    private Integer status;
    /**
     * 任务对应的业务类型
     */
    @ApiModelProperty(value = "任务对应的业务类型")
    private Integer bizType;
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
    /**
     * 学校代码
     */
    @ApiModelProperty(value = "学校代码")
    private String schoolCode;
    /**
     * 任务发起人
     */
    @ApiModelProperty(value = "任务发起人")
    private String userId;
    /**
     * 是否删除（0:未删除,1:已删除）
     */
    @ApiModelProperty(value = "是否删除（0:未删除,1:已删除）")
    private Integer isDelete;
    /**
     * 创建人ID
     */
    @ApiModelProperty(value = "创建人ID")
    private Long creator;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = DateFormats.DATE_LONGFORMAT, timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 修改人ID
     */
    @ApiModelProperty(value = "修改人ID")
    private Long modifyer;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = DateFormats.DATE_LONGFORMAT, timezone = "GMT+8")
    private LocalDateTime modifyTime;

}
