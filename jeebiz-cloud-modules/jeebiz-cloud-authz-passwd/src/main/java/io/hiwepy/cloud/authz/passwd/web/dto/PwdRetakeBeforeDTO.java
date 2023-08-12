package io.hiwepy.cloud.authz.passwd.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ApiModel(value = "PwdRetakeBeforeDTO", description = "密码找回前置数据DTO")
@Getter
@Setter
@ToString
public class PwdRetakeBeforeDTO {

    /**
     * 密码找回操作UID：作为后续操作的凭证
     */
    @ApiModelProperty(name = "uuid", dataType = "String", value = "密码找回操作UID：作为后续操作的凭证")
    protected String uuid;
    /**
     * 账号核实字段
     */
    @ApiModelProperty(name = "verifiList", dataType = "java.util.List<PwdRetakeVerifiDTO>", value = "账号核实字段")
    protected List<PwdRetakeVerifiDTO> verifiList;
    /**
     * 密码找回策略
     */
    @ApiModelProperty(name = "strategyList", dataType = "java.util.List<PwdRetakeStrategyDTO>", value = "密码找回策略")
    protected List<PwdRetakeStrategyDTO> strategyList;

}
