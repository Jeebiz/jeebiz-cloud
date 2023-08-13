/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.logs.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.ApiCode;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.api.dto.AuthzLogNewDTO;
import io.hiwepy.cloud.base.logs.enums.AuthzOptEnum;
import io.hiwepy.cloud.base.logs.enums.LoggerLevelEnum;
import io.hiwepy.cloud.base.logs.service.IAuthzLogService;
import io.hiwepy.cloud.base.logs.web.dto.AuthzLogPaginationDTO;
import io.hiwepy.cloud.base.logs.web.vo.AuthzLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(tags = "安全审计：认证授权日志（用户登录、登出日志信息）")
@RestController
@RequestMapping("logs/authz")
@Slf4j
public class AuthzLogsController extends BaseApiController {

    @Autowired
    private IAuthzLogService authzLogService;

    @ApiOperation(value = "认证授权类型", notes = "认证授权类型（login:登录认证、logout:会话注销）")
    @GetMapping("opts")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<Map<String, String>>> authzOpts() throws Exception {
        return ApiRestResponse.success(AuthzOptEnum.toList());
    }

    @ApiOperation(value = "日志级别", notes = "日志级别（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）")
    @GetMapping("levels")
    @PreAuthorize("authenticated")
    public ApiRestResponse<List<Map<String, String>>> levels() throws Exception {
        return ApiRestResponse.success(LoggerLevelEnum.toList());
    }

    @ApiOperation(value = "认证授权日志", notes = "分页查询用户登录、登出日志信息")
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('log:authz:list','*') ")
    public Result<AuthzLogVO> list(@Valid @RequestBody AuthzLogPaginationDTO paginationDTO) throws Exception {
        // 1、分页查询日志列表
        Page<AuthzLogVO> pageResultVo = getAuthzLogService().getPagedLogList(paginationDTO);
        // 2、转换为分页结果
        return new Result<>(pageResultVo, pageResultVo.getRecords());
    }

    @ApiOperation(value = "记录新的认证日志", notes = "增加认证日志信息")
    @PreAuthorize("authenticated and hasAnyAuthority('log:authz:new','*') ")
    @PostMapping("create")
    public ApiRestResponse<AuthzLogNewDTO> newLog(@RequestBody @Valid AuthzLogNewDTO logDTO) throws Exception {
        try {
            boolean flag = getAuthzLogService().saveLog(logDTO);
            if (flag) {
                return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("log.new.success"), logDTO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ApiRestResponse.of(ApiCode.SC_FAIL, getMessage("log.new.fail"), logDTO);
    }

    public IAuthzLogService getAuthzLogService() {
        return authzLogService;
    }

}
