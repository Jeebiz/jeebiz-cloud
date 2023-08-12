package io.hiwepy.cloud.sms.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.sms.core.SmsCountryTemplate;
import io.hiwepy.cloud.sms.core.bo.SendSmsBO;
import io.hiwepy.cloud.sms.core.bo.SendSmsResult;
import io.hiwepy.cloud.sms.core.enums.SmsChannel;
import io.hiwepy.cloud.sms.core.exception.SmsSendException;
import io.hiwepy.cloud.sms.core.strategy.SmsFlowProvider;
import io.hiwepy.cloud.sms.flow.dao.SmsFlowMapper;
import io.hiwepy.cloud.sms.flow.dao.entities.SmsFlowEntity;
import io.hiwepy.cloud.sms.flow.service.ISmsFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class SmsFlowServiceImpl extends BaseServiceImpl<SmsFlowMapper, SmsFlowEntity> implements ISmsFlowService, SmsFlowProvider {

    @Autowired
    private SmsCountryTemplate countryTemplate;

    @Override
    public <R extends SendSmsResult, O extends SendSmsBO> boolean hasFlow(SmsChannel channel, O smsBo) throws SmsSendException {
        // 1、查询短信发送记录数量
        Long count = getBaseMapper().selectCount(new LambdaQueryWrapper<SmsFlowEntity>()
                .eq(Objects.nonNull(smsBo.getUserId()), SmsFlowEntity::getUserId, smsBo.getUserId())
                .eq(Objects.nonNull(smsBo.getUserCode()), SmsFlowEntity::getUserCode, smsBo.getUserCode())
                .eq(Objects.nonNull(smsBo.getSmsNo()), SmsFlowEntity::getSmsNo, smsBo.getSmsNo()));
        // 2、判断是否发送过
        return Objects.nonNull(count) && count > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <R extends SendSmsResult, O extends SendSmsBO> void recordFlow(SmsChannel channel, O smsBo, R smsRt) throws SmsSendException {
        String flowDesc = new StringBuilder().append("BizId[")
                .append(smsBo.getBizId())
                .append("]渠道")
                .append("[")
                .append(smsBo.getChannel().getDesc())
                .append("]")
                .append("发送短信：")
                .append(smsBo.getVcode())
                .toString();

        // ip获取国家
        String countryByIp = getCountryTemplate().getCountryByIp(smsBo.getIpAddress());

        SmsFlowEntity flowEntity = SmsFlowEntity.builder()
                .appId(smsBo.getAppId())
                .appChannel(smsBo.getAppChannel())
                .appVer(smsBo.getAppVer())
                .bizId(smsRt.getBizId())
                .requestId(smsRt.getRequestId())
                .smsNo(smsBo.getSmsNo())
                .country(countryByIp)
                .content(smsBo.getContent())
                .flowDesc(flowDesc)
                .ipAddress(smsBo.getIpAddress())
                .channel(channel)
                .userId(smsBo.getUserId())
                .build();
        flowEntity.setCreateTime(LocalDateTime.now());
        flowEntity.setCreator(smsBo.getUserId());
        getBaseMapper().insert(flowEntity);

    }

    public SmsCountryTemplate getCountryTemplate() {
        return countryTemplate;
    }

}

