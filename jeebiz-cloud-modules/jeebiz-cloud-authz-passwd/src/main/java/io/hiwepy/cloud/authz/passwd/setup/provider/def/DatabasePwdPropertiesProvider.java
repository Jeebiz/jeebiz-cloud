package io.hiwepy.cloud.authz.passwd.setup.provider.def;

import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.utils.CollectionUtils;
import io.hiwepy.cloud.authz.passwd.service.IPwdRetakeSettingsService;
import io.hiwepy.cloud.authz.passwd.setup.provider.PwdPropertiesProvider;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Properties;

/**
 * 整合系统参数设置表数据为常用的系统参数配置
 */
public class DatabasePwdPropertiesProvider implements PwdPropertiesProvider {

    /**
     * 基于配置文件的默认配置
     */
    protected Properties defaultProps = new Properties();
    /**
     * 默认配置
     */
    protected Properties props = new Properties();

    protected final IPwdRetakeSettingsService pwdRetakeSettingsService;

    public DatabasePwdPropertiesProvider(IPwdRetakeSettingsService pwdRetakeSettingsService) {
        super();
        this.pwdRetakeSettingsService = pwdRetakeSettingsService;
    }

    @Override
    public Properties props() {

        // 清除参数
        props.clear();
        // 默认参数配置
        props.putAll(this.getDefaultProps());

        List<PairModel> pairList = getPwdRetakeSettingsService().getPairValues("");
        if (!CollectionUtils.isEmpty(pairList)) {
            for (PairModel pairModel : pairList) {
                props.setProperty(pairModel.getKey(), StringUtils.trimToNull(pairModel.getValue()));

            }
        }
        return props;
    }

    public Properties getProps() {
        return props;
    }

    @Override
    public void setProps(Properties props) {
        this.defaultProps = props;
    }

    public Properties getDefaultProps() {
        //系统配置参数
        defaultProps.putAll(System.getProperties());
        return defaultProps;
    }

    public void setDefaultProps(Properties defaultProps) {
        this.defaultProps = defaultProps;
    }

    public IPwdRetakeSettingsService getPwdRetakeSettingsService() {
        return pwdRetakeSettingsService;
    }

}
