package io.hiwepy.cloud.base.mqflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.hiwepy.cloud.base.mqflow.dao.IMqFlowDao;
import io.hiwepy.cloud.base.mqflow.dao.entities.MqFlowEntity;
import io.hiwepy.cloud.base.mqflow.service.IMqFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class MqFlowServiceImpl extends ServiceImpl<IMqFlowDao, MqFlowEntity> implements IMqFlowService {

    @Override
    public boolean save(String mqFlowId, String content) {
        MqFlowEntity entity = new MqFlowEntity();
        entity.setMqFlowId(mqFlowId);
        entity.setContent(content);
        LocalDateTime now = LocalDateTime.now();
        entity.setCreateTime(now);
        entity.setModifyTime(now);
        try {
            this.save(entity);
            return true;
        } catch (Exception e) {
            log.error("MqFlowServiceImpl save error mqFlowId:{}", mqFlowId, e);
        }
        return false;
    }

    @Override
    public Long countMqFlowByMqFlowId(String mqFlowId) {
        return this.count(new QueryWrapper<MqFlowEntity>().eq("mq_flow_id", mqFlowId));
    }

}
