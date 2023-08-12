/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.email.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.message.email.dao.IMailtoxHistoryDao;
import io.hiwepy.cloud.message.email.dao.entities.MailtoxHistoryModel;
import io.hiwepy.cloud.message.email.service.IMailtoxHistoryService;
import org.springframework.stereotype.Service;

/**
 * 邮件发送历史 Service实现
 */
@Service
public class MailtoxHistoryServiceImpl extends BaseServiceImpl<IMailtoxHistoryDao, MailtoxHistoryModel>
        implements IMailtoxHistoryService {

}
