/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.base.logs.dao.entities.AuthzLogEntity;
import io.hiwepy.cloud.base.logs.web.vo.AuthzLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
@Mapper
public interface AuthzLogMapper extends BaseMapper<AuthzLogEntity> {

    Page<AuthzLogVO> getPagedLogList(Page<AuthzLogVO> page, @Param("entity") AuthzLogEntity entity);

}
