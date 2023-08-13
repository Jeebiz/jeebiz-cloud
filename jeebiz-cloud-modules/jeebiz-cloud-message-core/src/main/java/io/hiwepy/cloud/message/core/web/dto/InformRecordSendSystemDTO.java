/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.web.dto;

import lombok.Data;

@Data
public class InformRecordSendSystemDTO extends InformRecordSendDTO {

    private String userId;

    private String ipAddress;

}
