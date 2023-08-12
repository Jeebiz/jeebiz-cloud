package io.hiwepy.cloud.authz.dbperms.dao.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Alias("DataPermissionGroupCheckedModel")
@Getter
@Setter
@ToString
public class DataPermissionGroupCheckedModel {

    /**
     * 数据权限组ID
     */
    private String id;
    /**
     * 数据权限组名称
     */
    private String name;
    /**
     * 数据权限组是否授权(1:已授权|0:未授权)
     */
    private String checked;

}
