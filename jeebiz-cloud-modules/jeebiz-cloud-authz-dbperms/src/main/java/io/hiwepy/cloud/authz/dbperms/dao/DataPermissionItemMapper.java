package io.hiwepy.cloud.authz.dbperms.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.dbperms.dao.entities.DataPermissionItemModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据权限项dao接口
 */
@Mapper
public interface DataPermissionItemMapper extends BaseMapper<DataPermissionItemModel> {

    int deleteByCode(@Param("code") String code);

    /**
     * 根据表名和字段得到配置对应的字段信息
     *
     * @param code
     * @param column
     * @return
     */
    public Map<String, String> getTableColumn(@Param("code") String code, @Param("column") String column);

    /**
     * 得到表名对应的字段关系
     *
     * @param code
     * @return
     */
    public List<Map<String, String>> getTableColumns(@Param("code") String code);

}
