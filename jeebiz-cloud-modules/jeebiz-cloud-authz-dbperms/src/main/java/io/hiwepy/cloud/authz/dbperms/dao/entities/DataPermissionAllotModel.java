package io.hiwepy.cloud.authz.dbperms.dao.entities;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.dao.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

@SuppressWarnings("serial")
@Alias("DataPermissionAllotModel")
@Getter
@Setter
@ToString
public class DataPermissionAllotModel extends BaseEntity<DataPermissionAllotModel> {

    /**
     * 用户ID
     */
    private String uid;
    /**
     * 角色ID
     */
    private String rid;
    /**
     * 数据权限组ID集合
     */
    private List<String> groups = Lists.newArrayList();
    /**
     * 数据权限专项编码集合
     */
    private List<String> specials = Lists.newArrayList();
    /**
     * 数据权限集合
     */
    private List<DataPermissionModel> perms = Lists.newArrayList();


}
