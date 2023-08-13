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
import io.hiwepy.cloud.message.core.emums.InformToType;
import lombok.*;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias(value = "InformTargetEntity")
@TableName("t_message_targets")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MessageTargetEntity extends PaginationEntity<MessageTargetEntity> {

    /**
     * 消息通知对象主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 消息通知事件id
     */
    @TableField(value = "from_id")
    private String eventId;
    /**
     * 消息通知事件通知对象类型：（ORG:组织机构、ROLE:角色、POST：岗位、USER：人员）
     */
    @TableField(value = "to_type")
    private InformToType toType;
    /**
     * 消息通知接收对象id
     */
    @TableField(value = "to_id")
    private String targetId;

}
