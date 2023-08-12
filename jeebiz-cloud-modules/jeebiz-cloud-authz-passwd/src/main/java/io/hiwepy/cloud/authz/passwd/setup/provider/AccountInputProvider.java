package io.hiwepy.cloud.authz.passwd.setup.provider;

import io.hiwepy.boot.api.dao.entities.BaseMap;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;

/**
 * 账号数据提供者
 */
public interface AccountInputProvider {

    /**
     * 服务提供者名称
     */
    public String name();

    public BaseMap input(PwdRetakeDto dto);

}
