package io.hiwepy.cloud.authz.passwd.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "PwdRetakeVerifiPairDTO", description = "账号核实字段验证参数")
@Getter
@Setter
@ToString
public class PwdRetakeVerifiPairDTO {

    /**
     * 账号核实字段名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "账号核实字段名称")
    protected String name;
    /**
     * 账号核实字段值（操作者填写）
     */
    @ApiModelProperty(name = "value", dataType = "String", value = "账号核实字段值（操作者填写）")
    protected String value;

}
