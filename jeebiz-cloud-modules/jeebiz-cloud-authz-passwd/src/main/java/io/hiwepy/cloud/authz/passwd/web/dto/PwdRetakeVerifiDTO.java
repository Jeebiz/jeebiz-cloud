package io.hiwepy.cloud.authz.passwd.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "PwdRetakeVerifiDTO", description = "账号核实验证参数")
@Getter
@Setter
@ToString
public class PwdRetakeVerifiDTO {

    /**
     * 账号核实字段表ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "账号核实字段表ID")
    protected String id;
    /**
     * 账号核实字段名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "账号核实字段名称")
    protected String name;
    /**
     * 账号核实字段Label名称
     */
    @ApiModelProperty(name = "label", dataType = "String", value = "账号核实字段Label名称")
    protected String label;
    /**
     * 账号核实字段描述，作为提示信息
     */
    @ApiModelProperty(name = "desc", dataType = "String", value = "账号核实字段描述，作为提示信息")
    protected String desc;
    /**
     * 账号核实字段校验规则
     */
    @ApiModelProperty(name = "rules", dataType = "String", value = "账号核实字段校验规则")
    protected String rules;
    /**
     * 账号核实字段是否必填，1：是，0：否
     */
    @ApiModelProperty(name = "required", dataType = "String", value = "账号核实字段是否必填，1：是，0：否")
    protected String required;
    /**
     * 账号核实字段启用状态标记，1：启用，0：停用
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "账号核实字段启用状态标记，1：启用，0：停用")
    protected String status;


}
