package io.hiwepy.cloud.authz.passwd.service.impl;

import io.hiwepy.boot.api.dao.entities.BaseMap;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.provider.AccountProvider;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.passwd.dao.IPwdRetakeAccountDao;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeAccountService;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PwdRetakeAccountServiceImpl extends BaseServiceImpl<IPwdRetakeAccountDao, PairModel> implements IPwdRetakeAccountService {

    @Autowired(required = false)
    protected AccountProvider accountProvider;

    private boolean isProvider() {
        return accountProvider != null;
    }

    @Override
    public BaseMap getAccount(PwdRetakeDto dto) {

        // 查询用户状态
        boolean hasAcc = isProvider() ? getAccountProvider().hasAccount(dto.getUsername()) : getBaseMapper().getCountByUid(dto.getUsername()) == 1;
        // 账号存在
        if (hasAcc) {
            //处理原始数据
            Map<String, Object> map = new HashMap<String, Object>(dto.getData());
            map.put("username", dto.getUsername());
            //查询用户信息
            BaseMap baseMap = isProvider() ? getAccountProvider().getAccount(map) : getBaseMapper().getAccount(map);
            if (baseMap != null) {
                baseMap.put("username", dto.getUsername());
            }
            return baseMap;
        }
        //无效的用户
        else {
            return null;
        }
    }

    @Override
    public boolean resetPwd(Map<String, Object> data) {
        if (isProvider()) {
            getAccountProvider().resetPwd(data);
        } else {
            getBaseMapper().resetPwd(data);
        }
        return true;
    }

    public AccountProvider getAccountProvider() {
        return accountProvider;
    }

    public void setAccountProvider(AccountProvider accountProvider) {
        this.accountProvider = accountProvider;
    }

}
