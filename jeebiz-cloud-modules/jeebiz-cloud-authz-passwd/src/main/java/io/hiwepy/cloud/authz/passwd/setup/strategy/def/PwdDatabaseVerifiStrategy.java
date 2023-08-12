package io.hiwepy.cloud.authz.passwd.setup.strategy.def;

import io.hiwepy.boot.api.dao.entities.BaseMap;
import io.hiwepy.cloud.authz.passwd.dao.entities.PwdRetakeVerifiModel;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeVerifiService;
import io.hiwepy.cloud.authz.passwd.setup.Constants;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeResult;
import io.hiwepy.cloud.authz.passwd.setup.provider.AccountInputProvider;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdStrategy;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdVerifiStrategy;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeVerifiPairDTO;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeVerifiWrapDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于数据库存储的账号核实字段验证策略实现
 */
public class PwdDatabaseVerifiStrategy implements PwdVerifiStrategy {

    protected Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected final IPwdRetakeVerifiService pwdRetakeVerifiService;
    protected final AccountInputProvider accountProvider;

    public PwdDatabaseVerifiStrategy(IPwdRetakeVerifiService pwdRetakeVerifiService,
                                     AccountInputProvider accountProvider) {
        super();
        this.pwdRetakeVerifiService = pwdRetakeVerifiService;
        this.accountProvider = accountProvider;
    }

    @Override
    public String name() {
        return PwdStrategy.DEFAULT_STRATEGY;
    }

    @Override
    public PwdRetakeResult verifi(PwdRetakeVerifiWrapDTO verifiDTO) {

        // 用户信息；有可能没有
        PwdRetakeDto dto = new PwdRetakeDto();
        dto.setUsername(verifiDTO.getUsername());
        dto.setUuid(verifiDTO.getUuid());
        BaseMap rtMap = getAccountProvider().input(dto);
        // 第一步：验证用户是否存在
        if (null == rtMap) {
            return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_RETAKE_INPUT_NULL);
        }
        if (CollectionUtils.isEmpty(verifiDTO.getList())) {
            return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_RETAKE_INPUT_NULL, rtMap);
        }
        // 第二步：验证附加信息
        try {

            // 查询启用的身份校验字段
            List<PwdRetakeVerifiModel> verifiList = getPwdRetakeVerifiService().getVerifiList();
            List<String> verifis = verifiDTO.getList().stream().map(mapper -> mapper.getName()).collect(Collectors.toList());
            // 第二步-1：通过数据库存储的字段来审查页面提交的字段防止伪造异常请求
            for (PwdRetakeVerifiModel verifi : verifiList) {
                // 需要提供的验证信息没有提供
                if (!CollectionUtils.contains(verifis.iterator(), verifi.getName())) {
                    return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_RETAKE_VERIFI_REQUIRED, rtMap);
                }
            }
            // 第二步-2：对提交的信息进行对比
            for (PwdRetakeVerifiPairDTO pairDTO : verifiDTO.getList()) {
                // 获取相应字段原值（有可能未定义）
                String origin = (String) rtMap.get(pairDTO.getName());
                // 获取对应字段参数值
                String from = pairDTO.getValue();
                //对比字段可能为空的情况处理
                if (StringUtils.isEmpty(from) || StringUtils.isEmpty(origin)) {
                    return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_RETAKE_VERIFI_REQUIRED, rtMap);
                }
                if (!from.equals(origin)) {
                    return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_RETAKE_VERIFI_UNPASS, rtMap);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return PwdRetakeResult.to(Constants.FAIL, Constants.PWD_RETAKE_VERIFI_ERROR, rtMap);
        }
        return PwdRetakeResult.to(Constants.SUCCESS, Constants.PWD_RETAKE_VERIFI_PASS, rtMap);
    }

    public AccountInputProvider getAccountProvider() {
        return accountProvider;
    }

    public IPwdRetakeVerifiService getPwdRetakeVerifiService() {
        return pwdRetakeVerifiService;
    }

}
