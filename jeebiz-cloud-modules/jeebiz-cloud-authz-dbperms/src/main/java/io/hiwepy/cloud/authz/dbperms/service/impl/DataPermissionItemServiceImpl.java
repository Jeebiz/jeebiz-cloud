package io.hiwepy.cloud.authz.dbperms.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.cloud.authz.dbperms.dao.DataPermissionItemMapper;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionItemModel;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionItemService;
import org.springframework.stereotype.Service;

/**
 * 数据权限项service层接口实现
 */
@Service
public class DataPermissionItemServiceImpl extends BaseServiceImpl<DataPermissionItemMapper, DataPermissionItemModel>
        implements IDataPermissionItemService {

}