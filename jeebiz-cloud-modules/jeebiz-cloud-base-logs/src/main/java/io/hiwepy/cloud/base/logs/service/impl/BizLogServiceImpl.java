/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.XHeaders;
import io.hiwepy.boot.api.annotation.ApiModule;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.boot.api.utils.WebUtils;
import io.hiwepy.boot.autoconfigure.region.NestedRegionTemplate;
import io.hiwepy.cloud.api.dto.BizLogNewDTO;
import io.hiwepy.cloud.base.logs.dao.BizLogMapper;
import io.hiwepy.cloud.base.logs.dao.entities.BizLogEntity;
import io.hiwepy.cloud.base.logs.service.IBizLogService;
import io.hiwepy.cloud.base.logs.strategy.LogWriteChannel;
import io.hiwepy.cloud.base.logs.strategy.LogWriteStrategy;
import io.hiwepy.cloud.base.logs.web.dto.BizLogPaginationDTO;
import io.hiwepy.cloud.base.logs.web.vo.BizLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * 业务操作日志Service实现
 */
@Service
public class BizLogServiceImpl extends BaseServiceImpl<BizLogMapper, BizLogEntity> implements IBizLogService, LogWriteStrategy {

    @Autowired
    private NestedRegionTemplate regionTemplate;

    @Override
    public LogWriteChannel getChannel() {
        return LogWriteChannel.database;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <O extends BizLogNewDTO> boolean saveLog(Method method, Api api, ApiOperation apiOperation, Object rt, Throwable ex) throws Exception {
        ApiModule apiModule = AnnotationUtils.findAnnotation(method.getDeclaringClass(), ApiModule.class);
        ApiOperationLog bizLog = AnnotationUtils.findAnnotation(method, ApiOperationLog.class);
        if (Objects.isNull(apiModule) || Objects.isNull(bizLog)) {
            return false;
        }
        // 1、获取 Request对象，解析请求来源
        HttpServletRequest request = WebUtils.getHttpServletRequest();
        String ipAddress = "";
        if (Objects.nonNull(request)) {
            ipAddress = WebUtils.getRemoteAddr(request);
            request.getHeader(XHeaders.X_APP_ID);
        }
        // 2、构造日志对象
        BizLogEntity entity = new BizLogEntity();
        entity.setModule(StringUtils.defaultIfBlank(bizLog.module(), apiModule.module()));
        entity.setBusiness(StringUtils.defaultIfBlank(bizLog.business(), apiModule.business()));
        entity.setOpt(bizLog.opt().getKey());
        entity.setLevel(Objects.isNull(ex) ? "info" : "error");
        entity.setAddr(ipAddress);
        entity.setLocation(regionTemplate.getLocationByIp(ipAddress));
        StringJoiner joiner = new StringJoiner("->");
        joiner.add(bizLog.opt().getDesc()).add(StringUtils.defaultIfBlank(apiOperation.value(), apiOperation.notes()));
        entity.setMsg(joiner.toString());
        entity.setException(Objects.toString(ex.getMessage(), StringUtils.EMPTY));
        entity.setDeviceId(request.getHeader(XHeaders.X_DEVICE_IMEI));
        entity.setAppId(request.getHeader(XHeaders.X_APP_ID));
        entity.setAppChannel(request.getHeader(XHeaders.X_APP_CHANNEL));
        entity.setAppVersion(request.getHeader(XHeaders.X_APP_VERSION));
        entity.setCreator(getVisiter());
        entity.setCreateTime(LocalDateTime.now());

        return this.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveLog(BizLogNewDTO logDTO) {
        BizLogEntity entity = getBeanMapper().map(logDTO, BizLogEntity.class);
        entity.setCreateTime(LocalDateTime.now());
        entity.setCreator(logDTO.getUserId());
        boolean save = this.save(entity);
        return save;
    }

    @Override
    public Page<BizLogVO> getPagedLogList(BizLogPaginationDTO paginationDTO) {
        // 1. 将DTO转换为Entity
        BizLogEntity entity = getBeanMapper().map(paginationDTO, BizLogEntity.class);
        // 2、使用时间筛选时，补全开始结束时间
        LocalDate beginDate = paginationDTO.getBeginDate();
        LocalDate endDate = paginationDTO.getEndDate();
        if (Objects.nonNull(beginDate) && Objects.nonNull(endDate)) {
            entity.setBeginTime(beginDate.atStartOfDay());
            entity.setEndTime(endDate.atTime(23, 59, 59));
        }
        // 3、创建分页对象
        Page<BizLogVO> page = new Page<>(paginationDTO.getPageNo(), paginationDTO.getLimit());
        // 4、调用Mapper查询
        return getBaseMapper().getPagedLogList(page, entity);
    }

    public String getVisiter() {
        String userId = "-1";
        try {
            SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
            if (Objects.nonNull(principal)) {
                userId = principal.getUid();
            }
        } catch (Throwable e) {
        }
        return userId;
    }

}