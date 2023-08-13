-- ----------------------------
-- Table structure for 发送短信流水表
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_flow`;
CREATE TABLE `t_sms_flow`
(
    `id`             bigint(12)   NOT NULL AUTO_INCREMENT,
    `app_id`         varchar(50) DEFAULT NULL COMMENT '客户端应用ID',
    `app_channel`    varchar(20) DEFAULT NULL COMMENT '客户端应用渠道编码',
    `app_version`    varchar(50) DEFAULT NULL COMMENT '客户端版本',
    `user_id`        bigint(11)  DEFAULT NULL COMMENT '发起发送短信的用户id',
    `user_code`      varchar(64) DEFAULT NULL COMMENT '发起发送短信的用户code',
    `source_ip`      varchar(64)  NOT NULL COMMENT '发送短信请求来源IP地址',
    `source_country` varchar(64)  NOT NULL COMMENT '发送短信请求来源国家',
    `request_id`     varchar(64) DEFAULT NULL COMMENT '短信三方请求ID',
    `biz_id`         varchar(64)  NOT NULL COMMENT '短信业务ID',
    `sms_no`         varchar(64)  NOT NULL COMMENT '短信流水号,32个字符内',
    `sms_channel`    varchar(20)  NOT NULL COMMENT '短信渠道编码',
    `sms_content`    varchar(200) NOT NULL COMMENT '短信内容',
    `sms_desc`       varchar(200) NOT NULL COMMENT '短信流水描述',
    `is_delete`      tinyint(2)  DEFAULT 0 COMMENT '是否逻辑删除（1：是，0：否）',
    `creator`        bigint(12)  DEFAULT NULL COMMENT '发送短信创建人ID',
    `create_time`    datetime    DEFAULT NULL COMMENT '发送短信发送时间',
    `modifyer`       bigint(12)  DEFAULT NULL COMMENT '发送短信修改人ID',
    `modify_time`    datetime    DEFAULT NULL COMMENT '发送短信更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_sms_no` (`sms_no`) USING BTREE,
    KEY `idx_sms_user` (`sms_no`, `user_id`, `user_code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='发送短信流水表';
