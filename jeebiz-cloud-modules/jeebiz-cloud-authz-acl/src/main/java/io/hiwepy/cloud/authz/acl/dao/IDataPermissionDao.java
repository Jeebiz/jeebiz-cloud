package io.hiwepy.cloud.authz.acl.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.acl.dao.entities.DataPermissionModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据权限配置表dao接口
 */
@Mapper
public interface IDataPermissionDao extends BaseMapper<DataPermissionModel> {

}
