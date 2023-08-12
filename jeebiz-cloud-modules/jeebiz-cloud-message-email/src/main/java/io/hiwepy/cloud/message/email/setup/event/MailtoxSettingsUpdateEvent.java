package io.hiwepy.cloud.message.email.setup.event;

import org.springframework.biz.context.event.EnhancedEvent;

import java.util.Map;

/**
 * 系统参数更新事件
 */
@SuppressWarnings("serial")
public class MailtoxSettingsUpdateEvent extends EnhancedEvent<Map<String, String>> {

    public MailtoxSettingsUpdateEvent(Object source, Map<String, String> props) {
        super(source, props);
    }

}
