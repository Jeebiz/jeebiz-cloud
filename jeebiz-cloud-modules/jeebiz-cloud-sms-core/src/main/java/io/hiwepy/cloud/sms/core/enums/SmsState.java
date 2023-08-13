package io.hiwepy.cloud.sms.core.enums;

import java.util.NoSuchElementException;

public enum SmsState {

    WAITING(0, "待发送"),
    SENDING(1, "发送中"),
    RETRYING(2, "重试中"),
    SUCCESS(3, "发送完成"),
    FAIL(4, "发送失败"),

    ;
    private int state;
    private String desc;

    public int getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }

    private SmsState(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public boolean equals(SmsState channel) {
        return this.compareTo(channel) == 0;
    }

    public boolean equals(String type) {
        return this.compareTo(SmsState.valueOfIgnoreCase(type)) == 0;
    }

    public static SmsState valueOfIgnoreCase(String key) {
        for (SmsState channel : SmsState.values()) {
            if (channel.name().equals(key)) {
                return channel;
            }
        }
        throw new NoSuchElementException("Cannot found Sms State with key '" + key + "'.");
    }

}