package io.hiwepy.cloud.api.event;

import io.hiwepy.cloud.api.dto.JpushRecordDTO;
import org.springframework.biz.context.event.EnhancedEvent;

public class JpushCreateEvent extends EnhancedEvent<JpushRecordDTO> {

    public JpushCreateEvent(Object source, JpushRecordDTO bind) {
        super(source, bind);
    }

}
