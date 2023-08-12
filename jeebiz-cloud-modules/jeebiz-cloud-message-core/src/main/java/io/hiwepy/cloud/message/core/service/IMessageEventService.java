/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.message.core.dao.entities.MessageEventEntity;
import io.hiwepy.cloud.message.core.web.dto.InformEventStatsDTO;
import io.hiwepy.cloud.message.core.web.dto.InformEventTargetsDTO;

import java.util.List;

public interface IMessageEventService extends IBaseService<MessageEventEntity> {

    /**
     * 消息通知事件统计信息
     */
    List<InformEventStatsDTO> getStats();

    boolean setTargets(InformEventTargetsDTO targetDtos);

    InformEventTargetsDTO getTargets(String eventId);
}
