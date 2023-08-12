package io.hiwepy.cloud.authz.passwd.web.mvc;

import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.boot.api.sequence.Sequence;
import io.hiwepy.boot.api.web.BaseApiController;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeStrategyModel;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeVerifiModel;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeStrategyService;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeVerifiService;
import io.hiwepy.cloud.authz.passwd.setup.Constants;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeResult;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdStrategy;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdStrategyManager;
import io.hiwepy.cloud.authz.passwd.web.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 找回密码控制器
 */
@Api(tags = "找回密码：业务控制器（Ok）")
@RestController
@RequestMapping(value = "/authz/passwd/retake/")
public class PwdRetakeController extends BaseApiController implements InitializingBean {

    @Autowired
    protected PwdStrategyManager strategyManager;
    @Autowired
    protected IPwdRetakeStrategyService strategyService;
    @Autowired
    protected IPwdRetakeVerifiService verifiService;
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;
    @Autowired
    protected Sequence sequence;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @ApiOperation(value = "第一步：准备密码找回前置数据：找回策略、核对信息", notes = "查询密码找回前置数据：找回策略、核对信息")
    @GetMapping("pre")
    @ResponseBody
    public ApiRestResponse<PwdRetakeBeforeDTO> strategys() throws Exception {

        PwdRetakeBeforeDTO beforeDTO = new PwdRetakeBeforeDTO();
        /*
         * 查询所有的找回策略
         */
        List<PwdRetakeStrategyModel> strategyList = getStrategyService().getStrategyList();
        if (!CollectionUtils.isEmpty(strategyList)) {

            List<PwdRetakeStrategyDTO> strategys = strategyList.stream().filter(strategy -> {
                        return getStrategyManager().retakeStrategys().contains(strategy.getKey());
                    })
                    .map(strategy -> getBeanMapper().map(strategy, PwdRetakeStrategyDTO.class))
                    .collect(Collectors.toList());

            beforeDTO.setStrategyList(strategys);

        }

        List<PwdRetakeVerifiModel> verifiList = getVerifiService().getVerifiList();
        if (!CollectionUtils.isEmpty(verifiList)) {
            List<PwdRetakeVerifiDTO> verifis = verifiList.stream()
                    .map(strategy -> getBeanMapper().map(strategy, PwdRetakeVerifiDTO.class))
                    .collect(Collectors.toList());
            beforeDTO.setVerifiList(verifis);
        }

        // 密码找回操作UID：作为后续操作的凭证
        String uuid = String.valueOf(sequence.nextId());
        beforeDTO.setUuid(uuid);

        // 清理Redis缓存
        String key = String.format("passwd:uuid:%s", uuid);
        if (getRedisTemplate().hasKey(key)) {
            getRedisTemplate().delete(key);
        }
        getRedisTemplate().opsForValue().set(key, true, Duration.ofMinutes(5));
        return ApiRestResponse.success(beforeDTO);

    }

    @ApiOperation(value = "第二步：账号信息核实（非必须）", notes = "通过核实要修改密码账号的隐私信息来防止他人篡改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "verifiDTO", value = "用户信息", dataType = "PwdRetakeVerifiWrapDTO")
    })
    @PostMapping("verifi")
    @ResponseBody
    public ApiRestResponse<String> verifi(@Valid @RequestBody PwdRetakeVerifiWrapDTO verifiDTO) {
        // 判断是否是有效的请求
        String key = String.format("passwd:uuid:%s", verifiDTO.getUuid());
        if (getRedisTemplate().hasKey(key)) {
            PwdRetakeResult result = getStrategyManager().getPwdVerifiStrategy(PwdStrategy.DEFAULT_STRATEGY).verifi(verifiDTO);
            // 如果验证通过
            if (STATUS_SUCCESS.equals(result.getStatus())) {
                return success(result.getMsgKey());
            }
            // 如果进入这里说明验证信息发送失败，需要进行异常处理
            return fail(result.getMsgKey());
        }
        return fail(Constants.PWD_RETAKE_INVALID_ACCESS);
    }

    @ApiOperation(value = "第三步：发送验证码（必须）", notes = "根据选择的找回方式发送邮件、验证码等")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "adviceDTO", value = "用户信息", dataType = "PwdRetakeAdviceDTO")
    })
    @PostMapping("advice")
    @ResponseBody
    public ApiRestResponse<String> advice(@Valid @RequestBody PwdRetakeAdviceDTO adviceDTO) {
        // 判断是否是有效的请求
        String key = String.format("passwd:uuid:%s", adviceDTO.getUuid());
        if (getRedisTemplate().hasKey(key)) {
            // 根据选择的密码找回策略，执行通知操作
            PwdRetakeResult result = getStrategyManager().getPwdRetakeStrategy(adviceDTO.getStrategy()).advice(adviceDTO);
            // 如果找回密码操作成功，表示验证信息已经发送成功，等待输入验证码
            if (STATUS_SUCCESS.equals(result.getStatus())) {
                return success(result.getMsgKey());
            }
            // 如果进入这里说明验证信息发送失败，需要进行异常处理
            return fail(result.getMsgKey());
        }
        return fail(Constants.PWD_RETAKE_INVALID_ACCESS);
    }

    @ApiOperation(value = "第四步：校验验证码（必须）", notes = "对提交的验证码有效性进行检查")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "adviceDTO", value = "用户信息", dataType = "PwdRetakeCheckDTO")
    })
    @PostMapping("check")
    @ResponseBody
    public ApiRestResponse<String> check(@Valid @RequestBody PwdRetakeCheckDTO checkDTO) {
        // 判断是否是有效的请求
        String key = String.format("passwd:uuid:%s", checkDTO.getUuid());
        if (getRedisTemplate().hasKey(key)) {
            PwdRetakeResult result = getStrategyManager().getPwdRetakeStrategy(checkDTO.getStrategy()).check(checkDTO);
            // 如果找回密码操作成功，表示验证信息已经发送成功，等待输入验证码
            if (STATUS_SUCCESS.equals(result.getStatus())) {
                return success(result.getMsgKey());
            }
            // 如果进入这里说明验证信息发送失败，需要进行异常处理
            return fail(result.getMsgKey());
        }
        return fail(Constants.PWD_RETAKE_INVALID_ACCESS);
    }

    @ApiOperation(value = "第五步：修改密码（必须）", notes = "指定该账号的新密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "verifiDTO", value = "用户信息", dataType = "PwdRetakeDoneDTO")
    })
    @PostMapping("done")
    @ResponseBody
    public ApiRestResponse<String> done(@Valid @RequestBody PwdRetakeDoneDTO doneDTO) {
        // 判断是否是有效的请求
        String key = String.format("passwd:uuid:%s", doneDTO.getUuid());
        if (getRedisTemplate().hasKey(key)) {
            PwdRetakeResult result = getStrategyManager().getPwdRetakeStrategy(doneDTO.getStrategy()).done(doneDTO);
            // 密码更新操作成功
            if (STATUS_SUCCESS.equals(result.getStatus())) {
                return success(result.getMsgKey());
            }
            // 如果进入这里说明验证信息发送失败，需要进行异常处理
            return fail(result.getMsgKey());
        }
        return fail(Constants.PWD_RETAKE_INVALID_ACCESS);
    }

    public PwdStrategyManager getStrategyManager() {
        return strategyManager;
    }

    public IPwdRetakeStrategyService getStrategyService() {
        return strategyService;
    }

    public IPwdRetakeVerifiService getVerifiService() {
        return verifiService;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

}
