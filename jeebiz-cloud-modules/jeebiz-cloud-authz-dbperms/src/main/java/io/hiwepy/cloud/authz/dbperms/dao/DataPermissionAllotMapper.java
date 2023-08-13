package io.hiwepy.cloud.authz.dbperms.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionAllotModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据授权dao接口
 */
@Mapper
public interface DataPermissionAllotMapper extends BaseMapper<DataPermissionAllotModel> {

}
