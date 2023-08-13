/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.sessions.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.base.sessions.dao.ILastOnlineSessionDao;
import io.hiwepy.cloud.base.sessions.dao.entities.LastOnlineSessionModel;
import io.hiwepy.cloud.base.sessions.service.ILastOnlineSessionService;
import org.springframework.stereotype.Service;

@Service
public class LastOnlineSessionServiceImpl extends BaseServiceImpl<ILastOnlineSessionDao, LastOnlineSessionModel>
        implements ILastOnlineSessionService {

}
