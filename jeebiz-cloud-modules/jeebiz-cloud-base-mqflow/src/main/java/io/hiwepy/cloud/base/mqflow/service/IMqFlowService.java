package io.hiwepy.cloud.base.mqflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.hiwepy.cloud.base.mqflow.dao.entities.MqFlowEntity;

public interface IMqFlowService extends IService<MqFlowEntity> {

    /**
     * @param mqFlowId : 消息流水id唯一标识
     * @param content  : 消息内容
     * @return
     */
    boolean save(String mqFlowId, String content);

    Long countMqFlowByMqFlowId(String mqFlowId);
}
