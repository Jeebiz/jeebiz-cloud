package io.hiwepy.cloud.sample.setup.event.handler;

import io.hiwepy.boot.api.event.PropsUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.biz.context.event.handler.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class PropsUpdateEventHandler implements EventHandler<PropsUpdateEvent> {

    protected Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(PropsUpdateEvent event) {

        if (LOG.isDebugEnabled()) {

            LOG.debug("Business Throwing [ Method ： {} , ArgNames : {} , Cause : {} ] ", event.getBind().toString());

        }

    }

}
