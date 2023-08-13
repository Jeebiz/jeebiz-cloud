package io.hiwepy.cloud.authz.passwd.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeStrategyModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IPwdRetakeStrategyDao extends BaseMapper<PwdRetakeStrategyModel> {

    List<PwdRetakeStrategyModel> getStrategyList();

}
