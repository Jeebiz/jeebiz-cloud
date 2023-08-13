package io.hiwepy.cloud.authz.dbperms.web.dto;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.annotation.AllowableValues;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.mybatis.dbperms.annotation.Relational;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "DataPermissionRenewDTO", description = "数据权限修改参数DTO")
@Getter
@Setter
@ToString
public class DataPermissionRenewDTO {

    /**
     * 数据权限ID
     */
    @ApiModelProperty(name = "id", required = true, dataType = "String", value = "数据权限ID")
    private String id;
    /**
     * 数据权限名称
     */
    @ApiModelProperty(name = "name", required = true, dataType = "String", value = "数据权限名称")
    @NotBlank(message = "数据权限名称不能为空")
    private String name;
    /**
     * 数据权限简介
     */
    @ApiModelProperty(name = "intro", required = true, dataType = "String", value = "数据权限简介")
    @NotBlank(message = "数据权限简介不能为空")
    private String intro;
    /**
     * 数据对象（实体表名称）
     */
    @ApiModelProperty(name = "table", required = true, dataType = "String", value = "数据对象（实体表名称）")
    @NotBlank(message = "受限表名称不能为空")
    private String table;
    /**
     * 数据权限项关系 and/or
     */
    @ApiModelProperty(name = "relation", required = true, dataType = "Integer", value = "数据权限项关系:（and:并且|or：或）", allowableValues = "and,or")
    @NotNull(message = "数据权限项关系不能为空")
    private Relational relation = Relational.AND;
    /**
     * 数据权限项集合
     */
    @ApiModelProperty(name = "items", dataType = "java.util.List<DataPermissionItemRenewDTO>", value = "数据权限项集合")
    @NotNull(message = "数据权限项不能为空")
    private List<DataPermissionItemRenewDTO> items = Lists.newArrayList();
    /**
     * 数据权限可用状态:（0:不可用|1：可用）
     */
    @ApiModelProperty(name = "status", required = true, dataType = "String", value = "可用状态:（0:不可用|1：可用）", allowableValues = "0,1")
    @NotNull(message = "可用状态不能为空")
    @AllowableValues(allows = "0,1", message = "可用状态只允许0或1（0:不可用|1：可用）")
    private String status;
    /**
     * 数据权限排序
     */
    @ApiModelProperty(name = "order", required = true, dataType = "Integer", value = "数据权限排序")
    @NotNull(message = "数据权限排序不能为空")
    private int order;

}
