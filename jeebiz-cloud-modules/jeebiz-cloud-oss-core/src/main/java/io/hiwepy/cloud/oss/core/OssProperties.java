/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.oss.core;

import lombok.Data;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(OssProperties.PREFIX)
@Data
public class OssProperties {

    public static final String PREFIX = "oss";

    private Cache cache = new Cache();

    private Cloud cloud = new Cloud();

    private Local local = new Local();

    @Data
    public static class Cache {

        /**
         * 文件对象是否开启Redis缓存
         */
        private boolean enabled;

        /**
         * 文件对象Redis缓存有效期
         */
        private Duration timeout = Duration.ofDays(1);

        /**
         * 需要进行缓存的文件对象后缀，小图片可以缓存起来
         */
        private String[] extensions = new String[]{"jpg", "jpeg", "png", "gif", "bmp", "wbmp"};

    }

    @Data
    public static class Cloud {

        /**
         * 文件对象存储桶名称或一级目录名称
         */
        private String bucket;

    }

    @Data
    public static class Local {

        /**
         * 存储服务对外服务的主机地址或域名
         */
        private String endpoint;
        /**
         * 本地存储目录
         */
        private String userDir = SystemUtils.getUserDir().getAbsolutePath();

    }

}
