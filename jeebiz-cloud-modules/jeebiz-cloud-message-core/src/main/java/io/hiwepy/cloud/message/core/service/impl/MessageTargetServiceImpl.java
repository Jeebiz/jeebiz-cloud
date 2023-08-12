/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.message.core.dao.MessageTargetMapper;
import io.hiwepy.cloud.message.core.dao.entities.MessageTargetEntity;
import io.hiwepy.cloud.message.core.service.IMessageTargetService;
import org.springframework.stereotype.Service;

@Service
public class MessageTargetServiceImpl extends BaseServiceImpl<MessageTargetMapper, MessageTargetEntity>
        implements IMessageTargetService {

}
