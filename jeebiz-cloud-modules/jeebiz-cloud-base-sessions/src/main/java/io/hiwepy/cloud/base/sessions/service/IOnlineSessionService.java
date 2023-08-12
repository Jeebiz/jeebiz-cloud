/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.sessions.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.base.sessions.dao.entities.OnlineSessionModel;
import io.hiwepy.cloud.base.sessions.web.dto.OnlineSessionDTO;

import java.util.List;

public interface IOnlineSessionService extends IBaseService<OnlineSessionModel> {

    List<OnlineSessionDTO> getActiveSessions();

    void offline(String sessionId);

    void online(OnlineSessionModel onlineSession);

    boolean forceLogout(String sessionId);

}
