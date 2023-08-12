package io.hiwepy.cloud.base.guard.setup.event.linstener;

import io.hiwepy.cloud.base.guard.dao.IAntisamyDao;
import io.hiwepy.cloud.base.guard.dao.entities.AntisamyModel;
import io.hiwepy.cloud.base.guard.setup.event.ServiceAntisamyReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ServiceAntisamyReceivedEventListener implements ApplicationListener<ServiceAntisamyReceivedEvent> {

    @Autowired
    private IAntisamyDao serviceInformDao;

    @Override
    public void onApplicationEvent(ServiceAntisamyReceivedEvent event) {

        for (AntisamyModel model : event.getBind()) {
            // 保存消息通知
            getServiceInformDao().insert(model);
        }

    }

    public IAntisamyDao getServiceInformDao() {
        return serviceInformDao;
    }

    public void setServiceInformDao(IAntisamyDao serviceInformDao) {
        this.serviceInformDao = serviceInformDao;
    }

}
