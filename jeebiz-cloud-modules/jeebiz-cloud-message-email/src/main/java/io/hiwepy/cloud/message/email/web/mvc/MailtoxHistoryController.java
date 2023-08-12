package io.hiwepy.cloud.message.email.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.message.email.dao.entities.MailtoxHistoryModel;
import io.hiwepy.cloud.message.email.service.IMailtoxHistoryService;
import io.hiwepy.cloud.message.email.setup.Constants;
import io.hiwepy.cloud.message.email.web.dto.MailtoxHistoryDTO;
import io.hiwepy.cloud.message.email.web.dto.MailtoxHistoryPaginationDTO;
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

@Api(tags = "邮件发送：历史记录（Ok）")
@RestController
@RequestMapping(value = "/mailtox/history/")
public class MailtoxHistoryController extends BaseApiController {

    @Autowired
    private IMailtoxHistoryService mailtoxHistoryService;

    @ApiOperation(value = "分页查询邮件发送历史数据", notes = "分页查询邮件发送历史数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "paginationDTO", value = "筛选条件", dataType = "MailtoxHistoryPaginationDTO")
    })
    @PostMapping("list")
    @PreAuthorize("authenticated and hasAnyAuthority('mailtox-history:list','*') ")
    @ResponseBody
    public Result<MailtoxHistoryDTO> list(@Valid @RequestBody MailtoxHistoryPaginationDTO paginationDTO) {

        MailtoxHistoryModel model = getBeanMapper().map(paginationDTO, MailtoxHistoryModel.class);
        Page<MailtoxHistoryModel> pageResult = getMailtoxHistoryService().getPagedList(model);
        List<MailtoxHistoryDTO> retList = Lists.newArrayList();
        for (MailtoxHistoryModel MailtoxHistoryModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(MailtoxHistoryModel, MailtoxHistoryDTO.class));
        }

        return new Result<MailtoxHistoryDTO>(pageResult, retList);

    }

    @ApiOperation(value = "删除邮件发送历史数据", notes = "删除邮件发送历史数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "邮件发送历史数据ID,多个用,拼接", required = true, dataType = "String")
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "删除邮件发送历史数据", opt = BusinessType.DELETE)
    @GetMapping("delete")
    @PreAuthorize("authenticated and hasAnyAuthority('mailtox-history:delete','*') ")
    @ResponseBody
    public ApiRestResponse<String> delete(@RequestParam String ids) throws Exception {
        // 执行邮件发送历史数据删除操作
        List<String> idList = Lists.newArrayList(StringUtils.tokenizeToStringArray(ids));
        boolean result = getMailtoxHistoryService().removeBatchByIds(idList);
        if (result) {
            return success("mailtox.history.delete.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return fail("mailtox.history.delete.fail", result);
    }

    @ApiOperation(value = "查询邮件发送历史数据信息", notes = "根据ID查询邮件发送历史数据信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "邮件发送历史数据ID", dataType = "String")
    })
    @GetMapping("detail")
    @PreAuthorize("authenticated and hasAnyAuthority('mailtox-history:detail','*') ")
    @ResponseBody
    public ApiRestResponse<MailtoxHistoryDTO> detail(@RequestParam("id") String id) throws Exception {
        MailtoxHistoryModel model = getMailtoxHistoryService().getById(id);
        if (model == null) {
            return ApiRestResponse.fail(getMessage("mailtox.history.get.empty"));
        }
        return ApiRestResponse.success(getBeanMapper().map(model, MailtoxHistoryDTO.class));
    }

    public IMailtoxHistoryService getMailtoxHistoryService() {
        return mailtoxHistoryService;
    }

}
