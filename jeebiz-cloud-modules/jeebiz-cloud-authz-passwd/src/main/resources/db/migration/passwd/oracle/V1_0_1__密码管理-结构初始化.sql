/* 找回密码核心表：密码找回策略表、账号核实字段表 */

-- ----------------------------
-- Table structure for t_PASSWD_STRATEGY
-- ----------------------------
CREATE TABLE t_PASSWD_STRATEGY
(
    S_ID VARCHAR2 (32) default sys_guid(),
    S_KEY VARCHAR2 (30) NOT NULL,
    S_NAME VARCHAR2 (50) NOT NULL,
    S_DESC VARCHAR2 (200) NOT NULL,
    S_STATUS VARCHAR2 (2) DEFAULT '1'
);
COMMENT ON TABLE t_PASSWD_STRATEGY IS '密码找回策略表';
COMMENT ON COLUMN t_PASSWD_STRATEGY.S_ID IS '找回策略表ID';
COMMENT ON COLUMN t_PASSWD_STRATEGY.S_NAME IS '找回策略Key，该名称必须与策略实现对象name方法提供的返回值一致';
COMMENT ON COLUMN t_PASSWD_STRATEGY.S_NAME IS '找回策略名称';
COMMENT ON COLUMN t_PASSWD_STRATEGY.S_DESC IS '找回策略描述，简述该策略的实现方式';
COMMENT ON COLUMN t_PASSWD_STRATEGY.S_STATUS IS '是否启用，1：启用(该状态下系统必要有与name对应的策略实现,才能有效)，0：停用';

-- ----------------------------
-- Table structure for t_PASSWD_VERIFI
-- ----------------------------
CREATE TABLE t_PASSWD_VERIFI
(
    V_ID VARCHAR2 (32) DEFAULT sys_guid(),
    V_NAME VARCHAR2 (50) NOT NULL,
    V_LABEL VARCHAR2 (50) NOT NULL,
    V_DESC VARCHAR2 (200) NULL,
    V_RULES VARCHAR2 (200) NULL,
    V_REQUIRED VARCHAR2 (2) DEFAULT '0',
    V_STATUS VARCHAR2 (2) DEFAULT '0'
);
COMMENT ON TABLE t_PASSWD_VERIFI IS '账号核实字段表';
COMMENT ON COLUMN t_PASSWD_VERIFI.V_ID IS '账号核实字段表ID';
COMMENT ON COLUMN t_PASSWD_VERIFI.V_NAME IS '账号核实字段名称';
COMMENT ON COLUMN t_PASSWD_VERIFI.V_LABEL IS '账号核实字段Label名称';
COMMENT ON COLUMN t_PASSWD_VERIFI.V_DESC IS '账号核实字段描述，作为提示信息';
COMMENT ON COLUMN t_PASSWD_VERIFI.V_RULES IS '账号核实字段校验规则';
COMMENT ON COLUMN t_PASSWD_VERIFI.V_REQUIRED IS '账号核实字段是否必填，1：是，0：否';
COMMENT ON COLUMN t_PASSWD_VERIFI.V_STATUS IS '账号核实字段启用状态标记，1：启用，0：停用';

-- ----------------------------
-- Table structure for t_PASSWD_CAPTCHA
-- ----------------------------
CREATE TABLE t_PASSWD_CAPTCHA
(
    V_ID VARCHAR2 (32) DEFAULT sys_guid(),
    V_UUID VARCHAR2 (50) NOT NULL,
    V_CAPTCHA VARCHAR2 (50) NOT NULL,
    TIME24 VARCHAR2 (32) DEFAULT to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')
);
COMMENT ON TABLE t_PASSWD_CAPTCHA IS '验证码信息表';
COMMENT ON COLUMN t_PASSWD_CAPTCHA.V_ID IS '验证码表ID，用于数据删除';
COMMENT ON COLUMN t_PASSWD_CAPTCHA.V_UUID IS '验证码关联外部UUID;用于业务逻辑关联查询';
COMMENT ON COLUMN t_PASSWD_CAPTCHA.V_CAPTCHA IS '验证码值';
COMMENT ON COLUMN t_PASSWD_CAPTCHA.TIME24 IS '验证码发送时间';


	
