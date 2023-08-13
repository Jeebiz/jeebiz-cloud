package io.hiwepy.cloud.authz.passwd.setup.provider;

import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeTime;

/**
 * 日期时间提供接口
 */
public interface DatetimeProvider {

    /**
     * 服务提供者名称
     */
    public String name();

    public PwdRetakeTime dateTime(String format, int effectTime);

}
