/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.strategy;

import java.util.*;

/**
 *  日志输出渠道
 */
public enum LogWriteChannel {

    database("DB存储（默认）"),
    rabbitmq("RabbitMQ 消息队列"),
    rocketmq("RocketMQ 消息队列"),
    kafka("Kafka 消息队列"),
    flume("Flume 日志采集"),
    slf4j("日志组件（Slf4j）"),
    log4j("日志组件（Log4j）"),
    logback("日志组件（Logback）"),
    fastdfs("Logstash 日志采集"),

    ;

    private String desc;

    private LogWriteChannel(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean equals(LogWriteChannel type) {
        return this.compareTo(type) == 0;
    }

    public boolean equals(String name) {
        return this.compareTo(LogWriteChannel.valueOfIgnoreCase(name)) == 0;
    }

    public static LogWriteChannel valueOfIgnoreCase(String name) {
        for (LogWriteChannel optType : LogWriteChannel.values()) {
            if (optType.name().equalsIgnoreCase(name)) {
                return optType;
            }
        }
        throw new NoSuchElementException("Cannot found LogWriteChannel with name '" + name + "'.");
    }

    public static List<Map<String, String>> toList() {
        List<Map<String, String>> optList = new LinkedList<Map<String, String>>();
        for (LogWriteChannel optEnum : LogWriteChannel.values()) {
            optList.add(optEnum.toMap());
        }
        return optList;
    }

    public Map<String, String> toMap() {
        Map<String, String> optMap = new HashMap<String, String>();
        optMap.put("key", this.name());
        optMap.put("desc", this.getDesc());
        return optMap;
    }

}
