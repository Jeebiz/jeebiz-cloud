/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.api.dto.AuthzLogNewDTO;
import io.hiwepy.cloud.base.logs.dao.AuthzLogMapper;
import io.hiwepy.cloud.base.logs.dao.entities.AuthzLogEntity;
import io.hiwepy.cloud.base.logs.service.IAuthzLogService;
import io.hiwepy.cloud.base.logs.web.dto.AuthzLogPaginationDTO;
import io.hiwepy.cloud.base.logs.web.vo.AuthzLogVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 认证授权日志Service实现
 */
@Service
public class AuthzLogServiceImpl extends BaseServiceImpl<AuthzLogMapper, AuthzLogEntity> implements IAuthzLogService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveLog(AuthzLogNewDTO logDTO) {
        AuthzLogEntity model = getBeanMapper().map(logDTO, AuthzLogEntity.class);
        model.setCreateTime(LocalDateTime.now());
        model.setCreator(logDTO.getUserId());
        return this.save(model);
    }

    @Override
    public Page<AuthzLogVO> getPagedLogList(AuthzLogPaginationDTO paginationDTO) {
        // 1. 将DTO转换为Entity
        AuthzLogEntity entity = getBeanMapper().map(paginationDTO, AuthzLogEntity.class);
        // 2、使用时间筛选时，补全开始结束时间
        LocalDate beginDate = paginationDTO.getBeginDate();
        LocalDate endDate = paginationDTO.getEndDate();
        if (Objects.nonNull(beginDate) && Objects.nonNull(endDate)) {
            entity.setBeginTime(beginDate.atStartOfDay());
            entity.setEndTime(endDate.atTime(23, 59, 59));
        }
        // 3、创建分页对象
        Page<AuthzLogVO> page = new Page<>(paginationDTO.getPageNo(), paginationDTO.getLimit());
        // 4、调用Mapper查询
        return getBaseMapper().getPagedLogList(page, entity);
    }

}