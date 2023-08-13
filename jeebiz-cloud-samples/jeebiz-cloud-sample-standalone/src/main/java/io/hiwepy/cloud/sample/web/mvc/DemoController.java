/**
 *
 */
package io.hiwepy.cloud.sample.web.mvc;

import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.web.BaseMapperController;
import io.hiwepy.cloud.sample.dao.entities.DemoEntity;
import io.hiwepy.cloud.sample.service.IDemoService;
import io.hiwepy.cloud.sample.web.dto.DemoDTO;
import io.hiwepy.cloud.sample.web.dto.DemoNewDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Demo示例表 前端控制器
 * </p>
 *
 * @author wandl
 * @since 2023-08-06
 */
@RestController
@RequestMapping("demo")
public class DemoController extends BaseMapperController {

    @Autowired
    private IDemoService demoService;

    /**
     * 增加逻辑实现
     */
    @ApiOperation(value = "创建xxx信息", notes = "根据DemoVo创建xxx", httpMethod = "POST")
    @ApiImplicitParam(name = "demoVo", value = "xxx数据传输对象", required = true, dataType = "DemoNewDTO")
    @PostMapping("new")
    @ResponseBody
    public ApiRestResponse<String> newDemo(@Valid DemoNewDTO dto) {
        try {

            DemoEntity entity = new DemoEntity();

            //如果自己较少，采用手动设置方式
            entity.setName(dto.getName());
            //如果自动较多，采用对象拷贝方式；该方式不支持文件对象拷贝
            //PropertyUtils.copyProperties(DemoEntity, demoVo);

            getDemoService().save(entity);
            return success("demo.new.success");
        } catch (Exception e) {
            logException(this, e);
            return fail("demo.new.fail");
        }
    }

    /**
     * 修改逻辑实现
     */
    @ApiOperation(value = "修改xxx信息", notes = "修改xxx", httpMethod = "POST")
    @ApiImplicitParam(name = "demoVo", value = "xxx数据传输对象", required = true, dataType = "DemoVo")
    @PostMapping("renew")
    @ResponseBody
    public ApiRestResponse<String> renew(@Valid DemoDTO demoVo) throws Exception {
        try {

            DemoEntity entity = new DemoEntity();

            //如果自己较少，采用手动设置方式
            entity.setName(demoVo.getName());
            //如果自动较多，采用对象拷贝方式；该方式不支持文件对象拷贝
            //PropertyUtils.copyProperties(DemoEntity, demoVo);

            getDemoService().updateById(entity);
            return success("demo.renew.success");
        } catch (Exception e) {
            logException(this, e);
            return fail("demo.renew.fail");
        }
    }

    /**
     * 删除逻辑实现
     */
    @ApiOperation(value = "删除xxx信息", notes = "根据ID删除xxx", httpMethod = "POST")
    @ApiImplicitParam(name = "ids", value = "ID集合，多个使用,拼接", required = true, dataType = "String")
    @PostMapping("delete")
    @ResponseBody
    public ApiRestResponse<String> delete(@RequestParam(value = "ids") String ids, HttpServletRequest request) throws Exception {
        try {
            if (ObjectUtils.isEmpty(ids)) {
                return fail("demo.delete.fail");
            }
            List<String> list = Arrays.asList(StringUtils.tokenizeToStringArray(ids));
            // 批量删除数据库配置记录
            getDemoService().removeBatchByIds(list);
            return success("demo.delete.success");
        } catch (Exception e) {
            logException(this, e);
            return fail("demo.delete.fail");
        }
    }


    public IDemoService getDemoService() {
        return demoService;
    }

    public void setDemoService(IDemoService demoService) {
        this.demoService = demoService;
    }

}
