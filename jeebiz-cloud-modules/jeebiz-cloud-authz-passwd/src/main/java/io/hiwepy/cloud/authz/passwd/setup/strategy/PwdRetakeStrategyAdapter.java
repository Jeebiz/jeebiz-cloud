package io.hiwepy.cloud.authz.passwd.setup.strategy;

import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeField;
import io.hiwepy.cloud.authz.passwd.setup.provider.AccountInputProvider;

/**
 * 密码找回策略Adapter
 */
public abstract class PwdRetakeStrategyAdapter implements PwdRetakeStrategy {

    protected AccountInputProvider accountProvider;
    protected PwdRetakeField[] bindFields;

    public PwdRetakeStrategyAdapter() {
    }

    public void setBindFields(PwdRetakeField... bindFields) {
        this.bindFields = bindFields;
    }

    public AccountInputProvider getAccountProvider() {
        return accountProvider;
    }

    public void setAccountProvider(AccountInputProvider accountProvider) {
        this.accountProvider = accountProvider;
    }

}
