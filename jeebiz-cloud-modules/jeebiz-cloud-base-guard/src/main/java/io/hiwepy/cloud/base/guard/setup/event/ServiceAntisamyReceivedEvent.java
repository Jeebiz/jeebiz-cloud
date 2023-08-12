package io.hiwepy.cloud.base.guard.setup.event;

import com.google.common.collect.Lists;
import io.hiwepy.cloud.base.guard.dao.entities.AntisamyModel;
import org.springframework.biz.context.event.EnhancedEvent;

import java.util.List;

/**
 *
 */
@SuppressWarnings("serial")
public class ServiceAntisamyReceivedEvent extends EnhancedEvent<List<AntisamyModel>> {

    public ServiceAntisamyReceivedEvent(Object source, AntisamyModel inform) {
        super(source, Lists.newArrayList(inform));
    }

    public ServiceAntisamyReceivedEvent(Object source, List<AntisamyModel> informs) {
        super(source, informs);
    }

}
