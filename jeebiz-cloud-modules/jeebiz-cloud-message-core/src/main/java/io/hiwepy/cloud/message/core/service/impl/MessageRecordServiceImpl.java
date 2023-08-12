/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.message.core.dao.MessageRecordMapper;
import io.hiwepy.cloud.message.core.dao.entities.MessageRecordEntity;
import io.hiwepy.cloud.message.core.service.IMessageRecordService;
import io.hiwepy.cloud.message.core.setup.Constants;
import io.hiwepy.cloud.message.core.web.dto.InformRecordStatsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageRecordServiceImpl extends BaseServiceImpl<MessageRecordMapper, MessageRecordEntity>
        implements IMessageRecordService {

    @Override
    public Long getCount(MessageRecordEntity entity) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<MessageRecordEntity>()
                .eq(MessageRecordEntity::getReceiverId, entity.getReceiverId())
                .eq(MessageRecordEntity::getIsDeleted, Constants.Normal.IS_DELETE_0));
    }

    @Override
    public List<InformRecordStatsDTO> getStats(String uid) {
        return getBaseMapper().getStats(uid);
    }

}
