-- ----------------------------
-- Table structure for t_data_pairgroup
-- ----------------------------
DROP TABLE IF EXISTS `t_data_pairgroup`;
CREATE TABLE `t_data_pairgroup`
(
    `g_id`        bigint(12)   NOT NULL AUTO_INCREMENT COMMENT '数据字典id',
    `g_key`       varchar(50)  NOT NULL COMMENT '数据字典键',
    `g_text`      varchar(500) NOT NULL COMMENT '数据字典值',
    `g_status`    tinyint(2)            DEFAULT 1 COMMENT '数据字典状态:（0:禁用|1：可用）',
    `g_order`     tinyint(2)            DEFAULT 1 COMMENT '数据字典排序',
    `is_delete`   tinyint(2)   NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
    `creator`     bigint(12)            DEFAULT '0' COMMENT '创建人ID',
    `create_time` timestamp             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifyer`    bigint(12)            DEFAULT NULL COMMENT '修改人ID',
    `modify_time` timestamp             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`g_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='基础数据字典信息表';

-- ----------------------------
-- Table structure for t_data_pairvalue
-- ----------------------------
DROP TABLE IF EXISTS `t_data_pairvalue`;
CREATE TABLE `t_data_pairvalue`
(
    `d_id`        bigint(12)   NOT NULL AUTO_INCREMENT COMMENT '数据id',
    `d_group`     varchar(50)  NOT NULL COMMENT '数据字典',
    `d_label`     varchar(200) NOT NULL COMMENT '数据标签',
    `d_key`       varchar(50)  NOT NULL COMMENT '数据键',
    `d_value`     varchar(500) NOT NULL COMMENT '数据值',
    `d_text`      varchar(2000) COMMENT '数据描述',
    `d_status`    int(1)                DEFAULT 1 COMMENT '数据状态:（0:不可用|1：可用）',
    `d_order`     int(10)               DEFAULT 1 COMMENT '数据排序:组内排序',
    `is_delete`   tinyint(2)   NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
    `creator`     bigint(12)            DEFAULT '0' COMMENT '创建人ID',
    `create_time` timestamp             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifyer`    bigint(12)            DEFAULT NULL COMMENT '修改人ID',
    `modify_time` timestamp             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`d_id`),
    UNIQUE KEY (`d_group`, `d_key`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='基础数据信息表';
