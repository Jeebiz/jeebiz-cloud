package io.hiwepy.cloud.sample.setup.event.handler;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.biz.context.event.EventInvocation;
import org.springframework.biz.context.event.aspect.JoinPointAfterEvent;
import org.springframework.biz.context.event.handler.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class BusinessAfterEventHandler implements EventHandler<JoinPointAfterEvent> {

    protected Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(JoinPointAfterEvent event) {

        if (LOG.isDebugEnabled()) {
            // 获取事件环境对象
            EventInvocation invocation = event.getBind();
            JoinPoint point = invocation.getPoint();
            Signature signature = point.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;

            LOG.debug("JoinPoint After [ Method ： {} , ArgNames : {} ] ", methodSignature.getName(),
                    invocation.getArgNames());

        }

    }

}
