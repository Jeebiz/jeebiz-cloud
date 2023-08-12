package io.hiwepy.cloud.authz.passwd.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.passwd.dao.IPwdRetakeCaptchaDao;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeCaptchaModel;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeCaptchaService;
import org.springframework.stereotype.Service;

@Service
public class PwdRetakeCaptchaServiceImpl extends BaseServiceImpl<IPwdRetakeCaptchaDao, PwdRetakeCaptchaModel>
        implements IPwdRetakeCaptchaService {

    @Override
    public String getDatetime() {
        return getBaseMapper().getDatetime();
    }

    @Override
    public PwdRetakeCaptchaModel getCaptcha(String uuid) {
        return getBaseMapper().getCaptcha(uuid);
    }

}
