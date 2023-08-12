/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.base.logs.dao.entities.BizLogEntity;
import io.hiwepy.cloud.base.logs.web.vo.BizLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
@Mapper
public interface BizLogMapper extends BaseMapper<BizLogEntity> {

    Page<BizLogVO> getPagedLogList(Page<BizLogVO> page, @Param("entity") BizLogEntity entity);

}
