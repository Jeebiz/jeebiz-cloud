package io.hiwepy.cloud.authz.dbperms.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.mybatis.dbperms.annotation.Condition;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "DataPermissionItemDTO", description = "数据权限项参数DTO")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionItemDTO {

    /**
     * 数据权限项ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "数据权限ID")
    private String id;
    /**
     * 受限表字段名称（实体表字段列名称）
     */
    @ApiModelProperty(name = "column", required = true, dataType = "String", value = "受限表字段名称（实体表字段列名称）")
    @NotBlank(message = "受限表字段名称不能为空")
    private String column;
    /**
     * 受限表字段名称（实体表字段中文名称）
     */
    @ApiModelProperty(name = "name", required = true, dataType = "String", value = "受限表字段名称（实体表字段中文名称）")
    @NotBlank(message = "受限表字段中文名称不能为空")
    private String name;
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
    public Condition condition;
    @ApiModelProperty(name = "conditionStr", dataType = "String", value = "受限表字段限制条件")
    public String conditionStr;
    /**
     * 受限表字段限制项
     */
    @ApiModelProperty(name = "perms", dataType = "String", value = "受限表字段限制项")
    private String perms;
    /**
     * 受限表字段可用状态:（0:不可用|1：可用）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "可用状态:（0:不可用|1：可用）", allowableValues = "0,1")
    private String status;
    /**
     * 受限表字段排序
     */
    @ApiModelProperty(name = "order", dataType = "Integer", value = "受限表字段排序")
    private int order;

}
