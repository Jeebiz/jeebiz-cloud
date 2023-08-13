/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.device.event;

import io.hiwepy.cloud.base.device.web.dto.DeviceActivateEventDTO;
import org.springframework.biz.context.event.EnhancedEvent;

@SuppressWarnings("serial")
public class DeviceActivateEvent extends EnhancedEvent<DeviceActivateEventDTO> {

    public DeviceActivateEvent(Object source, DeviceActivateEventDTO bind) {
        super(source, bind);
    }

}
