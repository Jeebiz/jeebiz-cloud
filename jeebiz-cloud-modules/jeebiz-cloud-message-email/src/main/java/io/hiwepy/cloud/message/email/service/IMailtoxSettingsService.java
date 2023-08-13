/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.email.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.message.email.dao.entities.MailtoxSettingsModel;

/**
 * 邮件发送服务设置 Service
 */
public interface IMailtoxSettingsService extends IBaseService<MailtoxSettingsModel> {

    int setStatus(String id, String status);
}
