/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.base.redis.BizRedisKey;
import io.hiwepy.cloud.message.core.dao.MessageEventMapper;
import io.hiwepy.cloud.message.core.dao.entities.MessageEventEntity;
import io.hiwepy.cloud.message.core.dao.entities.MessageTargetEntity;
import io.hiwepy.cloud.message.core.emums.InformTarget;
import io.hiwepy.cloud.message.core.service.IMessageEventService;
import io.hiwepy.cloud.message.core.service.IMessageTargetService;
import io.hiwepy.cloud.message.core.setup.Constants;
import io.hiwepy.cloud.message.core.web.dto.InformEventStatsDTO;
import io.hiwepy.cloud.message.core.web.dto.InformEventTargetDTO;
import io.hiwepy.cloud.message.core.web.dto.InformEventTargetsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MessageEventServiceImpl extends BaseServiceImpl<MessageEventMapper, MessageEventEntity>
        implements IMessageEventService {

    @Autowired
    private IMessageTargetService informTargetService;
    @Autowired
    private RedisOperationTemplate redisOperation;


    @Override
    public Long getCount(MessageEventEntity entity) {
        if (Objects.nonNull(entity.getId())) {
            return getBaseMapper().selectCount(new LambdaQueryWrapper<MessageEventEntity>()
                    .eq(MessageEventEntity::getType, entity.getType())
                    .eq(MessageEventEntity::getChannel, entity.getChannel())
                    .eq(MessageEventEntity::getTemplateId, entity.getTemplateId())
                    .eq(MessageEventEntity::getIsDeleted, Constants.Normal.IS_DELETE_0)
                    .ne(MessageEventEntity::getId, entity.getId()));
        }
        return getBaseMapper().selectCount(new LambdaQueryWrapper<MessageEventEntity>()
                .eq(MessageEventEntity::getType, entity.getType())
                .eq(MessageEventEntity::getChannel, entity.getChannel())
                .eq(MessageEventEntity::getTemplateId, entity.getTemplateId())
                .eq(MessageEventEntity::getIsDeleted, Constants.Normal.IS_DELETE_0));
    }

    @Override
    public List<InformEventStatsDTO> getStats() {
        return getBaseMapper().getStats();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(MessageEventEntity entity) {
        boolean rt = super.save(entity);
        String redisKey = BizRedisKey.INFORM_EVENT.getKey(entity.getId());
        redisOperation.hmSet(redisKey, JSONObject.parseObject(JSONObject.toJSONString(entity), Map.class));
        return rt;
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        boolean rt = super.removeByIds(list);
        if (rt && !CollectionUtils.isEmpty(list)) {
            List<String> keys = list.stream().map(id -> BizRedisKey.INFORM_EVENT.getKey(id)).collect(Collectors.toList());
            redisOperation.getOperations().delete(keys);
        }
        return rt;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(MessageEventEntity entity) {
        boolean rt = super.updateById(entity);
        String redisKey = BizRedisKey.INFORM_EVENT.getKey(entity.getId());
        redisOperation.hmSet(redisKey, JSONObject.parseObject(JSONObject.toJSONString(entity), Map.class));
        return rt;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setTargets(InformEventTargetsDTO targetsDto) {

        // 1、设置消息事件的通知对象类型
        MessageEventEntity eventEntity = new MessageEventEntity();
        eventEntity.setId(targetsDto.getEventId());
        eventEntity.setModifyer(SubjectUtils.getUserId());
        eventEntity.setTarget(targetsDto.getTarget());
        getBaseMapper().updateById(eventEntity);

        String redisKey = BizRedisKey.INFORM_EVENT.getKey(eventEntity.getId());
        redisOperation.hSet(redisKey, "target", targetsDto.getTarget());

        // 2、查询已经设置的通知对象并删除旧的通知对象
        this.removeTargetsByEventId(targetsDto.getEventId());

        // 3、如果设置的是部分通知，则进行通知对象处理
        if (InformTarget.SPECIFIC.equals(targetsDto.getTarget()) && !CollectionUtils.isEmpty(targetsDto.getTargets())) {
            targetsDto.getTargets().stream().forEach(targetDto -> {
                MessageTargetEntity entity = new MessageTargetEntity();
                entity.setEventId(targetsDto.getEventId());
                entity.setToType(targetDto.getToType());
                entity.setTargetId(targetDto.getTargetId());
                informTargetService.save(entity);

                String targetRedisKey = BizRedisKey.INFORM_TARGET.getKey(targetDto.getToType(), eventEntity.getId());
                redisOperation.zAdd(targetRedisKey, targetDto.getTargetId(), System.currentTimeMillis());

            });
        }

        return true;
    }

    protected List<MessageTargetEntity> removeTargetsByEventId(String eventId) {
        List<MessageTargetEntity> targets = informTargetService.list(new LambdaQueryWrapper<MessageTargetEntity>()
                .select(MessageTargetEntity::getId, MessageTargetEntity::getToType, MessageTargetEntity::getTargetId)
                .eq(MessageTargetEntity::getEventId, eventId));
        if (!CollectionUtils.isEmpty(targets)) {
            targets.stream().forEach(targetEntity -> {
                String targetRedisKey = BizRedisKey.INFORM_TARGET.getKey(targetEntity.getToType(), eventId);
                redisOperation.zRem(targetRedisKey, targetEntity.getTargetId());
            });
            informTargetService.remove(new LambdaQueryWrapper<MessageTargetEntity>().eq(MessageTargetEntity::getEventId, eventId));
        }
        return targets;
    }

    @Override
    public InformEventTargetsDTO getTargets(String eventId) {
        InformEventTargetsDTO targetsDTO = new InformEventTargetsDTO();
        MessageEventEntity entity = getBaseMapper().selectById(eventId);
        if (Objects.isNull(entity)) {
            return targetsDTO;
        }
        targetsDTO.setTarget(entity.getTarget());
        targetsDTO.setEventId(eventId);
        List<MessageTargetEntity> targets = informTargetService.list(new LambdaQueryWrapper<MessageTargetEntity>()
                .select(MessageTargetEntity::getId, MessageTargetEntity::getToType, MessageTargetEntity::getTargetId)
                .eq(MessageTargetEntity::getEventId, eventId)
                .eq(MessageTargetEntity::getIsDeleted, Constants.Normal.IS_DELETE_0));
        if (!CollectionUtils.isEmpty(targets)) {
            targetsDTO.setTargets(targets.stream().map(target -> {
                InformEventTargetDTO targetDTO = new InformEventTargetDTO();
                targetDTO.setId(target.getId());
                targetDTO.setToType(target.getToType());
                targetDTO.setTargetId(target.getTargetId());
                return targetDTO;

            }).collect(Collectors.toList()));
        }
        return targetsDTO;
    }

}
