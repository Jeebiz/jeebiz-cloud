package io.hiwepy.cloud.base.guard.web.mvc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.hiwepy.boot.api.annotation.ApiOperationLog;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.boot.api.web.Result;
import io.hiwepy.cloud.base.guard.dao.entities.AntisamyModel;
import io.hiwepy.cloud.base.guard.service.IAntisamyService;
import io.hiwepy.cloud.base.guard.web.dto.AntisamyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "消息通知管理")
@Controller
@RequestMapping("/extras/inform/")
public class AntisamyController extends BaseMapperController {

    @Autowired
    private IAntisamyService informService;

    @ApiOperation(value = "查询服务消息通知", notes = "分页查询服务消息通知")
    @ApiOperationLog(module = "", business = "分页查询服务消息通知", opt = BusinessType.SELECT)
    @PostMapping(value = "list")
    @ResponseBody
    public Object list(@Valid AntisamyModel paginationDTO)
            throws Exception {

        AntisamyModel model = getBeanMapper().map(paginationDTO, AntisamyModel.class);
        model.setUserid(SubjectUtils.getPrincipal(SecurityPrincipal.class).getUid());

        Page<AntisamyModel> pageResult = getInformService().getPagedList(model);
        List<AntisamyDTO> retList = new ArrayList<AntisamyDTO>();
        for (AntisamyModel registryModel : pageResult.getRecords()) {
            retList.add(getBeanMapper().map(registryModel, AntisamyDTO.class));
        }

        return new Result<AntisamyDTO>(pageResult, retList);

    }

    @ApiOperation(value = "待处理通知总数", notes = "查询待处理通知总数")
    @ApiOperationLog(module = "", business = "查询待处理通知总数", opt = BusinessType.SELECT)
    @PostMapping(value = "pending")
    @ResponseBody
    public Object pending() throws Exception {

        AntisamyModel model = new AntisamyModel();
        model.setUserid(SubjectUtils.getPrincipal(SecurityPrincipal.class).getUid());

        return getInformService().getCount(model);
    }


    @ApiOperation(value = "阅读消息通知", notes = "阅读消息通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务消息通知ID", required = true, dataType = "String"),
    })
    @ApiOperationLog(module = "", business = "服务消息通知", opt = BusinessType.UPDATE)
    @PostMapping(value = "read")
    @ResponseBody
    public Object read(@RequestParam String id) throws Exception {

        AntisamyModel model = new AntisamyModel();
        model.setId(id);
        model.setUserid(SubjectUtils.getPrincipal(SecurityPrincipal.class).getUid());
        model.setStatus("1");

        // 执行服务通知阅读操作
        boolean result = getInformService().updateById(model);
        if (result) {
            return success("inform.read.success");
        }
        // 逻辑代码，如果发生异常将不会被执行
        return success("inform.read.error", result);
    }


    @ApiOperation(value = "阅读消息通知", notes = "阅读消息通知")
    @ApiOperationLog(module = "", business = "阅读消息通知", opt = BusinessType.UPDATE)
    @PostMapping(value = "readall")
    @ResponseBody
    public Object readall() throws Exception {

        AntisamyModel model = new AntisamyModel();
        model.setUserid(SubjectUtils.getPrincipal(SecurityPrincipal.class).getUid());
        model.setStatus("1");

        // 执行服务通知阅读操作
        boolean result = getInformService().updateById(model);
        if (result) {
            return success("inform.readall.success", result);
        }
        // 逻辑代码，如果发生异常将不会被执行
        return success("inform.readall.error", result);
    }

    public IAntisamyService getInformService() {
        return informService;
    }

    public void setInformService(IAntisamyService informService) {
        this.informService = informService;
    }

}
