package io.hiwepy.cloud.message.email.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.message.email.dao.entities.MailtoxSettingsModel;
import io.hiwepy.cloud.message.email.service.IMailtoxSettingsService;
import io.hiwepy.cloud.message.email.setup.Constants;
import io.hiwepy.cloud.message.email.web.dto.MailtoxSettingsDTO;
import io.hiwepy.cloud.message.email.web.dto.MailtoxSettingsNewDTO;
import io.hiwepy.cloud.message.email.web.dto.MailtoxSettingsPaginationDTO;
import io.hiwepy.cloud.message.email.web.dto.MailtoxSettingsRenewDTO;
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

@Api(tags = "邮件发送：参数设置（Ok）")
@RestController
@RequestMapping(value = "/mailtox/settings/")
public class MailtoxSettingController extends BaseApiController {

    @Autowired
    private IMailtoxSettingsService mailtoxSettingsService;

    @ApiOperation(value = "分页查询邮件发送服务设置", notes = "分页查询邮件发送服务设置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "用户信息筛选条件", dataType = "MailtoxSettingsPaginationDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "根据分组分页查询邮件发送服务设置", opt = BusinessType.SELECT)
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('mailtox-settings:list','*') ")
    @ResponseBody
    public Object list(@Valid @RequestBody MailtoxSettingsPaginationDTO paginationDTO) {

        MailtoxSettingsModel model = getBeanMapper().map(paginationDTO, MailtoxSettingsModel.class);
        Page<MailtoxSettingsModel> pageResult = getMailtoxSettingsService().getPagedList(model);
        List<MailtoxSettingsDTO> retList = Lists.newArrayList();
        for (MailtoxSettingsModel MailtoxSettingsModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(MailtoxSettingsModel, MailtoxSettingsDTO.class));
        }

        return new Result<MailtoxSettingsDTO>(pageResult, retList);

    }

    @ApiOperation(value = "创建邮件发送服务设置", notes = "增加一个新的邮件发送服务设置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "vo", value = "邮件发送服务设置传输对象", dataType = "MailtoxSettingsNewDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "创建邮件发送服务设置", opt = BusinessType.INSERT)
    @PostMapping("new")
    @PreAuthorize("authenticated and hasAnyAuthority('mailtox-settings:new','*') ")
    @ResponseBody
    public ApiRestResponse<String> newSettings(@Valid @RequestBody MailtoxSettingsNewDTO vo) throws Exception {
        MailtoxSettingsModel model = getBeanMapper().map(vo, MailtoxSettingsModel.class);

        Long ct = getMailtoxSettingsService().getCount(model);
        if (ct > 0) {
            return fail("mailtox.settings.new.conflict");
        }
        // 新增一条数据库配置记录
        boolean result = getMailtoxSettingsService().save(model);
        if (result) {
            return success("mailtox.settings.new.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("mailtox.settings.new.fail", result);
    }

    @ApiOperation(value = "删除邮件发送服务设置", notes = "删除邮件发送服务设置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "邮件发送服务设置ID,多个用,拼接", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "删除邮件发送服务设置", opt = BusinessType.UPDATE)
    @GetMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('mailtox-settings:delete','*') ")
    @ResponseBody
    public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
        // 执行邮件发送服务设置删除操作
        List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
        boolean result = getMailtoxSettingsService().removeBatchByIds(idList);
        if (result) {
            return success("mailtox.settings.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("mailtox.settings.delete.fail", result);
    }

    @ApiOperation(value = "更新邮件发送服务设置", notes = "更新邮件发送服务设置")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "vo", value = "邮件发送服务设置", required = true, dataType = "MailtoxSettingsRenewDTO"),
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "更新邮件发送服务设置", opt = BusinessType.UPDATE)
    @PostMapping("renew")
    @PreAuthorize("authenticated and hasAnyAuthority('mailtox-settings:renew','*') ")
    @ResponseBody
    public ApiRestResponse<String> renew(@Valid @RequestBody MailtoxSettingsRenewDTO vo) throws Exception {
        MailtoxSettingsModel model = getBeanMapper().map(vo, MailtoxSettingsModel.class);
        Long ct = getMailtoxSettingsService().getCount(model);
        if (ct > 0) {
            return fail("mailtox.settings.renew.conflict");
        }
        boolean result = getMailtoxSettingsService().updateById(model);
        if (result) {
            return success("mailtox.settings.renew.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("mailtox.settings.renew.fail", result);
    }

    @ApiOperation(value = "更新邮件发送服务设置状态", notes = "更新邮件发送服务设置状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "邮件发送服务设置ID", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "status", required = true, value = "邮件发送服务设置状态", dataType = "String", allowableValues = "1,0")
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "更新邮件发送服务设置状态", opt = BusinessType.UPDATE)
    @GetMapping("status")
    @PreAuthorize("authenticated and hasAnyAuthority('mailtox-settings:status','*') ")
    @ResponseBody
    public ApiRestResponse<String> status(@RequestParam String id, @RequestParam String status) throws Exception {
        int result = getMailtoxSettingsService().setStatus(id, status);
        if (result == 1) {
            return success("mailtox.settings.status.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("mailtox.settings.status.fail", result);
    }

    @ApiOperation(value = "查询邮件发送服务设置信息", notes = "根据ID查询邮件发送服务设置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "邮件发送服务设置ID", dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "查询邮件发送服务设置信息", opt = BusinessType.SELECT)
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('mailtox-settings:detail','*') ")
    @ResponseBody
    public ApiRestResponse<MailtoxSettingsDTO> detail(@RequestParam("id") String id) throws Exception {
        MailtoxSettingsModel model = getMailtoxSettingsService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("mailtox.settings.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, MailtoxSettingsDTO.class));
    }

    public IMailtoxSettingsService getMailtoxSettingsService() {
        return mailtoxSettingsService;
    }

}
