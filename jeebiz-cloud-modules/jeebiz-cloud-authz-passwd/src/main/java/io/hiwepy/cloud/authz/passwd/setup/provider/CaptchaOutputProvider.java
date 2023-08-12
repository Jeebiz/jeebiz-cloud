package io.hiwepy.cloud.authz.passwd.setup.provider;

import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;

public interface CaptchaOutputProvider {

    /**
     * 服务提供者名称
     */
    public String name();

    public boolean output(PwdRetakeDto dto) throws Exception;

}
