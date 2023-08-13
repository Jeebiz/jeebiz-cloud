package io.hiwepy.cloud.authz.passwd.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeVerifiModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IPwdRetakeVerifiDao extends BaseMapper<PwdRetakeVerifiModel> {

    List<PwdRetakeVerifiModel> getVerifiList();

}
