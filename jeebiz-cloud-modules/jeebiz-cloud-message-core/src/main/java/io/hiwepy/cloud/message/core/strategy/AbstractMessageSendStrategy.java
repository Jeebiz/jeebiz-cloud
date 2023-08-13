package io.hiwepy.cloud.message.core.strategy;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import io.hiwepy.boot.api.sequence.Sequence;
import io.hiwepy.cloud.message.core.bo.InformSendBO;
import io.hiwepy.cloud.message.core.bo.InformSendResult;
import io.hiwepy.cloud.message.core.dao.MessageRecordMapper;
import io.hiwepy.cloud.message.core.dao.MessageTemplateMapper;
import io.hiwepy.cloud.message.core.dao.entities.MessageRecordEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
public abstract class AbstractMessageSendStrategy implements MessageSendStrategy, InitializingBean, ApplicationEventPublisherAware {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Mapper beanMapper;
    @Autowired
    private MessageRecordMapper messageRecordMapper;
    @Autowired
    private MessageTemplateMapper messageTemplateMapper;
    @Autowired
    private Sequence sequence;
    @Autowired
    private RedisOperationTemplate redisOperation;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    @Transactional
    public <R extends InformSendResult, O extends InformSendBO> R send(O sendBo, Class<R> rtType) throws Exception {
        // 1、验证请求，如果有不满足有要求则应抛出异常
        boolean flag = this.preCheck(sendBo);
        if (!flag) {
            return null;
        }
        // 2、钩子方法 用于扩展
        customizedMethod(sendBo);
        // 3、通过检查则继续处理上传
        R uploadRt = this.handleSend(sendBo);
        // 4、记录上传流水；文件上传状态（ 0：上传失败、1：上传成功）
        if (Objects.nonNull(uploadRt) && uploadRt.getStatus() == 1) {
            recordFlow(sendBo, uploadRt);
        }
        // 5、返回上传结果
        return uploadRt;
    }

    protected <O extends InformSendBO> boolean preCheck(O sendBo) throws Exception {

        return true;
    }

    protected <O extends InformSendBO> void customizedMethod(O sendBo) throws Exception {
        // TODO Auto-generated method stub
    }

    ;

    protected abstract <R extends InformSendResult, O extends InformSendBO> R handleSend(O sendBo) throws Exception;

    protected <R extends InformSendResult, O extends InformSendBO> void recordFlow(O sendBo, R sendRt) throws Exception {
        MessageRecordEntity fileEntity = MessageRecordEntity.builder()
                .appId(sendBo.getAppId())
                .appChannel(sendBo.getAppChannel())
                .appVer(sendBo.getAppVer())
                .channel(this.getChannel())
                .receiverId(sendBo.getUserId())
                .build();
        fileEntity.setCreateTime(LocalDateTime.now());
        fileEntity.setCreator(sendBo.getUserId());
        getInformRecordMapper().insert(fileEntity);
        log.info(" Upload File : {}", JSONObject.toJSONString(fileEntity));
    }

    public ApplicationEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    protected Mapper getBeanMapper() {
        return beanMapper;
    }

    protected Sequence getSequence() {
        return sequence;
    }

    public RedisOperationTemplate getRedisOperation() {
        return redisOperation;
    }

    public MessageRecordMapper getInformRecordMapper() {
        return messageRecordMapper;
    }

    public MessageTemplateMapper getInformTemplateMapper() {
        return messageTemplateMapper;
    }

}
