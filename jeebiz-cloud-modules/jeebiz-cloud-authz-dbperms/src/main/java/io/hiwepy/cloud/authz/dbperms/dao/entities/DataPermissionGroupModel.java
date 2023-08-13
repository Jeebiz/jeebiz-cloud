package io.hiwepy.cloud.authz.dbperms.dao.entities;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@SuppressWarnings("serial")
@Alias("DataPermissionGroupModel")
@Getter
@Setter
@ToString
public class DataPermissionGroupModel extends PaginationEntity<DataPermissionGroupModel> {

    /**
     * 数据权限组ID
     */
    private String id;
    /**
     * 数据权限组名称
     */
    private String name;
    /**
     * 数据权限组简介
     */
    private String intro;
    /**
     * 可用状态:（0:不可用|1：可用）
     */
    private String status;
    /**
     * 数据权限组创建时间
     */
    private String time24;
    /**
     * 数据权限集合
     */
    private List<DataPermissionModel> perms = Lists.newArrayList();


}
