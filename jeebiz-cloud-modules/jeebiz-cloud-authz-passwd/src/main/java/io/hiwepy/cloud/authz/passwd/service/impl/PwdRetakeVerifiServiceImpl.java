package io.hiwepy.cloud.authz.passwd.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.passwd.dao.IPwdRetakeVerifiDao;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeVerifiModel;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeVerifiService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PwdRetakeVerifiServiceImpl extends BaseServiceImpl<IPwdRetakeVerifiDao, PwdRetakeVerifiModel>
        implements IPwdRetakeVerifiService {

    @Override
    public List<PwdRetakeVerifiModel> getVerifiList() {
        return getBaseMapper().getVerifiList();
    }

}
