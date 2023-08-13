package io.hiwepy.cloud.base.quartz.dao.entities;

import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias(value = "QuartzJobModel")
@Getter
@Setter
@ToString
public class QuartzJobModel extends PaginationEntity<QuartzJobModel> {

    /**
     * 业务记录ID编号
     */
    private String bizId;
    /**
     * 任务记录ID编号
     */
    private String id;
    /**
     * 任务分组
     */
    private String group;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务描述
     */
    private String intro;
    /**
     * 任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败|6:删除）
     */
    private String status;
    /**
     * 任务cron表达式
     */
    private String cron;
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    private String clazz;
    /**
     * 任务延时
     */
    private long delay;
    /**
     * 任务周期
     */
    private long period;
    /**
     * 任务开始时间
     */
    private String start;
    /**
     * 任务创建时间
     */
    private String time24;

}
