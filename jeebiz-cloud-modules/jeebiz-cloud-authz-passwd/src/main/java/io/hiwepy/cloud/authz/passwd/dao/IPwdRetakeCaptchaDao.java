package io.hiwepy.cloud.authz.passwd.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeCaptchaModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IPwdRetakeCaptchaDao extends BaseMapper<PwdRetakeCaptchaModel> {

    String getDatetime();

    PwdRetakeCaptchaModel getCaptcha(@Param("uuid") String uuid);

}
