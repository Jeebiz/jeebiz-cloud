package io.hiwepy.cloud.authz.passwd.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.passwd.dao.IPwdRetakeStrategyDao;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeStrategyModel;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeStrategyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PwdRetakeStrategyServiceImpl extends BaseServiceImpl<IPwdRetakeStrategyDao, PwdRetakeStrategyModel>
        implements IPwdRetakeStrategyService {

    @Override
    public List<PwdRetakeStrategyModel> getStrategyList() {
        return getBaseMapper().getStrategyList();
    }

}
