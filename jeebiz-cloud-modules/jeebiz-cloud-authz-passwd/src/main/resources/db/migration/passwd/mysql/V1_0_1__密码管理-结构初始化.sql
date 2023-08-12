/* 找回密码核心表：密码找回策略表、账号核实字段表 */

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_PASSWD_STRATEGY
-- ----------------------------
CREATE TABLE `t_PASSWD_STRATEGY`
(
    `S_ID`     bigint(11)  NOT NULL AUTO_INCREMENT COMMENT '找回策略表ID',
    `S_KEY`    varchar(30) NOT NULL COMMENT '找回策略Key，该名称必须与策略实现对象name方法提供的返回值一致',
    `S_NAME`   varchar(50) NOT NULL COMMENT '找回策略名称',
    `S_DESC`   varchar(200)         DEFAULT NULL COMMENT '找回策略描述，简述该策略的实现方式',
    `S_STATUS` int(1)      NOT NULL DEFAULT '1' COMMENT '是否启用，1：启用(该状态下系统必要有与name对应的策略实现,才能有效)，0：停用',
    PRIMARY KEY (`S_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='密码找回策略表';

-- ----------------------------
-- Table structure for t_PASSWD_VERIFI
-- ----------------------------
CREATE TABLE `t_PASSWD_VERIFI`
(
    `V_ID`       bigint(11)   NOT NULL COMMENT '账号核实字段表ID',
    `V_NAME`     varchar(50)  NOT NULL COMMENT '账号核实字段名称',
    `V_LABEL`    varchar(50)  NOT NULL COMMENT '账号核实字段Label名称',
    `V_DESC`     varchar(200) NOT NULL COMMENT '账号核实字段描述，作为提示信息',
    `V_RULES`    varchar(200) NOT NULL COMMENT '账号核实字段校验规则',
    `V_REQUIRED` int(1)       NOT NULL COMMENT '账号核实字段是否必填，1：是，0：否',
    `V_STATUS`   int(1)       NOT NULL COMMENT '账号核实字段启用状态标记，1：启用，0：停用',
    PRIMARY KEY (`V_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='账号核实字段表';

-- ----------------------------
-- Table structure for t_PASSWD_CAPTCHA
-- ----------------------------
CREATE TABLE `t_PASSWD_CAPTCHA`
(
    `V_ID`      bigint(11)  NOT NULL COMMENT '验证码表ID，用于数据删除',
    `V_UUID`    varchar(50) NOT NULL COMMENT '验证码关联外部UUID;用于业务逻辑关联查询',
    `V_CAPTCHA` varchar(50) NOT NULL COMMENT '验证码值',
    `TIME24`    timestamp   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '验证码发送时间',
    PRIMARY KEY (`V_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='验证码信息表';
	
	