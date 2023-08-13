package io.hiwepy.cloud.authz.passwd.service;

import io.hiwepy.boot.api.dao.entities.BaseMap;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;

import java.util.Map;

public interface IPwdRetakeAccountService extends IBaseService<PairModel> {

    /**
     * 通过页面绑定的参数查询用户信息
     */
    public BaseMap getAccount(PwdRetakeDto dto);

    public boolean resetPwd(Map<String, Object> data);

}
