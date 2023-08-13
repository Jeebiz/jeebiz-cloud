/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.device.event;

import io.hiwepy.cloud.base.device.web.dto.DeviceBindEventDTO;
import org.springframework.biz.context.event.EnhancedEvent;

@SuppressWarnings("serial")
public class DeviceBindEvent extends EnhancedEvent<DeviceBindEventDTO> {

    public DeviceBindEvent(Object source, DeviceBindEventDTO bind) {
        super(source, bind);
    }

}
