/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import io.hiwepy.cloud.message.core.emums.InformTarget;
import lombok.*;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias(value = "InformEventEntity")
@TableName("t_message_events")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MessageEventEntity extends PaginationEntity<MessageEventEntity> {

    /**
     * 消息通知事件id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 消息通知事件类型
     */
    @TableField(value = "type")
    private String type;
    /**
     * 消息通知事件行为
     */
    @TableField(value = "channel")
    private InformSendChannel channel;
    /**
     * 消息通知事件关联模板id
     */
    @TableField(value = "template_id")
    private String templateId;
    /**
     * 消息通知事件说明
     */
    @TableField(value = "intro")
    private String intro;
    /**
     * 消息通知事件状态：（0:停用、1:启用）
     */
    @TableField(value = "status")
    private Integer status;
    /**
     * 消息通知事件通知对象：（ALL:全部、TRIGGER:触发人、SPECIFIC：部分对象）
     */
    @TableField(value = "target")
    private InformTarget target;
    /**
     * 消息通知模板创建人id
     */
    @TableField(exist = false)
    private String userId;
    /**
     * 消息通知模板创建人
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 消息通知事件已发消息总数
     */
    @TableField(exist = false)
    private Integer total;
    /**
     * 消息通知事件已发消息未读总数
     */
    @TableField(exist = false)
    private Integer unread;

}
