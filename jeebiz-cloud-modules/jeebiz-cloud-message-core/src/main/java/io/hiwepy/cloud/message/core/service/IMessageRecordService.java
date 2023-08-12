/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.message.core.dao.entities.MessageRecordEntity;
import io.hiwepy.cloud.message.core.web.dto.InformRecordStatsDTO;

import java.util.List;

public interface IMessageRecordService extends IBaseService<MessageRecordEntity> {

    /**
     * 消息通知统计信息
     */
    List<InformRecordStatsDTO> getStats(String uid);

}
