/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.task;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Objects;

@ConfigurationProperties(TaskProperties.PREFIX)
@Data
public class TaskProperties {

    public static final String PREFIX = "task";

    /**
     * 任务缓存过期时间
     */
    private Duration duration = Duration.ofDays(1);

    public long getTaskStatusExpireTime() {
        return Objects.nonNull(duration) ? duration.getSeconds() : Duration.ofDays(1).getSeconds();
    }

}
