/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.facex.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.facex.dao.entities.AuthzFaceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthzFaceMapper extends BaseMapper<AuthzFaceEntity> {

    /**
     * 根据用户ID查询人脸数据
     * @param userId 用户ID
     * @return
     */
    AuthzFaceEntity getFaceModel(@Param("uid") String userId);

}
