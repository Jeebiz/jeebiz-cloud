/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.dao;


import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.message.core.dao.entities.MessageTemplateEntity;
import io.hiwepy.cloud.message.core.web.dto.InformTemplateStatsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageTemplateMapper extends BaseMapper<MessageTemplateEntity> {

    /**
     * 消息通知统计信息
     */
    List<InformTemplateStatsDTO> getStats();

}
