package io.hiwepy.cloud.authz.dbperms.dao.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Alias("DataPermissionSpecialCheckedModel")
@Getter
@Setter
@ToString
public class DataPermissionSpecialCheckedModel {

    /**
     * 数据权限专项ID
     */
    private String id;
    /**
     * 数据权限专项名称
     */
    private String name;
    /**
     * 数据权限专项是否授权(1:已授权|0:未授权)
     */
    private String checked;

}
