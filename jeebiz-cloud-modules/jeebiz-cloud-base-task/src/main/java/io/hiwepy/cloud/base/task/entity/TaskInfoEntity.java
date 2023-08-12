package io.hiwepy.cloud.base.task.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 任务信息表
 * </p>
 *
 * @author wandl
 * @since 2023-07-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_task_info")
public class TaskInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务主键ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 任务名称
     */
    @TableField("task_name")
    private String name;

    /**
     * 任务描述
     */
    @TableField("task_desc")
    private String desc;

    /**
     * 任务状态(0:未开始, 1:执行中, 2:成功, 3:失败, 4:取消, 4:已过期)
     */
    @TableField("status")
    private Integer status;

    /**
     * 任务对应的业务类型
     */
    @TableField("biz_type")
    private Integer bizType;

    /**
     * 任务对应的业务数据
     */
    @TableField("biz_data")
    private String bizData;

    /**
     * 文件存储地址
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 学校代码
     */
    @TableField("school_code")
    private String schoolCode;

    /**
     * 任务发起人
     */
    @TableField("user_id")
    private String userId;

    /**
     * 是否删除（0:未删除,1:已删除）
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 创建人ID
     */
    @TableField("creator")
    private Long creator;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @TableField("modifyer")
    private Long modifyer;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    private LocalDateTime modifyTime;
}
