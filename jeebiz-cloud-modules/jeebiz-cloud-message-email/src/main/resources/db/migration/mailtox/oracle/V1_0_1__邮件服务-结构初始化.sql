-- create table
create table t_data_mailtox_history
(
    mail_id varchar2 (32) default sys_guid() not null,
    mail_uid varchar2 (32) not null,
    mail_addr varchar2 (50) not null,
    mail_priority varchar2 (50) not null,
    mail_subject varchar2 (100) not null,
    mail_html number (1) not null,
    mail_from varchar2 (500),
    mail_content clob,
    mail_to varchar2 (500),
    mail_cc varchar2 (500),
    mail_bcc varchar2 (500),
    time24 varchar2 (32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
    constraint t_data_mailtox_history_pk primary key (mail_id)
);
-- add comments to the table
comment on table t_data_mailtox_history  is '邮件发送记录表';
-- add comments to the columns
comment on column t_data_mailtox_history.mail_id is '邮件发送记录id编号';
comment on column t_data_mailtox_history.mail_uid is '邮件发送人id';
comment on column t_data_mailtox_history.mail_addr is '邮件发送请求来源ip地址';
comment on column t_data_mailtox_history.mail_priority is '邮件优先级(1:紧急 3:普通 5:低)';
comment on column t_data_mailtox_history.mail_subject is '邮件主题';
comment on column t_data_mailtox_history.mail_html is '邮件内容是否是html';
comment on column t_data_mailtox_history.mail_from is '发件人名称和邮箱';
comment on column t_data_mailtox_history.mail_content is '邮件内容,普通文本或者html';
comment on column t_data_mailtox_history.mail_to is '收件人名称和邮箱';
comment on column t_data_mailtox_history.mail_cc is '抄送人名称和邮箱';
comment on column t_data_mailtox_history.mail_bcc is '密送人名称和邮箱';
comment on column t_data_mailtox_history.time24 is '邮件发送时间';

-- create table
create table t_data_mailtox_settings
(
    s_id varchar2 (32) default sys_guid() not null,
    s_emaill varchar2 (200) not null,
    s_passwd varchar2 (50) not null,
    s_domain varchar2 (50) not null,
    s_addr varchar2 (100) not null,
    s_port varchar2 (10) not null,
    s_timeout number (10) default 25000,
    s_remark varchar2 (500) not null,
    s_protocol varchar2 (10) not null,
    s_useauth number (1) default 0,
    s_usessl number (1) default 0,
    s_status number (1) default 0,
    s_debug number (1) default 0,
    constraint t_data_mailtox_settings_pk primary key (s_id)
);
-- add comments to the table
comment on table t_data_mailtox_settings  is '邮件服务配置信息表';
-- add comments to the columns
comment on column t_data_mailtox_settings.s_id is '邮件服务设置id';
comment on column t_data_mailtox_settings.s_emaill is '发送邮件账号';
comment on column t_data_mailtox_settings.s_passwd is '发送邮件账号密码';
comment on column t_data_mailtox_settings.s_domain is '邮件服务器根域名';
comment on column t_data_mailtox_settings.s_addr is '邮件服务器地址';
comment on column t_data_mailtox_settings.s_port is '邮件服务器默认端口';
comment on column t_data_mailtox_settings.s_timeout is '发送超时时间，默认25000';
comment on column t_data_mailtox_settings.s_remark is '邮件服务器备注信息';
comment on column t_data_mailtox_settings.s_protocol is '邮件协议: smtp、nntp、pop3、pop4、imap';
comment on column t_data_mailtox_settings.s_useauth is '是否要求身份认证 (1:验证;0:不验证)';
comment on column t_data_mailtox_settings.s_usessl is '使用ssl加密通信(1:使用;0:不使用)';
comment on column t_data_mailtox_settings.s_status is '邮箱主机配置启用状态标记，1：启用，0：停用';
comment on column t_data_mailtox_settings.s_debug is '是否开启邮件发送debug模式；用于调试，生产环境请设置为0，1：启用，0：停用';
