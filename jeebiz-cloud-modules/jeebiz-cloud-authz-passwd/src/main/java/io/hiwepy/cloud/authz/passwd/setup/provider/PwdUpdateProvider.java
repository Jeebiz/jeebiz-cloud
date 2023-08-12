package io.hiwepy.cloud.authz.passwd.setup.provider;

import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeResult;

/**
 * 密码修改服务接口
 */
public interface PwdUpdateProvider {

    /**
     * 服务提供者名称
     */
    public String name();

    public Object get(PwdRetakeDto data);

    public PwdRetakeResult update(PwdRetakeDto dto);

}
