package io.hiwepy.cloud.message.email.service;

import hitool.mail.conf.EmailBody;

public interface IMailtoxSendService {

    public boolean send(EmailBody email, boolean html, String addr) throws Exception;

}
