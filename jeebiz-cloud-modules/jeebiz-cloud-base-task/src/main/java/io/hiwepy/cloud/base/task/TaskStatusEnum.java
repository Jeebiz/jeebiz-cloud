package io.hiwepy.cloud.base.task;

/**
 * 任务状态(0:未开始, 1:执行中, 2:成功, 3:失败, 4:取消, 4:已过期)
 */
public enum TaskStatusEnum {

    NOT_STARTED(0, "未开始"),
    EXECUTING(1, "执行中"),
    SUCCESS(2, "成功"),
    FAILURE(3, "失败"),
    CANCEL(4, "取消"),
    EXPIRED(5, "已过期")
    ;

    private int status;
    private String desc;

    TaskStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

}
