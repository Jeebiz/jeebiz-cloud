/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.service;

import io.hiwepy.boot.api.service.IBaseService;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionGroupCheckedModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionGroupModel;

import java.util.List;

/**
 * 数据权限组service层接口
 */
public interface IDataPermissionGroupService extends IBaseService<DataPermissionGroupModel> {

    List<DataPermissionGroupCheckedModel> getGroupUserPairs(String userid);

    List<DataPermissionGroupCheckedModel> getGroupRolePairs(String roleid);

}
