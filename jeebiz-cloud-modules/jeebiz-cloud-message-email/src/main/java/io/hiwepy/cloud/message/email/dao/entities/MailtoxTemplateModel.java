package io.hiwepy.cloud.message.email.dao.entities;

import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Alias("MailtoxTemplateModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class MailtoxTemplateModel extends PaginationEntity<MailtoxTemplateModel> {

    /**
     * 邮件模板ID编号
     */
    protected String id;
    /**
     * 邮件模板创建ID
     */
    protected String uid;
    /**
     * 邮件优先级(1:紧急 3:普通 5:低)
     */
    protected String priority;
    /**
     * 邮件主题
     */
    protected String subject;
    /**
     * 邮件内容是否是html
     */
    protected boolean html = false;
    /**
     * 邮件内容,普通文本或者html
     */
    protected String content;
    /**
     * 发件人名称和邮箱
     */
    protected String from;
    /**
     * 收件人名称和邮箱
     */
    protected String mailto;
    /**
     * 抄送人名称和邮箱
     */
    protected String mailcc;
    /**
     * 密送人名称和邮箱
     */
    protected String mailBcc;
    /**
     * 邮件模板创建时间
     */
    private String time24;


}
