package io.hiwepy.cloud.authz.dbperms.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionTableColumnModel;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionTableModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据权限对象查询Dao
 */
@Mapper
public interface DataPermissionTableMapper extends BaseMapper<DataPermissionTableModel> {

    int insertColumn(DataPermissionTableColumnModel model);

    int deleteColumn(@Param("id") String id);

    int updateColumn(DataPermissionTableColumnModel model);

    DataPermissionTableColumnModel getColumnModel(@Param("id") String id);

    List<DataPermissionTableColumnModel> getTableColumns(@Param("tid") String tid);

    List<PairModel> getDataList(DataPermissionTableColumnModel model);

}
