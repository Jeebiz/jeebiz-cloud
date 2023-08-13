package io.hiwepy.cloud.authz.dbperms.web.dto;

import io.hiwepy.boot.api.annotation.AllowableValues;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.mybatis.dbperms.annotation.Condition;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "DataPermissionItemRenewDTO", description = "数据权限项修改参数DTO")
@Getter
@Setter
@ToString
public class DataPermissionItemRenewDTO {

    /**
     * 数据权限项ID
     */
    @ApiModelProperty(name = "id", required = true, dataType = "String", value = "数据权限项ID")
    @NotBlank(message = "数据权限项ID不能为空")
    private String id;
    /**
     * 数据权限项名称（实体表字段中文名称）
     */
    @ApiModelProperty(name = "name", required = true, dataType = "String", value = "数据权限项名称（实体表字段中文名称）")
    @NotBlank(message = "数据权限项名称不能为空")
    private String name;
    /**
     * 受限表字段名称（实体表字段列名称）
     */
    @ApiModelProperty(name = "column", required = true, dataType = "String", value = "受限表字段名称（实体表字段列名称）")
    @NotBlank(message = "受限表字段名称不能为空")
    private String column;
    /**
     * 受限表字段中文拼音
     */
    @ApiModelProperty(name = "pinyin", dataType = "String", value = "受限表字段中文拼音")
    @NotBlank(message = "受限表字段中文拼音不能为空")
    private String pinyin;
    /**
     * 受限表字段限制条件
     */
    @ApiModelProperty(name = "condition", dataType = "org.apache.mybatis.dbperms.annotation.Condition", value = "受限表字段限制条件")
    @NotNull(message = "受限表字段限制条件不能为空")
    public Condition condition;
    /**
     * 受限表字段限制项
     */
    @ApiModelProperty(name = "perms", dataType = "String", value = "受限表字段限制项")
    @NotBlank(message = "受限表字段限制项不能为空")
    private String perms;
    /**
     * 数据权限项可用状态:（0:不可用|1：可用）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "可用状态:（0:不可用|1：可用）", allowableValues = "0,1")
    @NotNull(message = "可用状态不能为空")
    @AllowableValues(allows = "0,1", message = "可用状态只允许0或1（0:不可用|1：可用）")
    private String status;
    /**
     * 数据权限项排序
     */
    @ApiModelProperty(name = "order", dataType = "Integer", value = "数据权限项排序")
    private int order;

}
