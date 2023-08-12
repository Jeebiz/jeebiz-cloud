package io.hiwepy.cloud.message.core.strategy;

import io.hiwepy.cloud.message.core.bo.InformSendBO;
import io.hiwepy.cloud.message.core.emums.InformSendChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MessageSendStrategyRouter implements InitializingBean {

    private Map<InformSendChannel, MessageSendStrategy> map = new HashMap<>();

    @Autowired(required = false)
    private List<MessageSendStrategy> payStrategyList;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(payStrategyList)) {
            for (MessageSendStrategy payStrategy : payStrategyList) {
                map.put(payStrategy.getChannel(), payStrategy);
            }
        }
        log.info("payStrategyMap:{}", map);
    }

    public <O extends InformSendBO> MessageSendStrategy routeFor(InformSendChannel channel) {
        MessageSendStrategy orderStrategy = map.get(channel);
        return orderStrategy;
    }

    public <O extends InformSendBO> MessageSendStrategy routeFor(O payBo) {
        MessageSendStrategy orderStrategy = map.get(payBo.getChannel());
        return orderStrategy;
    }

}
