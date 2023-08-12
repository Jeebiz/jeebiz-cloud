package io.hiwepy.cloud.message.email.setup.event;

import hitool.mail.JavaMailClientAdapter;
import org.springframework.context.ApplicationListener;

/**
 * 邮件推送事件监听
 */
public class EmailPushEventListener implements ApplicationListener<EmailPushEvent> {

    private JavaMailClientAdapter mailClient;

    public EmailPushEventListener(JavaMailClientAdapter mailClient) {
        this.mailClient = mailClient;
    }

    @Override
    public void onApplicationEvent(EmailPushEvent event) {
        try {
            getMailClient().sendText(event.getBind());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JavaMailClientAdapter getMailClient() {
        return mailClient;
    }

}