package io.hiwepy.cloud.authz.passwd.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeStrategyModel;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeStrategyService;
import io.hiwepy.cloud.authz.passwd.setup.Constants;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdStrategyManager;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeStrategyDTO;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeStrategyPaginationDTO;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeStrategyRenewDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 密码找回策略控制器
 */
@Api(tags = "找回密码：策略控制器（Ok）")
@RestController
@RequestMapping(value = "/authz/passwd/strategy/")
public class PwdRetakeStrategyController extends BaseApiController {

    @Resource(name = "pwdStrategyManager")
    protected PwdStrategyManager strategyManager;
    @Autowired
    protected IPwdRetakeStrategyService pwdRetakeStrategyService;

    @ApiOperation(value = "分页查询密码找回策略", notes = "分页查询密码找回策略")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "PwdRetakeStrategyPaginationDTO")
    })
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('passwd-strategy:list','*') ")
    @ResponseBody
    public Object list(@Valid @RequestBody PwdRetakeStrategyPaginationDTO paginationDTO) {

        PwdRetakeStrategyModel model = getBeanMapper().map(paginationDTO, PwdRetakeStrategyModel.class);
        Page<PwdRetakeStrategyModel> pageResult = getPwdRetakeStrategyService().getPagedList(model);
        List<PwdRetakeStrategyDTO> retList = Lists.newArrayList();
        for (PwdRetakeStrategyModel PwdRetakeStrategyModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(PwdRetakeStrategyModel, PwdRetakeStrategyDTO.class));
        }

        return new Result<PwdRetakeStrategyDTO>(pageResult, retList);

    }

    @ApiOperation(value = "更新密码找回策略", notes = "更新密码找回策略")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "vo", value = "密码找回策略", required = true, dataType = "PwdRetakeStrategyRenewDTO"),
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "更新密码找回策略", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('passwd-strategy:renew','*') ")
    @ResponseBody
    public ApiRestResponse<String> renew(@Valid @RequestBody PwdRetakeStrategyRenewDTO vo) throws Exception {
        Long ct = getPwdRetakeStrategyService().getCountByName(vo.getName(), vo.getId());
        if (ct > 0) {
            return fail("passwd.strategy.renew.conflict");
        }
        PwdRetakeStrategyModel model = getBeanMapper().map(vo, PwdRetakeStrategyModel.class);
        boolean result = getPwdRetakeStrategyService().updateById(model);
        if (result) {
            return success("passwd.strategy.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("passwd.strategy.renew.fail", result);
    }

    @ApiOperation(value = "更新密码找回策略状态", notes = "更新密码找回策略状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "密码找回策略ID", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "status", required = true, value = "密码找回策略状态", dataType = "String", allowableValues = "1,0")
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "更新密码找回策略状态", opt = BusinessType.UPDATE)
    @GetMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('passwd-strategy:status','*') ")
    @ResponseBody
    public ApiRestResponse<String> status(@RequestParam String id, @RequestParam String status) throws Exception {
        boolean result = getPwdRetakeStrategyService().setStatus(id, status);
        if (result) {
            return success("passwd.strategy.status.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("passwd.strategy.status.fail", result);
    }

    @ApiOperation(value = "查询密码找回策略信息", notes = "根据ID查询密码找回策略信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "密码找回策略ID", dataType = "String")
    })
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('passwd-strategy:detail','*') ")
    @ResponseBody
    public ApiRestResponse<PwdRetakeStrategyDTO> detail(@RequestParam("id") String id) throws Exception {
        PwdRetakeStrategyModel model = getPwdRetakeStrategyService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("passwd.strategy.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, PwdRetakeStrategyDTO.class));
    }

    public IPwdRetakeStrategyService getPwdRetakeStrategyService() {
        return pwdRetakeStrategyService;
    }


}
