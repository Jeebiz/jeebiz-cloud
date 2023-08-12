/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.sessions.service.impl;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.base.sessions.dao.IOnlineSessionDao;
import io.hiwepy.cloud.base.sessions.dao.entities.OnlineSessionModel;
import io.hiwepy.cloud.base.sessions.service.IOnlineSessionService;
import io.hiwepy.cloud.base.sessions.setup.SimpleOnlineSession;
import io.hiwepy.cloud.base.sessions.setup.SimpleOnlineSession.OnlineStatus;
import io.hiwepy.cloud.base.sessions.web.dto.OnlineSessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

@Service
public class OnlineSessionServiceImpl extends BaseServiceImpl<IOnlineSessionDao, OnlineSessionModel>
        implements IOnlineSessionService {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    @Autowired
    private RedisSessionRepository sessionRegistry;

    @Override
    public List<OnlineSessionDTO> getActiveSessions() {
        if (sessionRegistry == null) {
            throw new IllegalStateException("sessionDao must be set for this filter");
        }

        getSessionRegistry().getSessionRedisOperations().keys("spring:session:sessions:*");

        Collection<Session> sessions = null;
        List<OnlineSessionDTO> onlineSessions = Lists.newArrayList();
        for (Session session : sessions) {

            OnlineSessionDTO sessionDTO = null/*new OnlineSessionDTO(String.valueOf(session.getId()), session.getHost(),
					dateFormat.format(session.getStartTimestamp()),
					dateFormat.format(session.getLastAccessTime()),
					session.getTimeout())*/;

            if (session instanceof SimpleOnlineSession) {
                SimpleOnlineSession onlineSession = (SimpleOnlineSession) session;
                sessionDTO.setStatus(onlineSession.getStatus().getInfo());
                sessionDTO.setUserAgent(onlineSession.getUserAgent());
                sessionDTO.setSystemHost(onlineSession.getSystemHost());
            }
            //if(Boolean.TRUE.equals(session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY))) {
            //	sessionDTO.setStatus(OnlineStatus.FORCE_LOGOUT.getInfo());
            //}
            onlineSessions.add(sessionDTO);
        }
        return onlineSessions;
    }

    @Override
    public boolean forceLogout(String sessionId) {
        try {
            Session session = getSessionRegistry().findById(sessionId);
            if (session != null) {
                if (session instanceof SimpleOnlineSession) {
                    SimpleOnlineSession onlineSession = (SimpleOnlineSession) session;
                    onlineSession.setStatus(OnlineStatus.FORCE_LOGOUT);
                }
                //session.setAttribute(Constants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);
            }
            return true;
        } catch (Exception e) {/*ignore*/
            return false;
        }
    }

    @Override
    public void offline(String sessionId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void online(OnlineSessionModel onlineSession) {
        // TODO Auto-generated method stub

    }

    public RedisSessionRepository getSessionRegistry() {
        return sessionRegistry;
    }

}
