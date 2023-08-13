package io.hiwepy.cloud.base.mqflow.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.boot.api.dao.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "mq_flow", keepGlobalPrefix = true)
public class MqFlowEntity extends BaseEntity<MqFlowEntity> {

    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * mq消息唯一标识用于做幂等
     */
    private String mqFlowId;
    /**
     * 消息内容
     */
    private String content;

}
