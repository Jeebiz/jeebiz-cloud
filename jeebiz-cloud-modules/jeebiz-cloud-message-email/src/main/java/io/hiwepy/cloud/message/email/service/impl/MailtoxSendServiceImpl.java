package io.hiwepy.cloud.message.email.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.dozermapper.core.Mapper;
import hitool.mail.JavaMailClientAdapter;
import hitool.mail.conf.EmailBody;
import io.hiwepy.cloud.message.email.dao.IMailtoxHistoryDao;
import io.hiwepy.cloud.message.email.dao.entities.MailtoxHistoryModel;
import io.hiwepy.cloud.message.email.service.IMailtoxSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.stereotype.Service;

@Service
public class MailtoxSendServiceImpl implements IMailtoxSendService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected Mapper beanMapper;
    @Autowired
    private JavaMailClientAdapter mailClient;
    @Autowired
    private IMailtoxHistoryDao mailtoxHistoryDao;

    @Override
    public boolean send(EmailBody email, boolean html, String addr) throws Exception {

        // 设置发件人信息
        //email.setFrom(props.getProperty(JavaMailKey.MAIL_FROM_DESC), props.getProperty(JavaMailKey.MAIL_USER), false);

        boolean status = html ? getMailClient().sendMime(email) : getMailClient().sendText(email);
        if (status) {
            SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
            MailtoxHistoryModel model = new MailtoxHistoryModel();
            model.setAddr(addr);
            model.setPriority(email.getPriority());
            model.setSubject(email.getSubject());
            model.setContent(email.getContent());
            model.setFrom(JSONObject.toJSONString(email.getFrom()));
            model.setHtml(html);
            model.setMailto(JSONObject.toJSONString(email.getMailto()));
            model.setMailcc(JSONObject.toJSONString(email.getMailcc()));
            model.setMailBcc(JSONObject.toJSONString(email.getMailBcc()));
            model.setUid(principal.getUid());
            getMailtoxHistoryDao().insert(model);
            logger.debug("邮件已发送，历史记录成功！");

        }

        return status;
    }

    public Mapper getBeanMapper() {
        return beanMapper;
    }

    public JavaMailClientAdapter getMailClient() {
        return mailClient;
    }

    public IMailtoxHistoryDao getMailtoxHistoryDao() {
        return mailtoxHistoryDao;
    }

}
