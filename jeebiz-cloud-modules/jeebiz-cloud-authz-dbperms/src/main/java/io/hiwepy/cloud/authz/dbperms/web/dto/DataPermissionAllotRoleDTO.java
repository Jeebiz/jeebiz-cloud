package io.hiwepy.cloud.authz.dbperms.web.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ApiModel(value = "DataPermissionAllotRoleDTO", description = "角色数据授权参数DTO")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class DataPermissionAllotRoleDTO {

    /**
     * 角色ID
     */
    @ApiModelProperty(name = "rid", required = true, dataType = "String", value = "角色ID")
    @NotBlank(message = "角色ID不能为空")
    private String rid;
    /**
     * 数据权限组ID集合
     */
    @ApiModelProperty(name = "groups", required = true, dataType = "java.util.List<String>", value = "数据权限组ID集合")
    private List<String> groups = Lists.newArrayList();
    /**
     * 数据权限专项编码集合
     */
    @ApiModelProperty(name = "specials", required = true, dataType = "java.util.List<String>", value = "数据权限专项编码集合")
    private List<String> specials = Lists.newArrayList();
    /**
     * 数据权限集合（单独授权时可使用该参数）
     */
    @ApiModelProperty(name = "perms", dataType = "java.util.List<DataPermissionDTO>", value = "数据权限集合（单独授权时可使用该参数）")
    private List<DataPermissionDTO> perms = Lists.newArrayList();

}
