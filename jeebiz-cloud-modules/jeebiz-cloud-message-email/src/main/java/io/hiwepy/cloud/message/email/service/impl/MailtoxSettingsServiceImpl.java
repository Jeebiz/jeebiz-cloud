/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.email.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.message.email.dao.IMailtoxSettingsDao;
import io.hiwepy.cloud.message.email.dao.entities.MailtoxSettingsModel;
import io.hiwepy.cloud.message.email.service.IMailtoxSettingsService;
import org.springframework.stereotype.Service;

/**
 * 邮件发送服务设置 Service实现
 */
@Service
public class MailtoxSettingsServiceImpl extends BaseServiceImpl<IMailtoxSettingsDao, MailtoxSettingsModel>
        implements IMailtoxSettingsService {

    @Override
    public int setStatus(String id, String status) {
        return 0;
    }
}
