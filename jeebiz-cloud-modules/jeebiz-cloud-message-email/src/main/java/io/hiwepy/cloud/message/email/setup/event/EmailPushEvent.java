package io.hiwepy.cloud.message.email.setup.event;

import hitool.mail.conf.EmailBody;
import org.springframework.biz.context.event.EnhancedEvent;

@SuppressWarnings("serial")
public class EmailPushEvent extends EnhancedEvent<EmailBody> {

    public EmailPushEvent(Object source, EmailBody bind) {
        super(source, bind);
    }

}
