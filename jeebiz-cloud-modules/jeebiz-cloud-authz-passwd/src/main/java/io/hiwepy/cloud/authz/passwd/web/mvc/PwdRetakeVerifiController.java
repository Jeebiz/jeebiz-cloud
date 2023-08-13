package io.hiwepy.cloud.authz.passwd.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeVerifiModel;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeVerifiService;
import io.hiwepy.cloud.authz.passwd.setup.Constants;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeVerifiDTO;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeVerifiNewDTO;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeVerifiPaginationDTO;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeVerifiRenewDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 密码找回核实字段信息
 */
@Api(tags = "找回密码：核实字段信息（Ok）")
@RestController
@RequestMapping(value = "/authz/passwd/verifi/")
public class PwdRetakeVerifiController extends BaseApiController {

    @Autowired
    protected IPwdRetakeVerifiService pwdRetakeVerifiService;

    @ApiOperation(value = "分页查询账号核实字段信息", notes = "分页查询账号核实字段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "PwdRetakeVerifiPaginationDTO")
    })
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('passwd-verifi:list','*') ")
    @ResponseBody
    public Object list(@Valid @RequestBody PwdRetakeVerifiPaginationDTO paginationDTO) {

        PwdRetakeVerifiModel model = getBeanMapper().map(paginationDTO, PwdRetakeVerifiModel.class);
        Page<PwdRetakeVerifiModel> pageResult = getPwdRetakeVerifiService().getPagedList(model);
        List<PwdRetakeVerifiDTO> retList = Lists.newArrayList();
        for (PwdRetakeVerifiModel PwdRetakeVerifiModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(PwdRetakeVerifiModel, PwdRetakeVerifiDTO.class));
        }

        return new Result<PwdRetakeVerifiDTO>(pageResult, retList);

    }

    @ApiOperation(value = "创建账号核实字段信息", notes = "增加一个新的账号核实字段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "vo", value = "账号核实字段信息传输对象", dataType = "PwdRetakeVerifiNewDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "创建账号核实字段信息", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('passwd-verifi:new','*') ")
    @ResponseBody
    public ApiRestResponse<String> newVerifi(@Valid @RequestBody PwdRetakeVerifiNewDTO vo) throws Exception {
        Long ct = getPwdRetakeVerifiService().getCountByName(vo.getName(), null);
        if (ct > 0) {
            return fail("passwd.verifi.new.conflict");
        }
        // 新增一条数据库配置记录
        PwdRetakeVerifiModel model = getBeanMapper().map(vo, PwdRetakeVerifiModel.class);
        boolean result = getPwdRetakeVerifiService().save(model);
        if (result) {
            return success("passwd.verifi.new.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("passwd.verifi.new.fail", result);
    }

    @ApiOperation(value = "删除账号核实字段信息", notes = "删除账号核实字段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "账号核实字段信息ID,多个用,拼接", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "删除账号核实字段信息", opt = BusinessType.UPDATE)
    @GetMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('passwd-verifi:delete','*') ")
    @ResponseBody
    public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
        // 执行账号核实字段信息删除操作
        List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
        boolean result = getPwdRetakeVerifiService().removeBatchByIds(idList);
        if (result) {
            return success("passwd.verifi.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("passwd.verifi.delete.fail", result);
    }

    @ApiOperation(value = "更新账号核实字段信息", notes = "更新账号核实字段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "vo", value = "账号核实字段信息", required = true, dataType = "PwdRetakeVerifiRenewDTO"),
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "更新账号核实字段信息", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('passwd-verifi:renew','*') ")
    @ResponseBody
    public ApiRestResponse<String> renew(@Valid @RequestBody PwdRetakeVerifiRenewDTO vo) throws Exception {
        Long ct = getPwdRetakeVerifiService().getCountByName(vo.getName(), vo.getId());
        if (ct > 0) {
            return fail("passwd.verifi.renew.conflict");
        }
        PwdRetakeVerifiModel model = getBeanMapper().map(vo, PwdRetakeVerifiModel.class);
        boolean result = getPwdRetakeVerifiService().updateById(model);
        if (result) {
            return success("passwd.verifi.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("passwd.verifi.renew.fail", result);
    }

    @ApiOperation(value = "更新账号核实字段信息状态", notes = "更新账号核实字段信息状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "账号核实字段信息ID", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "status", required = true, value = "账号核实字段信息状态", dataType = "String", allowableValues = "1,0")
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "更新账号核实字段信息状态", opt = BusinessType.UPDATE)
    @GetMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('passwd-verifi:status','*') ")
    @ResponseBody
    public ApiRestResponse<String> status(@RequestParam String id, @RequestParam String status) throws Exception {
        boolean result = getPwdRetakeVerifiService().setStatus(id, status);
        if (result) {
            return success("passwd.verifi.status.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("passwd.verifi.status.fail", result);
    }

    @ApiOperation(value = "查询账号核实字段信息信息", notes = "根据ID查询账号核实字段信息信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "账号核实字段信息ID", dataType = "String")
    })
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('passwd-verifi:detail','*') ")
    @ResponseBody
    public ApiRestResponse<PwdRetakeVerifiDTO> detail(@RequestParam("id") String id) throws Exception {
        PwdRetakeVerifiModel model = getPwdRetakeVerifiService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("passwd.verifi.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, PwdRetakeVerifiDTO.class));
    }

    public IPwdRetakeVerifiService getPwdRetakeVerifiService() {
        return pwdRetakeVerifiService;
    }

}
