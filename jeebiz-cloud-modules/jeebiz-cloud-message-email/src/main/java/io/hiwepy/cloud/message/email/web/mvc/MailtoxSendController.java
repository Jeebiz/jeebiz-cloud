package io.hiwepy.cloud.message.email.web.mvc;

import hitool.mail.conf.EmailBody;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.cloud.message.email.service.IMailtoxSendService;
import io.hiwepy.cloud.message.email.setup.Constants;
import io.hiwepy.cloud.message.email.web.dto.MailtoxSendDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.WebUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "邮件发送：发送（Ok）")
@RestController
@RequestMapping(value = "/mailtox/")
public class MailtoxSendController extends BaseApiController {

    @Autowired
    private IMailtoxSendService mailtoxSendService;

    @ApiOperation(value = "发送邮件", notes = "发送邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "sendDTO", value = "用户信息", dataType = "MailtoxSendDTO")
    })
    @ApiOperationLog(module = Constants.EXTRAS_MAILTOX, business = "发生邮件", opt = BusinessType.EMAIL)
    @PostMapping("send")
    @PreAuthorize("authenticated")
    @ResponseBody
    public ApiRestResponse<String> send(@Valid @RequestBody MailtoxSendDTO sendDTO, HttpServletRequest request) throws Exception {
        EmailBody email = getBeanMapper().map(sendDTO, EmailBody.class);
        boolean status = getMailtoxSendService().send(email, sendDTO.isHtml(), WebUtils.getRemoteAddr(request));
        if (status) {
            return success("email.send.success");
        }
        return fail("email.send.fail");
    }

    public IMailtoxSendService getMailtoxSendService() {
        return mailtoxSendService;
    }


}
