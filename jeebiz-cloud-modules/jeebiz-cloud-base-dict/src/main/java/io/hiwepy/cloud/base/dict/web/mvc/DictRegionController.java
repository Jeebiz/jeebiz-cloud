package io.hiwepy.cloud.base.dict.web.mvc;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.base.dict.dao.entities.DictRegionEntity;
import io.hiwepy.cloud.base.dict.service.IDictRegionService;
import io.hiwepy.cloud.base.dict.web.dto.DictRegionDTO;
import io.hiwepy.cloud.base.dict.web.dto.DictRegionPaginationDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 国家地区代码 http://doc.chacuo.net/iso-3166-1 前端控制器
 * </p>
 *
 * @author wandl
 * @since 2022-01-22
 */
@Controller
@RequestMapping("/dict/region")
public class DictRegionController extends BaseApiController {

    @Autowired
    private IDictRegionService regionService;

    @ApiOperation(value = "分页查询国家地区代码", notes = "分页查询国家地区代码")
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('region:list','*') ")
    @ResponseBody
    public Result<DictRegionDTO> list(@Valid @RequestBody DictRegionPaginationDTO paginationDTO) {

        DictRegionEntity entity = getBeanMapper().map(paginationDTO, DictRegionEntity.class);
        Page<DictRegionEntity> pageResult = getRegionService().getPagedList(entity);
        List<DictRegionDTO> retList = Lists.newArrayList();
        for (DictRegionEntity regionEntity : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(regionEntity, DictRegionDTO.class));
        }

        return new Result<DictRegionDTO>(pageResult, retList);

    }

    @ApiOperation(value = "查询国家地区代码（键值对）", notes = "查询国家地区代码（键值对）")
    @GetMapping("pairs")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<PairModel>> pairs() throws Exception {
        return ApiRestResponse.success(getRegionService().getPairList());
    }

    public IDictRegionService getRegionService() {
        return regionService;
    }
}

