package io.hiwepy.cloud.authz.passwd.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ApiModel(value = "PwdRetakeStrategyDTO", description = "密码找回策略")
@Getter
@Setter
@ToString
public class PwdRetakeStrategyDTO {

    /**
     * 策略表ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "策略表ID")
    protected String id;

    /**
     * 策略名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "策略名称")
    protected String name;

    /**
     * 策略描述，简述该策略的实现方式
     */
    @ApiModelProperty(name = "desc", dataType = "String", value = "策略描述，简述该策略的实现方式")
    protected String desc;

    /**
     * 是否启用，1：启用(该状态下系统必要有与name对应的策略实现,才能有效)，0：停用
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "是否启用，1：启用(该状态下系统必要有与name对应的策略实现,才能有效)，0：停用")
    protected String status;

}
