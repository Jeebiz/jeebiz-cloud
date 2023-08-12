/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.core.enums;

import java.util.*;

/**
 *  存储目标（local:服务本地,fdfs:FastDFS存储服务,minio:MinIO对象存储）
 */
public enum OssChannel {

    OSS_DATABASE("oss-db", "DB存储（数据库）"),
    OSS_LOCAL("oss-local", "本地目录"),
    OSS_NFS("oss-nfs", "文件共享（NFS协议）"),
    OSS_WEBDAV("oss-webdav", "文件共享（WebDAV协议）"),
    OSS_SFTP("oss-sftp", "文件共享（FTP协议）"),
    OSS_SAMBA("oss-samba", "文件共享（Samba协议）"),
    OSS_FASTDFS("oss-fastdfs", "FastDFS"),
    OSS_ALIYUN("oss-aliyun", "对象存储（阿里云）"),
    OSS_TENCENT("oss-tencent", "对象存储（腾讯云）"),
    OSS_BAIDU("oss-baidu", "对象存储（百度云）"),
    OSS_HUAWEI("oss-huawei", "对象存储（华为云）"),
    OSS_MINIO("oss-minio", "对象存储（Minio）");

    private String key;
    private String desc;

    private OssChannel(String key, String desc) {
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

    public boolean equals(OssChannel type) {
        return this.compareTo(type) == 0;
    }

    public boolean equals(String key) {
        return this.compareTo(OssChannel.valueOfIgnoreCase(key)) == 0;
    }

    public static OssChannel valueOfIgnoreCase(String key) {
        for (OssChannel optType : OssChannel.values()) {
            if (optType.getKey().equalsIgnoreCase(key)) {
                return optType;
            }
        }
        throw new NoSuchElementException("Cannot found AuthzOptEnum with key '" + key + "'.");
    }

    public static List<Map<String, String>> toList() {
        List<Map<String, String>> optList = new LinkedList<Map<String, String>>();
        for (OssChannel optEnum : OssChannel.values()) {
            optList.add(optEnum.toMap());
        }
        return optList;
    }

    public Map<String, String> toMap() {
        Map<String, String> optMap = new HashMap<String, String>();
        optMap.put("key", this.getKey());
        optMap.put("desc", this.getDesc());
        return optMap;
    }

}
