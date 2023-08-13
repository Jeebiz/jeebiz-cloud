/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.base.sessions.web.mvc;

import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.base.sessions.service.IOnlineSessionService;
import io.hiwepy.cloud.base.sessions.setup.Constants;
import io.hiwepy.cloud.base.sessions.web.dto.OnlineSessionDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * http://jinnianshilongnian.iteye.com/blog/2047643
 */
@Api(tags = "会话管理：在线会话管理")
@Validated
@RestController
@RequestMapping("/sessions/")
public class SessionStatusController extends BaseMapperController {

    @Autowired
    private IOnlineSessionService onlineSessionService;

    @ApiOperation(value = "我的应用列表", notes = "分页查询已维护的应用基本信息、订阅服务量")
    @ApiOperationLog(module = Constants.EXTRAS_SESSIONS, business = "分页查询数据源信息", opt = BusinessType.SELECT)
    @RequestMapping()
    public String list(Model model) {
        List<OnlineSessionDTO> sessions = getOnlineSessionService().getActiveSessions();
        model.addAttribute("sessions", sessions);
        model.addAttribute("sesessionCount", sessions.size());
        return "sessions/list";
    }

    @ApiOperation(value = "我的应用列表", notes = "分页查询已维护的应用基本信息、订阅服务量")
    @ApiOperationLog(module = Constants.EXTRAS_SESSIONS, business = "分页查询数据源信息", opt = BusinessType.SELECT)
    @PostMapping(value = "list")
    @ResponseBody
    public Object list() throws Exception {
        List<OnlineSessionDTO> sessions = getOnlineSessionService().getActiveSessions();
        return new Result<OnlineSessionDTO>(sessions);
    }

    @ApiOperation(value = "创建我的应用", notes = "增加我的应用信息： 应用名称、开发语言、部署地址等")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "appDTO", value = "应用信息传输对象", dataType = "MyApplicationDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_SESSIONS, business = "创建新数据源", opt = BusinessType.INSERT)
    @RequestMapping("/{sessionId}/forceLogout")
    public String forceLogout(@PathVariable("sessionId") String sessionId) {
        getOnlineSessionService().forceLogout(sessionId);
        return "redirect:/sessions";
    }

    public IOnlineSessionService getOnlineSessionService() {
        return onlineSessionService;
    }

    public void setOnlineSessionService(IOnlineSessionService onlineSessionService) {
        this.onlineSessionService = onlineSessionService;
    }

}
