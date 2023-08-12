/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.api.dto.BizLogNewDTO;
import io.hiwepy.cloud.base.logs.enums.LoggerLevelEnum;
import io.hiwepy.cloud.base.logs.service.IBizLogService;
import io.hiwepy.cloud.base.logs.web.dto.BizLogPaginationDTO;
import io.hiwepy.cloud.base.logs.web.vo.BizLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(tags = "安全审计：功能操作日志 （除登录外的功能操作日志信息）")
@RestController
@RequestMapping("logs/biz")
@Slf4j
public class BizLogsController extends BaseApiController {

    @Autowired
    private IBizLogService bizLogService;

    @ApiOperation(value = "操作类型", notes = "操作类型（操作日志筛选条件）")
    @GetMapping("opts")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<Map<String, String>>> bizOpts() throws Exception {
        return ApiRestResponse.success(BusinessType.toList());
    }

    @ApiOperation(value = "日志级别", notes = "日志级别（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）")
    @GetMapping("levels")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<List<Map<String, String>>> levels() throws Exception {
        return ApiRestResponse.success(LoggerLevelEnum.toList());
    }

    @ApiOperation(value = "功能操作日志", notes = "分页查询除登录外的功能操作日志信息")
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('log:biz:list','*') ")
    public Result<BizLogVO> list(@Valid @RequestBody BizLogPaginationDTO paginationDTO) throws Exception {
        // 1、分页查询日志列表
        Page<BizLogVO> pageResult = getBizLogService().getPagedLogList(paginationDTO);
        // 2、转换为分页结果
        return new Result<>(pageResult, pageResult.getRecords());
    }

    @ApiOperation(value = "记录新的业务日志", notes = "增加业务日志信息")
    @PreAuthorize("authenticated and hasAnyAuthority('log:biz:new','*') ")
    @PostMapping("create")
    public ApiRestResponse<BizLogNewDTO> newLog(@RequestBody @Valid BizLogNewDTO logDTO) throws Exception {
        try {
            boolean flag = getBizLogService().saveLog(logDTO);
            if (flag) {
                return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("log.new.success"), logDTO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ApiRestResponse.of(ApiCode.SC_FAIL, getMessage("log.new.fail"), logDTO);
    }

    public IBizLogService getBizLogService() {
        return bizLogService;
    }

}
