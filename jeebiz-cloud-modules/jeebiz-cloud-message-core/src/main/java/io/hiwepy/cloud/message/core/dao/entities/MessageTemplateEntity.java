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
import lombok.*;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias(value = "InformTemplateEntity")
@TableName("t_message_templates")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MessageTemplateEntity extends PaginationEntity<MessageTemplateEntity> {

    /**
     * 消息通知模板id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 消息通知模板名称
     */
    @TableField(value = "name")
    private String name;
    /**
     * 消息通知模板类型
     */
    @TableField(value = "channel")
    private InformSendChannel channel;
    /**
     * 消息通知标题（可能包含变量）
     */
    @TableField(value = "title")
    private String title;
    /**
     * 消息通知签名（短信消息模板需要使用）
     */
    @TableField(value = "sign")
    private String signature;
    /**
     * 消息通知内容（可能包含变量）
     */
    @TableField(value = "content")
    private String content;
    /**
     * 消息通知模板对应第三方平台内的模板id
     */
    @TableField(value = "template_id")
    private String templateId;
    /**
     * 消息通知模板变量载体,JOSN格式的数据
     */
    @TableField(value = "payload")
    private String payload;
    /**
     * 消息通知模板状态：（0:停用、1:启用）
     */
    @TableField(value = "status")
    private Integer status;
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
     * 消息通知模板已发消息总数
     */
    @TableField(exist = false)
    private Integer total;
    /**
     * 消息通知模板已发消息未读总数
     */
    @TableField(exist = false)
    private Integer unread;
    /**
     * 模糊搜索关键字
     */
    @TableField(exist = false)
    private String keywords;

}