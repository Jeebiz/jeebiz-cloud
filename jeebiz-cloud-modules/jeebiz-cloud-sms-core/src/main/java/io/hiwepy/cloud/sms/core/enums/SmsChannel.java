package io.hiwepy.cloud.sms.core.enums;

import java.util.NoSuchElementException;

public enum SmsChannel {

    ALIYUN(1, 5, 10, "阿里云短信"),
    TXCLOUD(1, 5, 10, "腾讯云短信"),

    ;
    private int timesOfMinute;
    private int timesOfHour;
    private int timesOfDay;
    private String desc;

    public int getTimesOfMinute() {
        return timesOfMinute;
    }

    public int getTimesOfHour() {
        return timesOfHour;
    }

    public int getTimesOfDay() {
        return timesOfDay;
    }

    public String getDesc() {
        return desc;
    }

    private SmsChannel(int timesOfMinute, int timesOfHour, int timesOfDay, String desc) {
        this.timesOfMinute = timesOfMinute;
        this.timesOfHour = timesOfHour;
        this.timesOfDay = timesOfDay;
        this.desc = desc;
    }

    public boolean equals(SmsChannel channel) {
        return this.compareTo(channel) == 0;
    }

    public boolean equals(String type) {
        return this.compareTo(SmsChannel.valueOfIgnoreCase(type)) == 0;
    }

    public static SmsChannel valueOfIgnoreCase(String key) {
        for (SmsChannel channel : SmsChannel.values()) {
            if (channel.name().equals(key)) {
                return channel;
            }
        }
        throw new NoSuchElementException("Cannot found Sms Channel with key '" + key + "'.");
    }

}