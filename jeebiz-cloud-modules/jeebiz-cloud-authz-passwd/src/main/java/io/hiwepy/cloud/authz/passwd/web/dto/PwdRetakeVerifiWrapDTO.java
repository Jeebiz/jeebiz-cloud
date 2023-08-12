package io.hiwepy.cloud.authz.passwd.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "PwdRetakeVerifiWrapDTO", description = "账号核实验证参数")
@Getter
@Setter
@ToString
public class PwdRetakeVerifiWrapDTO {

    /**
     * 密码找回操作UID：作为后续操作的凭证
     */
    @ApiModelProperty(name = "uuid", dataType = "String", value = "密码找回操作UID：作为后续操作的凭证")
    protected String uuid;
    /**
     * 用户账号
     */
    @ApiModelProperty(name = "username", dataType = "String", value = "用户账号")
    protected String username;
    /**
     * 账号核实字段集合
     */
    @ApiModelProperty(name = "list", dataType = "java.util.List<PwdRetakeVerifiPairDTO>", value = "账号核实字段集合")
    protected List<PwdRetakeVerifiPairDTO> list = new ArrayList<>();

}
