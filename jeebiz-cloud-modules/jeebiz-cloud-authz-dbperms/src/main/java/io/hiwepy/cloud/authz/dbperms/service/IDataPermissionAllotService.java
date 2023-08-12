/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionAllotModel;

/**
 * 数据授权service层接口
 */
public interface IDataPermissionAllotService extends IBaseService<DataPermissionAllotModel> {

    public int allot(DataPermissionAllotModel model);

}
