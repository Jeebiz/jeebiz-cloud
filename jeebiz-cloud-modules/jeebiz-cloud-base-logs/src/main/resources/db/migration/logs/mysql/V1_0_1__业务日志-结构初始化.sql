-- ----------------------------
-- Table structure for t_log_authz
-- ----------------------------
DROP TABLE IF EXISTS `t_log_authz`;
CREATE TABLE `t_log_authz`
(
    `log_id`        bigint(12)   NOT NULL AUTO_INCREMENT COMMENT '日志id编号',
    `log_opt`       varchar(50)  NOT NULL COMMENT '操作类型：login、logout',
    `log_protocol`  varchar(100) NOT NULL COMMENT '认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等',
    `log_addr`      varchar(50)       DEFAULT NULL COMMENT '认证请求来源IP地址',
    `log_location`  varchar(200)      DEFAULT NULL COMMENT '认证请求来源IP所在地点',
    `log_status`    varchar(10)       DEFAULT NULL COMMENT '认证结果：fail、success',
    `log_msg`       varchar(500)      DEFAULT NULL COMMENT '认证请求信息',
    `log_excp`      tinytext COMMENT '认证异常信息',
    `log_level`     varchar(10)       DEFAULT NULL COMMENT '日志等级',
    `device_id`     varchar(50)       DEFAULT NULL COMMENT '设备激活记录ID',
    `u_app_id`      varchar(11)       DEFAULT NULL COMMENT '用户登录的客户端应用ID',
    `u_app_channel` varchar(255)      DEFAULT NULL COMMENT '用户登录的客户端应用渠道编码',
    `u_app_version` varchar(20)       DEFAULT NULL COMMENT '用户登录的客户端版本',
    `creator`       bigint(12)        DEFAULT NULL COMMENT '认证对象id',
    `create_time`   timestamp    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '认证发生时间',
    PRIMARY KEY (`log_id`),
    KEY `idx_uid` (`creator`, `device_id`) USING BTREE COMMENT '用户索引',
    KEY `idx_biz_log` (`log_opt`, `creator`) USING BTREE COMMENT '认证日志索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='认证授权记录表';

-- ----------------------------
-- Table structure for t_log_biz
-- ----------------------------
DROP TABLE IF EXISTS `t_log_biz`;
CREATE TABLE `t_log_biz`
(
    `log_id`        bigint(12)   NOT NULL AUTO_INCREMENT COMMENT '日志id编号',
    `log_module`    varchar(50)  NOT NULL COMMENT '功能模块',
    `log_biz`       varchar(100) NOT NULL COMMENT '业务名称',
    `log_opt`       varchar(50)  NOT NULL COMMENT '操作类型',
    `log_addr`      varchar(50)       DEFAULT NULL COMMENT '功能操作请求来源IP地址',
    `log_location`  varchar(200)      DEFAULT NULL COMMENT '功能操作请求来源IP所在地点',
    `log_msg`       varchar(500) COMMENT '功能操作信息',
    `log_excp`      tinytext COMMENT '功能操作异常',
    `log_type`      tinyint(1)        DEFAULT NULL COMMENT '操作结果： 0 ：成功 ；1：失败',
    `log_level`     varchar(10)       DEFAULT NULL COMMENT '日志等级',
    `device_id`     varchar(50)       DEFAULT NULL COMMENT '设备激活记录ID',
    `u_app_id`      varchar(11)       DEFAULT NULL COMMENT '用户登录的客户端应用ID',
    `u_app_channel` varchar(255)      DEFAULT NULL COMMENT '用户登录的客户端应用渠道编码',
    `u_app_version` varchar(20)       DEFAULT NULL COMMENT '用户登录的客户端版本',
    `creator`       bigint(12)        DEFAULT NULL COMMENT '功能操作人id',
    `create_time`   timestamp    NULL DEFAULT CURRENT_TIMESTAMP COMMENT '功能操作发生时间',
    PRIMARY KEY (`log_id`),
    KEY `idx_uid` (`creator`, `device_id`) USING BTREE COMMENT '用户索引',
    KEY `idx_biz_log` (`log_module`, `log_biz`, `log_opt`, `creator`) USING BTREE COMMENT '业务日志索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='功能操作日志信息表';
