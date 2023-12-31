/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.enums;

import java.util.*;

/**
 * 日志级别
 */
public enum LoggerLevelEnum {

    DEBUG("debug", "调试"),
    INFO("info", "信息"),
    WARN("warn", "警告"),
    ERROR("error", "错误"),
    FETAL("fetal", "严重错误");

    private String key;
    private String desc;

    private LoggerLevelEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean equals(LoggerLevelEnum type) {
        return this.compareTo(type) == 0;
    }

    public boolean equals(String key) {
        return this.compareTo(LoggerLevelEnum.valueOfIgnoreCase(key)) == 0;
    }

    public static LoggerLevelEnum valueOfIgnoreCase(String key) {
        for (LoggerLevelEnum bizApiType : LoggerLevelEnum.values()) {
            if (bizApiType.getKey().equalsIgnoreCase(key)) {
                return bizApiType;
            }
        }
        throw new NoSuchElementException("Cannot found ServiceLoggerLevelEnum with key '" + key + "'.");
    }

    public static List<Map<String, String>> toList() {
        List<Map<String, String>> componentList = new LinkedList<Map<String, String>>();
        for (LoggerLevelEnum omponentEnum : LoggerLevelEnum.values()) {
            componentList.add(omponentEnum.toMap());
        }
        return componentList;
    }

    public Map<String, String> toMap() {
        Map<String, String> omponentMap = new HashMap<String, String>();
        omponentMap.put("key", this.getKey());
        omponentMap.put("desc", this.getDesc());
        return omponentMap;
    }

}
