SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_DATA_MAILTOX_HISTORY
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_MAILTOX_HISTORY`;
CREATE TABLE `t_DATA_MAILTOX_HISTORY`
(
    `MAIL_ID`       bigint(11)   NOT NULL AUTO_INCREMENT COMMENT '邮件发送记录ID编号',
    `MAIL_UID`      bigint(11)   NOT NULL COMMENT '邮件发送人ID',
    `MAIL_ADDR`     varchar(50)       DEFAULT NULL COMMENT '邮件发送请求来源IP地址',
    `MAIL_PRIORITY` varchar(1)   NOT NULL COMMENT '邮件优先级(1:紧急 3:普通 5:低)',
    `MAIL_SUBJECT`  varchar(100) NOT NULL COMMENT '邮件主题',
    `MAIL_HTML`     varchar(50)  NOT NULL COMMENT '邮件内容是否是html',
    `MAIL_FROM`     varchar(500) NOT NULL COMMENT '发件人名称和邮箱',
    `MAIL_CONTENT`  tinytext COMMENT '邮件内容,普通文本或者html',
    `MAIL_TO`       varchar(500)      DEFAULT NULL COMMENT '收件人名称和邮箱',
    `MAIL_CC`       varchar(500)      DEFAULT NULL COMMENT '抄送人名称和邮箱',
    `MAIL_BCC`      varchar(500)      DEFAULT NULL COMMENT '密送人名称和邮箱',
    `TIME24`        timestamp    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '邮件发送时间',
    PRIMARY KEY (`MAIL_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='邮件发送记录表';

-- ----------------------------
-- Table structure for t_DATA_MAILTOX_SETTINGS
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_MAILTOX_SETTINGS`;
CREATE TABLE `t_DATA_MAILTOX_SETTINGS`
(
    `S_ID`       bigint(11)   NOT NULL AUTO_INCREMENT COMMENT '邮件服务设置ID',
    `S_EMAILL`   varchar(200) NOT NULL COMMENT '发送邮件账号',
    `S_PASSWD`   varchar(50)  NOT NULL COMMENT '发送邮件账号密码',
    `S_DOMAIN`   varchar(50)  NOT NULL COMMENT '邮件服务器根域名',
    `S_ADDR`     varchar(100) NOT NULL COMMENT '邮件服务器地址',
    `S_PORT`     varchar(10)  NOT NULL COMMENT '邮件服务器默认端口',
    `S_TIMEOUT`  bigint(11)  DEFAULT 25000 COMMENT '发送超时时间，默认25000',
    `S_REMARK`   varchar(500) COMMENT '邮件服务器备注信息',
    `S_PROTOCOL` varchar(10) DEFAULT NULL COMMENT '邮件协议: smtp、nntp、pop3、pop4、imap',
    `S_USEAUTH`  int(1)      DEFAULT 0 COMMENT '是否要求身份认证 (1:验证;0:不验证)',
    `S_USESSL`   int(1)      DEFAULT 0 COMMENT '使用SSL加密通信(1:使用;0:不使用)',
    `S_STATUS`   int(1)      DEFAULT 0 COMMENT '邮箱主机配置启用状态标记，1：启用，0：停用',
    `S_DEBUG`    int(1)      DEFAULT 0 COMMENT '是否开启邮件发送Debug模式；用于调试，生产环境请设置为0，1：启用，0：停用',
    PRIMARY KEY (`S_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='邮件服务配置信息表';

