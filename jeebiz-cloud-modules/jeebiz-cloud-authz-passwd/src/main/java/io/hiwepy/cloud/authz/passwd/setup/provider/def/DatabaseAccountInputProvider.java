package io.hiwepy.cloud.authz.passwd.setup.provider.def;

import io.hiwepy.boot.api.dao.entities.BaseMap;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeAccountService;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;
import io.hiwepy.cloud.authz.passwd.setup.provider.AccountInputProvider;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于数据库存储的账号信息提供实现
 */
public class DatabaseAccountInputProvider implements AccountInputProvider {

    protected Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected final IPwdRetakeAccountService pwdRetakeAccountService;

    public DatabaseAccountInputProvider(IPwdRetakeAccountService pwdRetakeAccountService) {
        super();
        this.pwdRetakeAccountService = pwdRetakeAccountService;
    }

    @Override
    public String name() {
        return PwdStrategy.DEFAULT_STRATEGY;
    }

    @Override
    public BaseMap input(PwdRetakeDto data) {
        return getPwdRetakeAccountService().getAccount(data);
    }

    public IPwdRetakeAccountService getPwdRetakeAccountService() {
        return pwdRetakeAccountService;
    }

}
