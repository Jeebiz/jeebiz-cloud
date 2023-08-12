package io.hiwepy.cloud.message.email.dao.entities;

import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Alias("MailtoxHistoryModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class MailtoxHistoryModel extends PaginationEntity<MailtoxHistoryModel> {

    /**
     * 邮件发送记录ID编号
     */
    protected String id;
    /**
     * 邮件发送人ID
     */
    protected String uid;
    /**
     * 邮件发送请求来源IP地址
     */
    protected String addr;
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
     * 邮件发送时间
     */
    private String time24;
    /**
     * 筛选起始时间
     */
    private String begintime;
    /**
     * 筛选结束时间
     */
    private String endtime;

}
