package io.hiwepy.cloud.authz.passwd.setup.provider.def;

import hitool.mail.conf.EmailBody;
import io.hiwepy.cloud.authz.passwd.setup.Constants;
import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeDto;
import io.hiwepy.cloud.authz.passwd.setup.provider.CaptchaOutputProvider;
import io.hiwepy.cloud.authz.passwd.setup.provider.PwdPropertiesProvider;
import io.hiwepy.cloud.authz.passwd.setup.strategy.PwdStrategy;
import io.hiwepy.cloud.authz.passwd.utils.PatternFormatUtils;
import io.hiwepy.cloud.message.email.service.IMailtoxSendService;

import java.util.Properties;

/**
 * 邮件发送服务提供者
 */
public class CaptchaEmailOutputProvider implements CaptchaOutputProvider {

    protected final IMailtoxSendService mailtoxSendService;

    protected PwdPropertiesProvider propsProvider = null;

    public CaptchaEmailOutputProvider(IMailtoxSendService mailtoxSendService) {
        super();
        this.mailtoxSendService = mailtoxSendService;
    }

    @Override
    public String name() {
        return this.getClass().getName();
    }

    @Override
    public boolean output(PwdRetakeDto dto) throws Exception {

        Properties props = getPropsProvider().props();

        //检查参数配置
        if (!props.containsKey(Constants.PWD_MAIL_SUBJECT)) {
            throw new IllegalArgumentException("密码找回参数: " + Constants.PWD_MAIL_SUBJECT + " not found. ");
        }
        if (!props.containsKey(Constants.PWD_MAIL_CONTENT)) {
            throw new IllegalArgumentException("密码找回参数: " + Constants.PWD_MAIL_CONTENT + " not found. ");
        }

        // 构造邮件载体
        EmailBody email = new EmailBody();

        // 设置收件人的邮箱
        String sendTo = (String) dto.getData().get(PwdStrategy.PWD_RETAKE_BY_EMAIL);
        email.setMailto(sendTo, sendTo);

        // 设置邮件标题
        email.setSubject(props.getProperty(Constants.PWD_MAIL_SUBJECT));

        // 设置邮件的内容体
        String content = PatternFormatUtils.format(props.getProperty(Constants.PWD_MAIL_CONTENT), dto.getData());
        email.setContent(content);

        return getMailtoxSendService().send(email, false, dto.getAddr());
    }

    public PwdPropertiesProvider getPropsProvider() {
        return propsProvider;
    }

    public void setPropsProvider(PwdPropertiesProvider propsProvider) {
        this.propsProvider = propsProvider;
    }

    public IMailtoxSendService getMailtoxSendService() {
        return mailtoxSendService;
    }

}
