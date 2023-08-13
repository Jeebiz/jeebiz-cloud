package io.hiwepy.cloud.authz.passwd.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeCaptchaModel;
import org.springframework.stereotype.Service;

@Service
public interface IPwdRetakeCaptchaService extends IBaseService<PwdRetakeCaptchaModel> {

    String getDatetime();


    PwdRetakeCaptchaModel getCaptcha(String uuid);

}
