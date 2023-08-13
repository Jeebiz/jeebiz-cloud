package io.hiwepy.cloud.authz.dbperms.dao.entities;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.dao.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.util.List;

@SuppressWarnings("serial")
@Alias("DataPermissionSpecialModel")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionSpecialModel extends BaseEntity<DataPermissionSpecialModel> {


    /**
     * 数据权限专项ID
     */
    private String id;
    /**
     * 数据权限专项编码
     */
    private String code;
    /**
     * 数据权限专项名称
     */
    private String name;
    /**
     * 数据权限专项可用状态:（0:不可用|1：可用）
     */
    private String status;
    /**
     * 数据权限专项排序
     */
    private int order;
    /**
     * 数据权限专项字段集合
     */
    private List<DataPermissionSpecialColumnModel> columns = Lists.newArrayList();


}
