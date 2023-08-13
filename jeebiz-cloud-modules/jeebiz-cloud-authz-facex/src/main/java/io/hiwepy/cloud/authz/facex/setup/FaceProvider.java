/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.setup;

import io.hiwepy.boot.api.dao.entities.PairModel;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 人脸识别提供者
 */
public enum FaceProvider {

    /**
     * 默认人脸识别
     */
    UNKNOWN("默认人脸识别"),
    /**
     * 虹软离线人脸识别
     */
    ARCSOFT("虹软离线人脸识别"),
    /**
     * OpenCv人脸识别
     */
    OPENCV("OpenCv人脸识别"),
    /**
     * 海康威视人脸识别
     */
    HIKVISION("海康威视人脸识别"),
    /**
     * 阿里云人脸识别
     */
    ALIYUN("阿里云人脸识别"),
    /**
     * 百度云人脸识别
     */
    BAIDU("百度云人脸识别"),
    /**
     * 腾讯云人脸识别
     */
    TENCENT("腾讯云人脸识别"),
    /**
     * 华为云人脸识别
     */
    HUAWEI("华为云人脸识别");

    private String provider;

    private FaceProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public boolean equals(FaceProvider provider) {
        return this.compareTo(provider) == 0;
    }

    public boolean equals(String provider) {
        return this.compareTo(FaceProvider.valueOfIgnoreCase(provider)) == 0;
    }

    public static FaceProvider valueOfIgnoreCase(String provider) {
        for (FaceProvider providerEnum : FaceProvider.values()) {
            if (providerEnum.name().equals(provider)) {
                return providerEnum;
            }
        }
        throw new NoSuchElementException("Cannot found Thirdparty with provider '" + provider + "'.");
    }

    public PairModel toPair() {
        PairModel pair = new PairModel();
        pair.setKey(this.name());
        pair.setValue(this.getProvider());
        return pair;
    }

    public static List<PairModel> toList() {
        List<PairModel> providerList = new LinkedList<PairModel>();
        for (FaceProvider provider : FaceProvider.values()) {
            providerList.add(provider.toPair());
        }
        return providerList;
    }

}