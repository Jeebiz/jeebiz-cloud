package io.hiwepy.cloud.authz.passwd.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.boot.api.dao.entities.PairModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPwdRetakeSettingsDao extends BaseMapper<PairModel> {

}
