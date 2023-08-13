-- ----------------------------
-- Table structure for Mq消息流水表
-- ----------------------------
DROP TABLE IF EXISTS `$${table-prefix}mq_flow`;
CREATE TABLE `$${table-prefix}mq_flow`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
    `mq_flow_id`  varchar(64) NOT NULL COMMENT '消息唯一标识',
    `content`     text,
    `is_delete`   tinyint(2)  NOT NULL DEFAULT '0',
    `creator`     bigint(12)           DEFAULT NULL COMMENT '创建人id',
    `modifyer`    bigint(12)           DEFAULT NULL COMMENT '修改人id',
    `create_time` datetime             DEFAULT NULL,
    `modify_time` datetime             DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_mq_flow_id` (`mq_flow_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='Mq消息流水表';