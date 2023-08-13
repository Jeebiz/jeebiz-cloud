package io.hiwepy.cloud.authz.org.dao;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.cloud.authz.org.dao.entities.AuthzTeamEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthzTeamMapper extends BaseMapper<AuthzTeamEntity> {

    /**
     * 根据名称获取记录数
     *
     * @return
     */
    public int getTeamCountByName(@Param("name") String name, @Param("deptId") String deptId, @Param("teamId") String teamId);

    /**
     * 获取小组下成员梳理
     *
     * @param id
     * @return
     */
    public int getStaffCount(@Param("id") String id);

}
