package io.hiwepy.cloud.authz.dbperms.web.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.mybatis.dbperms.annotation.Relational;

import java.util.List;

@ApiModel(value = "DataPermissionDTO", description = "数据权限参数DTO")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionDTO {

    /**
     * 数据权限ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "数据权限ID")
    private String id;
    /**
     * 数据权限名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "数据权限名称")
    private String name;
    /**
     * 数据权限类型(1:原生|2:继承|3:复制)
     */
    @ApiModelProperty(name = "type", dataType = "Integer", value = "数据权限类型(1:原生|2:继承|3:复制)", allowableValues = "1,2,3")
    private String type;
    /**
     * 数据权限简介
     */
    @ApiModelProperty(name = "intro", dataType = "String", value = "数据权限简介")
    private String intro;
    /**
     * 数据对象（实体表名称）
     */
    @ApiModelProperty(name = "table", dataType = "String", value = "数据对象（实体表名称）")
    private String table;
    /**
     * 数据权限项集合
     */
    @ApiModelProperty(name = "items", dataType = "java.util.List<DataPermissionItemDTO>", value = "数据权限项集合")
    private List<DataPermissionItemDTO> items = Lists.newArrayList();
    /**
     * 数据权限项关系 and/or
     */
    @ApiModelProperty(name = "relation", dataType = "org.apache.mybatis.dbperms.annotation.Relational", value = "数据权限项关系:（and:并且|or：或）")
    private Relational relation = Relational.AND;
    @ApiModelProperty(name = "relationStr", dataType = "String", value = "数据权限项关系:（and:并且|or：或）")
    private String relationStr;
    /**
     * 数据权限可用状态:（0:不可用|1：可用）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "可用状态:（0:不可用|1：可用）", allowableValues = "0,1")
    private String status;
    /**
     * 数据权限排序
     */
    @ApiModelProperty(name = "order", dataType = "Integer", value = "数据权限排序")
    private int order;


}
