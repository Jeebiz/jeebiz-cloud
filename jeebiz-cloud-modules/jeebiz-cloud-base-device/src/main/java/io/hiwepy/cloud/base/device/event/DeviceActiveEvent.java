/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.device.event;

import io.hiwepy.cloud.base.device.web.dto.DeviceActiveEventDTO;
import org.springframework.biz.context.event.EnhancedEvent;

@SuppressWarnings("serial")
public class DeviceActiveEvent extends EnhancedEvent<DeviceActiveEventDTO> {

    public DeviceActiveEvent(Object source, DeviceActiveEventDTO bind) {
        super(source, bind);
    }

}
