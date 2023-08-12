package io.hiwepy.cloud.base.logs.strategy;

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
public class LogWriteStrategyRouter implements InitializingBean {

    private Map<String, LogWriteStrategy> map = new HashMap<>();

    @Autowired(required = false)
    private List<LogWriteStrategy> logWriteStrategyList;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(logWriteStrategyList)) {
            for (LogWriteStrategy strategy : logWriteStrategyList) {
                map.put(strategy.getChannel().name(), strategy);
            }
        }
        log.info("logWriteStrategyMap:{}", map);
    }

    public LogWriteStrategy routeFor(LogWriteChannel channel) {
        LogWriteStrategy orderStrategy = map.get(channel);
        return orderStrategy;
    }

}
