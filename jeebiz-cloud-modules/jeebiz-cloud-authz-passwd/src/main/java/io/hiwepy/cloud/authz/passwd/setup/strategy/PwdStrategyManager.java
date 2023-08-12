package io.hiwepy.cloud.authz.passwd.setup.strategy;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PwdStrategyManager {

    protected ConcurrentMap<String, PwdVerifiStrategy> COMPLIED_PWD_VERIFI_STRATEGY = new ConcurrentHashMap<String, PwdVerifiStrategy>();
    protected ConcurrentMap<String, PwdRetakeStrategy> COMPLIED_PWD_RETAKE_STRATEGY = new ConcurrentHashMap<String, PwdRetakeStrategy>();

    public PwdStrategyManager() {

    }

    public Set<String> verifiStrategys() {
        return COMPLIED_PWD_VERIFI_STRATEGY.keySet();
    }

    public Set<String> retakeStrategys() {
        return COMPLIED_PWD_RETAKE_STRATEGY.keySet();
    }

    /**
     * 注册账号核实字段验证策略接口
     */
    public void register(PwdVerifiStrategy strategy) {
        if (strategy != null) {
            COMPLIED_PWD_VERIFI_STRATEGY.putIfAbsent(strategy.name(), strategy);
        }
    }

    /**
     * 根据字段名称查找账号核实字段验证策略实现对象
     */
    public PwdVerifiStrategy getPwdVerifiStrategy(String strategy) {
        if (strategy != null) {
            PwdVerifiStrategy ret = COMPLIED_PWD_VERIFI_STRATEGY.get(strategy);
            if (ret != null) {
                return ret;
            }
        }
        return COMPLIED_PWD_VERIFI_STRATEGY.get(PwdStrategy.DEFAULT_STRATEGY);
    }

    /**
     * 注册密码找回策略接口
     */
    public void register(PwdRetakeStrategy strategy) {
        if (strategy != null) {
            COMPLIED_PWD_RETAKE_STRATEGY.putIfAbsent(strategy.name(), strategy);
        }
    }

    /**
     * 根据策略名称查找密码找回策略实现对象
     */
    public PwdRetakeStrategy getPwdRetakeStrategy(String strategy) {
        if (strategy != null) {
            PwdRetakeStrategy ret = COMPLIED_PWD_RETAKE_STRATEGY.get(strategy);
            if (ret != null) {
                return ret;
            }
        }
        return COMPLIED_PWD_RETAKE_STRATEGY.get(PwdStrategy.DEFAULT_STRATEGY);
    }

}
