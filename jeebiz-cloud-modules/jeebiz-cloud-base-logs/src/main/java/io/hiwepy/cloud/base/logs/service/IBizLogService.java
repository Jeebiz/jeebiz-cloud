/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.api.dto.BizLogNewDTO;
import io.hiwepy.cloud.base.logs.dao.entities.BizLogEntity;
import io.hiwepy.cloud.base.logs.web.dto.BizLogPaginationDTO;
import io.hiwepy.cloud.base.logs.web.vo.BizLogVO;

/**
 * 业务操作日志Service
 */
public interface IBizLogService extends IBaseService<BizLogEntity> {

    /**
     * 保存业务操作日志
     * @param logDTO
     */
    boolean saveLog(BizLogNewDTO logDTO);

    /**
     * 分页查询登录日志列表
     * @param paginationDTO
     * @return
     */
    Page<BizLogVO> getPagedLogList(BizLogPaginationDTO paginationDTO);

}
