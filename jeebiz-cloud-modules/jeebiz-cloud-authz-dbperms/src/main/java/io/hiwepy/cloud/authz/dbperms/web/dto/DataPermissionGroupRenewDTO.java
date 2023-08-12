package io.hiwepy.cloud.authz.dbperms.web.dto;

import com.google.common.collect.Lists;
import io.hiwepy.boot.api.annotation.AllowableValues;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "DataPermissionGroupRenewDTO", description = "数据权限组修改参数DTO")
@Getter
@Setter
@ToString
public class DataPermissionGroupRenewDTO {

    /**
     * 数据权限组ID
     */
    @ApiModelProperty(name = "id", required = true, dataType = "String", value = "数据权限组ID")
    @NotBlank(message = "数据权限组ID不能为空")
    private String id;
    /**
     * 数据权限组名称
     */
    @ApiModelProperty(name = "name", required = true, dataType = "String", value = "数据权限组名称")
    @NotBlank(message = "数据权限组名称不能为空")
    private String name;
    /**
     * 数据权限组简介
     */
    @ApiModelProperty(name = "intro", required = true, dataType = "String", value = "数据权限组简介")
    @NotBlank(message = "数据权限组简介不能为空")
    private String intro;
    /**
     * 可用状态:（0:不可用|1：可用）
     */
    @ApiModelProperty(name = "status", required = true, dataType = "String", value = "可用状态:（0:不可用|1：可用）", allowableValues = "0,1")
    @NotNull(message = "可用状态不能为空")
    @AllowableValues(allows = "0,1", message = "可用状态只允许0或1（0:不可用|1：可用）")
    private String status;
    /**
     * 数据权限集合
     */
    @ApiModelProperty(name = "perms", dataType = "java.util.List<DataPermissionRenewDTO>", value = "数据权限集合")
    @NotNull(message = "数据权限不能为空")
    private List<DataPermissionRenewDTO> perms = Lists.newArrayList();

}
