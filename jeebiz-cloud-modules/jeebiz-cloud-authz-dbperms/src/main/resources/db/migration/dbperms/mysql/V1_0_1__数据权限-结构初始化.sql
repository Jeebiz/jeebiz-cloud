/* 数据权限表：数据权限对象表、数据权限组信息表、数据权限信息表、数据权限项信息表、数据权限-数据权限组关系表、数据权限角色关系表、
	数据权限组角色关系表、数据权限用户关系表、数据权限组用户关系表*/

-- ----------------------------
-- Table structure for t_data_perms_table
-- ----------------------------
DROP TABLE IF EXISTS `t_data_perms_table`;
CREATE TABLE `t_data_perms_table`
(
    `T_ID`     int(11)      NOT NULL AUTO_INCREMENT COMMENT '数据权限对象ID',
    `T_NAME`   varchar(60)  NOT NULL COMMENT '数据权限对象名称',
    `T_TABLE`  varchar(100) NOT NULL COMMENT '受限表名称（实体表名称）',
    `T_STATUS` int(1) DEFAULT 1 COMMENT '数据权限对象可用状态:（0:不可用|1：可用）',
    `T_ORDER`  int(3) DEFAULT 1 COMMENT '数据权限对象排序',
    PRIMARY KEY (`T_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限对象表';

-- ----------------------------
-- Table structure for t_data_perms_table_columns
-- ----------------------------
DROP TABLE IF EXISTS `t_data_perms_table_columns`;
CREATE TABLE `t_data_perms_table_columns`
(
    `T_ID`     int(11)      NOT NULL COMMENT '数据权限对象ID',
    `C_ID`     int(11)      NOT NULL AUTO_INCREMENT COMMENT '数据权限对象字段ID',
    `C_NAME`   varchar(60)  NOT NULL COMMENT '受数据权限对象字段限制的业务数据表字段名称（实体表字段列名称）',
    `C_LABEL`  varchar(100) NOT NULL COMMENT '数据权限对象字段标签（页面显示名称）',
    `C_SQL`    text COMMENT '受限数据项数据查询SQL',
    `C_STATUS` int(1) DEFAULT 1 COMMENT '受限数据项字段可用状态:（0:不可用|1：可用）',
    `C_ORDER`  int(3) DEFAULT 1 COMMENT '受限数据项字段排序',
    PRIMARY KEY (`C_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限对象字段表';

-- ----------------------------
-- Table structure for t_DATA_PERMS_GROUP
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_PERMS_GROUP`;
CREATE TABLE `t_DATA_PERMS_GROUP`
(
    `G_ID`     int(11)     NOT NULL AUTO_INCREMENT COMMENT '数据权限组ID',
    `G_NAME`   varchar(60) NOT NULL COMMENT '数据权限组名称',
    `G_INTRO`  text COMMENT '数据权限组简介',
    `G_STATUS` int(1)           DEFAULT 1 COMMENT '数据权限组可用状态:（0:不可用|1：可用）',
    `G_ORDER`  int(3)           DEFAULT 1 COMMENT '数据权限组排序',
    `G_TIME24` timestamp   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据权限组创建时间',
    PRIMARY KEY (`G_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限组信息表';

-- ----------------------------
-- Table structure for t_DATA_PERMS
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_PERMS`;
CREATE TABLE `t_DATA_PERMS`
(
    `P_ID`       int(11)      NOT NULL AUTO_INCREMENT COMMENT '数据权限ID',
    `P_NAME`     varchar(60)  NOT NULL COMMENT '数据权限名称',
    `P_TYPE`     int(1) DEFAULT 1 COMMENT '数据权限类型(1:原生|2:继承|3:复制)',
    `P_INTRO`    text COMMENT '数据权限简介',
    `P_TABLE`    varchar(100) NOT NULL COMMENT '受限表名称（实体表名称）',
    `P_RELATION` varchar(20)  NOT NULL COMMENT '数据权限项关系 and/or',
    `P_STATUS`   int(1) DEFAULT 1 COMMENT '数据权限可用状态:（0:不可用|1：可用）',
    `P_ORDER`    int(4) DEFAULT 1 COMMENT '数据权限排序',
    PRIMARY KEY (`P_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限表';

-- ----------------------------
-- Table structure for t_DATA_PERMS_ITEM
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_PERMS_ITEM`;
CREATE TABLE `t_DATA_PERMS_ITEM`
(
    `P_ID`        int(11)      NOT NULL COMMENT '数据权限ID',
    `I_ID`        int(11)      NOT NULL AUTO_INCREMENT COMMENT '数据权限项ID',
    `I_COLUMN`    varchar(100) NOT NULL COMMENT '受限表字段名称（实体表字段列名称）',
    `I_NAME`      varchar(100) NOT NULL COMMENT '数据权限项名称（实体表字段中文名称）',
    `I_CONDITION` varchar(50)  NOT NULL COMMENT '受限表字段与限制条件之间的关联条件',
    `I_PINYIN`    varchar(100) NOT NULL COMMENT '受限表字段中文拼音',
    `I_PERMS`     text COMMENT '受限表字段限制条件',
    `I_STATUS`    int(1) DEFAULT 1 COMMENT '数据权限项可用状态:（0:不可用|1：可用）',
    `I_ORDER`     int(4) DEFAULT 1 COMMENT '数据权限项排序',
    PRIMARY KEY (`I_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限项信息表';

-- ----------------------------
-- Table structure for t_DATA_PERMS_GROUP_REL
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_PERMS_GROUP_REL`;
CREATE TABLE `t_DATA_PERMS_GROUP_REL`
(
    `G_ID` int(11) NOT NULL COMMENT '数据权限组ID',
    `P_ID` int(11) NOT NULL COMMENT '数据权限ID',
    UNIQUE KEY (`G_ID`, `P_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限-数据权限组关系表';

-- ----------------------------
-- Table structure for t_DATA_PERMS_ROLE_REL
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_PERMS_ROLE_REL`;
CREATE TABLE `t_DATA_PERMS_ROLE_REL`
(
    `APP_ID` int(11) NOT NULL COMMENT '应用ID',
    `P_ID`   int(11) NOT NULL COMMENT '数据权限ID',
    `R_ID`   int(11) NOT NULL COMMENT '角色ID',
    UNIQUE KEY (`P_ID`, `R_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限角色关系表';

-- ----------------------------
-- Table structure for t_DATA_PERMS_USER_REL
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_PERMS_USER_REL`;
CREATE TABLE `t_DATA_PERMS_USER_REL`
(
    `APP_ID` int(11) NOT NULL COMMENT '应用ID',
    `P_ID`   int(11) NOT NULL COMMENT '数据权限ID',
    `U_ID`   int(11) NOT NULL COMMENT '用户ID',
    UNIQUE KEY (`P_ID`, `U_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限用户关系表';

-- ----------------------------
-- Table structure for t_DATA_PERMS_GROUP_ROLE_REL
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_PERMS_GROUP_ROLE_REL`;
CREATE TABLE `t_DATA_PERMS_GROUP_ROLE_REL`
(
    `APP_ID` int(11) NOT NULL COMMENT '应用ID',
    `G_ID`   int(11) NOT NULL COMMENT '数据权限组ID',
    `R_ID`   int(11) NOT NULL COMMENT '角色ID',
    UNIQUE KEY (`G_ID`, `R_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限组角色关系表';

-- ----------------------------
-- Table structure for t_DATA_PERMS_GROUP_USER_REL
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_PERMS_GROUP_USER_REL`;
CREATE TABLE `t_DATA_PERMS_GROUP_USER_REL`
(
    `APP_ID` int(11) NOT NULL COMMENT '应用ID',
    `G_ID`   int(11) NOT NULL COMMENT '数据权限组ID',
    `U_ID`   int(11) NOT NULL COMMENT '用户ID',
    UNIQUE KEY (`G_ID`, `U_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限组用户关系表';


-- ----------------------------
-- Table structure for t_DATA_PERMS_SPCL
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_PERMS_SPCL`;
CREATE TABLE `t_DATA_PERMS_SPCL`
(
    `T_ID`     int(11)      NOT NULL AUTO_INCREMENT COMMENT '数据权限专项ID',
    `T_CODE`   varchar(60)  NOT NULL COMMENT '数据权限专项编码（对应程序中的）',
    `T_NAME`   varchar(100) NOT NULL COMMENT '数据权限专项名称',
    `T_STATUS` int(1) DEFAULT 1 COMMENT '数据权限专项可用状态:（0:不可用|1：可用）',
    `T_ORDER`  int(3) DEFAULT 1 COMMENT '数据权限专项排序',
    PRIMARY KEY (`T_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限专项表';

-- ----------------------------
-- Table structure for t_DATA_PERMS_SPCL_COLUMNS
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_PERMS_SPCL_COLUMNS`;
CREATE TABLE `t_DATA_PERMS_SPCL_COLUMNS`
(
    `T_ID`     int(11)      NOT NULL COMMENT '数据权限专项ID',
    `C_ID`     int(11)      NOT NULL AUTO_INCREMENT COMMENT '数据权限专项字段ID',
    `C_TABLE`  varchar(100) NOT NULL COMMENT '受限表名称（实体表名称）',
    `C_NAME`   varchar(60)  NOT NULL COMMENT '受数据权限专项字段限制的业务数据表字段名称（实体表字段列名称）',
    `C_LABEL`  varchar(100) NOT NULL COMMENT '数据权限专项字段标签（页面显示名称）',
    `C_SQL`    text COMMENT '受限数据项数据查询SQL',
    `C_STATUS` int(1) DEFAULT 1 COMMENT '受限数据项字段可用状态:（0:不可用|1：可用）',
    `C_ORDER`  int(3) DEFAULT 1 COMMENT '受限数据项字段排序',
    PRIMARY KEY (`C_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限专项字段表';


-- ----------------------------
-- Table structure for t_DATA_PERMS_SPCL_ROLE_REL
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_PERMS_SPCL_ROLE_REL`;
CREATE TABLE `t_DATA_PERMS_SPCL_ROLE_REL`
(
    `APP_ID` int(11) NOT NULL COMMENT '应用ID',
    `T_ID`   int(11) NOT NULL COMMENT '数据权限专项ID',
    `R_ID`   int(11) NOT NULL COMMENT '角色ID',
    UNIQUE KEY (`T_ID`, `R_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限专项角色关系表';

-- ----------------------------
-- Table structure for t_DATA_PERMS_SPCL_USER_REL
-- ----------------------------
DROP TABLE IF EXISTS `t_DATA_PERMS_SPCL_USER_REL`;
CREATE TABLE `t_DATA_PERMS_SPCL_USER_REL`
(
    `APP_ID` int(11) NOT NULL COMMENT '应用ID',
    `T_ID`   int(11) NOT NULL COMMENT '数据权限专项ID',
    `U_ID`   int(11) NOT NULL COMMENT '用户ID',
    UNIQUE KEY (`T_ID`, `U_ID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数据权限专项用户关系表';
