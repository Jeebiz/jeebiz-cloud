package io.hiwepy.cloud.sample.setup.provider;

import io.hiwepy.boot.api.provider.KeyValueProvider;
import io.hiwepy.boot.api.provider.PropertiesProvider;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 整合系统参数设置表数据为常用的系统参数配置
 */
public class OracleMultiPropertiesProvider implements PropertiesProvider {

    /**
     * 基于配置文件的默认配置
     */
    protected Properties defaultProps = new Properties();
    /**
     * 默认配置
     */
    protected Properties props = new Properties();
    /**
     * 要从数据库提取的属性值key
     */
    protected List<String> propKeys = new ArrayList<String>();

    /**
     * 多参数获取接口
     */
    @Resource(name = "keyValuesProvider")
    protected KeyValueProvider<Map<String, String>> keyValuesProvider;

    @Override
    public boolean set(String key, String value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Properties props() {

        // 清除参数
        props.clear();
        // 默认参数配置
        props.putAll(this.getDefaultProps());

        // 根据给出的key查找数据库存储的参数
        if (getPropKeys() != null) {
            for (String key : getPropKeys()) {
                // 此处因为结果中可能包含null值不能使用 props.putAll(t);
                Map<String, String> values = getKeyValuesProvider().get(key);
                if (null != values && !values.isEmpty()) {
                    for (String key2 : values.keySet()) {
                        props.setProperty(key2, StringUtils.trimToNull(values.get(key2)));
                    }
                }
            }
        }

        return props;
    }

    public List<String> getPropKeys() {
        return propKeys;
    }

    public void setPropKeys(List<String> propKeys) {
        this.propKeys = propKeys;
    }

    public KeyValueProvider<Map<String, String>> getKeyValuesProvider() {
        return keyValuesProvider;
    }

    public void setKeyValuesProvider(KeyValueProvider<Map<String, String>> keyValuesProvider) {
        this.keyValuesProvider = keyValuesProvider;
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


}
