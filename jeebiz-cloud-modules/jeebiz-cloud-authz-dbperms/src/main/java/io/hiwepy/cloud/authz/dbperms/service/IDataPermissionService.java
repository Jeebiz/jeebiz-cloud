/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionModel;

/**
 * 数据权限service层接口
 */
public interface IDataPermissionService extends IBaseService<DataPermissionModel> {

    int setStatus(String id, String status);

}
