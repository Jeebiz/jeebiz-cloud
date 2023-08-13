package io.hiwepy.cloud.sms.flow.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.sms.flow.dao.entities.SmsFlowEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsFlowMapper extends BaseMapper<SmsFlowEntity> {
}
