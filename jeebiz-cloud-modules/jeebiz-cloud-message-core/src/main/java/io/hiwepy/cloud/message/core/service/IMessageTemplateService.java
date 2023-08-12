/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.message.core.dao.entities.MessageTemplateEntity;
import io.hiwepy.cloud.message.core.web.dto.InformTemplatePairDTO;
import io.hiwepy.cloud.message.core.web.dto.InformTemplateStatsDTO;

import java.util.List;

public interface IMessageTemplateService extends IBaseService<MessageTemplateEntity> {

    /**
     * 消息通知模板下拉列表
     */
    List<InformTemplatePairDTO> getPairs();

    /**
     * 消息通知模板统计信息
     */
    List<InformTemplateStatsDTO> getStats();

}
