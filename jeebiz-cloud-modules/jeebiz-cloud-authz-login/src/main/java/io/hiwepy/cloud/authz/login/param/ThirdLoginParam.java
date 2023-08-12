package io.hiwepy.cloud.authz.login.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "ThirdLoginParam", description = "第三方登录vo")
@Data
public class ThirdLoginParam {
    /**
     * 第三方登录临时码
     */
    @ApiModelProperty(value = "loginTmpCode", dataType = "String", notes = "第三方登录临时码")
    @NotBlank(message = "第三方登录临时码为空")
    private String loginTmpCode;

    /**
     * 第三方组织id
     */
    @ApiModelProperty(value = "corpId", dataType = "Integer", notes = "第三方组织id")
    @NotBlank(message = "第三方组织id为空")
    private String corpId;
}
