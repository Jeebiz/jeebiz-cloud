/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.api.dto.AuthzLogNewDTO;
import io.hiwepy.cloud.base.logs.dao.entities.AuthzLogEntity;
import io.hiwepy.cloud.base.logs.web.dto.AuthzLogPaginationDTO;
import io.hiwepy.cloud.base.logs.web.vo.AuthzLogVO;

/**
 * 认证授权日志Service
 */
public interface IAuthzLogService extends IBaseService<AuthzLogEntity> {

    /**
     * 保存认证授权日志
     * @param logDTO
     */
    boolean saveLog(AuthzLogNewDTO logDTO);

    /**
     * 分页查询登录日志列表
     * @param paginationDTO
     * @return
     */
    Page<AuthzLogVO> getPagedLogList(AuthzLogPaginationDTO paginationDTO);

}
