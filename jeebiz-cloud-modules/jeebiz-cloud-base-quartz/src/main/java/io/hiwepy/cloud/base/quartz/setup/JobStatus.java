/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.quartz.setup;

/**
 * 任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败）
 */
public enum JobStatus {

    /**
     * 0:待执行
     */
    WAITING("0", "待执行"),
    /**
     * 1:运行中
     */
    RUNNING("1", "运行中"),
    /**
     * 2:暂停中
     */
    PAUSED("2", "暂停中"),
    /**
     * 3:被锁定
     */
    BLOCKED("3", "被锁定"),
    /**
     * 4:已完成
     */
    COMPLETE("4", "已完成"),
    /**
     * 5:失败
     */
    ERROR("5", "失败");

    private final String code;
    private final String value;

    private JobStatus(String code, String value) {
        this.value = value;
        this.code = code;
    }

    public static JobStatus of(String key) {
        for (JobStatus e : JobStatus.values()) {
            if (e.getCode().equals(key)) {
                return e;
            }
        }
        return null;
    }

    /** 获取value */
    public String getValue() {
        return value;
    }

    /** 获取code */
    public String getCode() {
        return code;
    }

}
