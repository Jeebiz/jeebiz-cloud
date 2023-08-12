/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.service;

import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionTableColumnModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionTableModel;

import java.util.List;

/**
 * 数据权限组service层接口
 */
public interface IDataPermissionTableService extends IBaseService<DataPermissionTableModel> {

    List<PairModel> getDataList(DataPermissionTableColumnModel model);

}
