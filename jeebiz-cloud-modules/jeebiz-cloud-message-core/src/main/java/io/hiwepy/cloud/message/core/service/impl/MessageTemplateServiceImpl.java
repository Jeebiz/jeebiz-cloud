/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.message.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.base.redis.BizRedisKey;
import io.hiwepy.cloud.message.core.dao.MessageTemplateMapper;
import io.hiwepy.cloud.message.core.dao.entities.MessageTemplateEntity;
import io.hiwepy.cloud.message.core.service.IMessageTemplateService;
import io.hiwepy.cloud.message.core.setup.Constants;
import io.hiwepy.cloud.message.core.web.dto.InformTemplatePairDTO;
import io.hiwepy.cloud.message.core.web.dto.InformTemplateStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageTemplateServiceImpl extends BaseServiceImpl<MessageTemplateMapper, MessageTemplateEntity>
        implements IMessageTemplateService {

    @Autowired
    private RedisOperationTemplate redisOperation;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(MessageTemplateEntity entity) {
        boolean rt = super.save(entity);
        String redisKey = BizRedisKey.INFORM_TEMPLATE.getKey(entity.getId());
        redisOperation.hmSet(redisKey, JSONObject.parseObject(JSONObject.toJSONString(entity), Map.class));
        return rt;
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        boolean rt = super.removeByIds(list);
        if (rt && !CollectionUtils.isEmpty(list)) {
            List<String> keys = list.stream().map(id -> BizRedisKey.INFORM_TEMPLATE.getKey(id)).collect(Collectors.toList());
            redisOperation.getOperations().delete(keys);
        }
        return rt;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(MessageTemplateEntity entity) {
        boolean rt = super.updateById(entity);
        String redisKey = BizRedisKey.INFORM_TEMPLATE.getKey(entity.getId());
        redisOperation.hmSet(redisKey, JSONObject.parseObject(JSONObject.toJSONString(entity), Map.class));
        return rt;
    }

    @Override
    public List<InformTemplatePairDTO> getPairs() {

        List<MessageTemplateEntity> templateList = getBaseMapper().selectList(new LambdaQueryWrapper<MessageTemplateEntity>()
                .eq(MessageTemplateEntity::getStatus, Constants.Normal.IS_STATUS_YES)
                .eq(MessageTemplateEntity::getCreator, SubjectUtils.getUserId())
                .eq(MessageTemplateEntity::getIsDeleted, Constants.Normal.IS_DELETE_0));
        if (CollectionUtils.isEmpty(templateList)) {
            return Lists.newArrayList();
        }

        return templateList.stream()
                .map(template -> InformTemplatePairDTO.builder().id(template.getId()).name(template.getName()).build())
                .collect(Collectors.toList());
    }

    @Override
    public List<InformTemplateStatsDTO> getStats() {
        return getBaseMapper().getStats();
    }

}
