package io.hiwepy.cloud.message.email.dao.entities;

import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Alias("MailtoxSettingsModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class MailtoxSettingsModel extends PaginationEntity<MailtoxSettingsModel> {

    /**
     * 邮件服务设置ID
     */
    protected String id;
    /**
     * 发送邮件账号
     */
    protected String eamil;
    /**
     * 发送邮件账号密码
     */
    protected String passwd;
    /**
     * 邮件服务器根域名
     */
    protected String domain;
    /**
     * 邮件服务器地址
     */
    protected String addr;
    /**
     * 邮件服务器默认端口
     */
    protected String port = "25";
    /**
     * 发送超时时间，默认25000
     */
    protected String timeout;
    /**
     * 邮件服务器备注信息
     */
    protected String remark;
    /**
     * 邮件协议 smtp、nntp、pop3、pop4、imap
     */
    protected String protocol = "smtp";
    /**
     * 是否要求身份认证 (1:验证;0:不验证)
     */
    protected String useauth = "0";
    /**
     * 使用SSL加密通信(1:使用;0:不使用)
     */
    protected String usessl = "0";
    /**
     * 邮箱主机配置启用状态标记，1：启用，0：停用
     */
    protected String status;
    /**
     * 关键词搜索
     */
    private String keywords;

}
