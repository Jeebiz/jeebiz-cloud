package io.hiwepy.cloud.authz.passwd.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeVerifiModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPwdRetakeVerifiService extends IBaseService<PwdRetakeVerifiModel> {

    List<PwdRetakeVerifiModel> getVerifiList();

}
