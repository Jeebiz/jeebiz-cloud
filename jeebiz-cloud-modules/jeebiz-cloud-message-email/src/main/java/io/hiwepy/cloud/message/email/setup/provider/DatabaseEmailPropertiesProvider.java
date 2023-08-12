package io.hiwepy.cloud.message.email.setup.provider;

import hitool.mail.JavaMailKey;
import hitool.mail.provider.def.DefaultEmailPropertiesProvider;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.utils.CollectionUtils;
import io.hiwepy.cloud.message.email.service.IMailtoxSettingsService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 整合系统参数设置表和邮箱服务配置表数据，为邮件发送服务提供配置支持
 */
public class DatabaseEmailPropertiesProvider extends DefaultEmailPropertiesProvider {

    /**
     * 要从数据库提取的属性值key
     */
    protected List<String> propKeys = new ArrayList<String>();

    protected final IMailtoxSettingsService mailtoxSettingsService;

    public DatabaseEmailPropertiesProvider(IMailtoxSettingsService mailtoxSettingsService) {
        super();
        this.mailtoxSettingsService = mailtoxSettingsService;
    }

    @Override
    public Properties props() {
        //调用父类方法
        super.props();
        //根据给出的key查找数据库存储的参数
        if (getPropKeys() != null) {
            for (String key : getPropKeys()) {
                props.setProperty(key, StringUtils.trimToEmpty(getMailtoxSettingsService().getValue(key)));
            }
        }

        try {

            List<PairModel> pairList = getMailtoxSettingsService().getPairValues("");
            Map<String, Object> pairMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(pairList)) {
                for (PairModel pairModel : pairList) {
                    pairMap.put(pairModel.getKey(), StringUtils.trimToNull(pairModel.getValue()));
                }
            }

            String mail_user = MapUtils.getString(pairMap, JavaMailKey.MAIL_USER);
            String mail_password = MapUtils.getString(pairMap, JavaMailKey.MAIL_PASSWORD);
            String mail_host = MapUtils.getString(pairMap, JavaMailKey.MAIL_HOST);
            String mail_port = MapUtils.getString(pairMap, JavaMailKey.MAIL_PORT);

            props.setProperty(JavaMailKey.MAIL_HOST, mail_host);
            props.setProperty(JavaMailKey.MAIL_FROM_DESC, MapUtils.getString(pairMap, JavaMailKey.MAIL_FROM_DESC));
            props.setProperty(JavaMailKey.MAIL_PORT, mail_port);
            props.setProperty(JavaMailKey.MAIL_USER, mail_user);
            props.setProperty(JavaMailKey.MAIL_PASSWORD, mail_password);

            props.setProperty(JavaMailKey.MAIL_SMTP_HOST, mail_host);
            props.setProperty(JavaMailKey.MAIL_SMTP_PORT, mail_port);
            //commons-mail需要设置mail.smtp.user和mail.smtp.password，才能正常发送邮件
            props.setProperty(JavaMailKey.MAIL_SMTP_USER, mail_user);
            props.setProperty(JavaMailKey.MAIL_SMTP_PASSWORD, mail_password);

            props.setProperty(JavaMailKey.MAIL_SMTP_AUTH, MapUtils.getBoolean(pairMap, JavaMailKey.MAIL_SMTP_AUTH).toString());
            props.setProperty(JavaMailKey.MAIL_SMTP_TIMEOUT, MapUtils.getString(pairMap, JavaMailKey.MAIL_SMTP_TIMEOUT));
            props.setProperty(JavaMailKey.MAIL_TRANSPORT_PROTOCOL, MapUtils.getString(pairMap, JavaMailKey.MAIL_TRANSPORT_PROTOCOL));
            props.setProperty(JavaMailKey.MAIL_SMTP_SSL_ENABLE, MapUtils.getBoolean(pairMap, JavaMailKey.MAIL_SMTP_SSL_ENABLE).toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public List<String> getPropKeys() {
        return propKeys;
    }

    public void setPropKeys(List<String> propKeys) {
        this.propKeys = propKeys;
    }

    public IMailtoxSettingsService getMailtoxSettingsService() {
        return mailtoxSettingsService;
    }

}
