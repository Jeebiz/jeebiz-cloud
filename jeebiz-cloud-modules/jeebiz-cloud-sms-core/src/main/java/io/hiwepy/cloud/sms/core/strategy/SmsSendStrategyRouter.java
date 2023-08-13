package io.hiwepy.cloud.sms.core.strategy;

import io.hiwepy.cloud.sms.core.bo.SendSmsBO;
import io.hiwepy.cloud.sms.core.enums.SmsChannel;
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
public class SmsSendStrategyRouter implements InitializingBean {

    private Map<SmsChannel, SmsSendStrategy> map = new HashMap<>();

    @Autowired(required = false)
    private List<SmsSendStrategy> smsSendStrategyList;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(smsSendStrategyList)) {
            for (SmsSendStrategy strategy : smsSendStrategyList) {
                map.put(strategy.getChannel(), strategy);
            }
        }
        log.info("smsSendStrategyMap:{}", map);
    }

    public <O extends SendSmsBO> SmsSendStrategy routeFor(O smsBo) {
        SmsSendStrategy orderStrategy = map.get(smsBo.getChannel());
        return orderStrategy;
    }

}
