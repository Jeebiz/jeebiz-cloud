package io.hiwepy.cloud.base.logs.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 横幅配置查询参数
 */
@ApiModel(value = "BannerQueryParams", description = "横幅配置查询参数")
@Data
public class BannerQueryParams {

    /**
     * 横幅类型（0：首页轮班、1：我的页面轮播、2：搜索页面轮播）
     */
    @ApiModelProperty(value = "横幅类型（0：首页轮班、1：我的页面轮播、2：搜索页面轮播）")
    @NotNull
    private Integer type;

}
