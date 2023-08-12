/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.emums;

import io.hiwepy.boot.api.dao.entities.PairModel;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public enum InformEventType {

    LOGIN("login", "系统登录"),
    LOGOUT("logout", "注销登录");

    private String key;
    private String desc;

    private InformEventType(String key, String desc) {
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

    public boolean equals(InformEventType type) {
        return this.compareTo(type) == 0;
    }

    public boolean equals(String key) {
        return this.compareTo(InformEventType.valueOfIgnoreCase(key)) == 0;
    }

    public static InformEventType valueOfIgnoreCase(String key) {
        for (InformEventType optType : InformEventType.values()) {
            if (optType.getKey().equalsIgnoreCase(key)) {
                return optType;
            }
        }
        throw new NoSuchElementException("Cannot found InformEventType with key '" + key + "'.");
    }

    public PairModel toPair() {
        PairModel pair = new PairModel();
        pair.setKey(this.getKey());
        pair.setValue(this.getDesc());
        return pair;
    }

    public static List<PairModel> toList() {
        List<PairModel> pairList = new LinkedList<PairModel>();
        for (InformEventType eventType : InformEventType.values()) {
            pairList.add(eventType.toPair());
        }
        return pairList;
    }

}
