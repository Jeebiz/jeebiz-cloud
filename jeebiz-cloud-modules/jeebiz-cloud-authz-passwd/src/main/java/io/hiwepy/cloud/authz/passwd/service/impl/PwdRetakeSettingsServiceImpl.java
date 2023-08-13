package io.hiwepy.cloud.authz.passwd.service.impl;

import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.passwd.dao.IPwdRetakeSettingsDao;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeSettingsService;
import org.springframework.stereotype.Service;

@Service
public class PwdRetakeSettingsServiceImpl extends BaseServiceImpl<IPwdRetakeSettingsDao, PairModel>
        implements IPwdRetakeSettingsService {

}
