package io.hiwepy.cloud.authz.acl.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.acl.dao.IDataPermissionDao;
import io.hiwepy.cloud.authz.acl.dao.entities.DataPermissionModel;
import io.hiwepy.cloud.authz.acl.service.IDataPermissionService;
import org.springframework.stereotype.Service;

/**
 * 数据权限service层接口实现
 */
@Service
public class DataPermissionServiceImpl extends BaseServiceImpl<IDataPermissionDao, DataPermissionModel>
        implements IDataPermissionService {

}