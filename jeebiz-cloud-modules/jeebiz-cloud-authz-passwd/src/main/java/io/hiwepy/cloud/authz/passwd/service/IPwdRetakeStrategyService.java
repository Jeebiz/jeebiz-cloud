package io.hiwepy.cloud.authz.passwd.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeStrategyModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPwdRetakeStrategyService extends IBaseService<PwdRetakeStrategyModel> {


    List<PwdRetakeStrategyModel> getStrategyList();


}
