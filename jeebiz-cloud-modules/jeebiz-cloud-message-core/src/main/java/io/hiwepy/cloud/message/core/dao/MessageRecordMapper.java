/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.dao;


import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.message.core.dao.entities.MessageRecordEntity;
import io.hiwepy.cloud.message.core.web.dto.InformRecordStatsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageRecordMapper extends BaseMapper<MessageRecordEntity> {

    /**
     * 消息通知统计信息
     */
    List<InformRecordStatsDTO> getStats(@Param("userId") String userId);

}
