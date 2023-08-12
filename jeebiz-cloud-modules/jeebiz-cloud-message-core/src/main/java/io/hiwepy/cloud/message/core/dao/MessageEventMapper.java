/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.dao;


import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.message.core.dao.entities.MessageEventEntity;
import io.hiwepy.cloud.message.core.web.dto.InformEventStatsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageEventMapper extends BaseMapper<MessageEventEntity> {

    /**
     * 消息通知事件统计信息
     */
    List<InformEventStatsDTO> getStats();

}
