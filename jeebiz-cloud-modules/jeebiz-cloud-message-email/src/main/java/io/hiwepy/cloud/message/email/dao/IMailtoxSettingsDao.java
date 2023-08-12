package io.hiwepy.cloud.message.email.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.message.email.dao.entities.MailtoxSettingsModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMailtoxSettingsDao extends BaseMapper<MailtoxSettingsModel> {

}
