package io.hiwepy.cloud.authz.dbperms.web.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ApiModel(value = "DataPermissionGroupDTO", description = "数据权限组参数DTO")
@Getter
@Setter
@ToString
public class DataPermissionGroupDTO {

    /**
     * 数据权限组ID
     */
    @ApiModelProperty(name = "id", dataType = "String", value = "数据权限组ID")
    private String id;
    /**
     * 数据权限组名称
     */
    @ApiModelProperty(name = "name", dataType = "String", value = "数据权限组名称")
    private String name;
    /**
     * 数据权限组简介
     */
    @ApiModelProperty(name = "intro", dataType = "String", value = "数据权限组简介")
    private String intro;
    /**
     * 可用状态:（0:不可用|1：可用）
     */
    @ApiModelProperty(name = "status", dataType = "String", value = "可用状态:（0:不可用|1：可用）", allowableValues = "0,1")
    private String status;
    /**
     * 数据权限组创建时间
     */
    @ApiModelProperty(name = "time24", dataType = "String", value = "数据权限组创建时间")
    private String time24;
    /**
     * 数据权限集合
     */
    @ApiModelProperty(name = "perms", dataType = "java.util.List<DataPermissionDTO>", value = "数据权限集合")
    private List<DataPermissionDTO> perms = Lists.newArrayList();


}
