package io.hiwepy.cloud.base.logs.web.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 扩展字段类
 */

@Data
public class ExtendPropertyModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 间隔时间
     */
    private Integer intervalTime;

    /**
     * 过期时间
     */
    private Integer expireTime;

    /**
     * 等待时间
     */
    private Integer waitTime;
}
