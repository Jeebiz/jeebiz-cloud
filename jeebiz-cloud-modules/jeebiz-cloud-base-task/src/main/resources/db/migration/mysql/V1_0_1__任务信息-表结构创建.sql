-- ----------------------------
-- Table structure for t_task_info
-- ----------------------------
CREATE TABLE `t_task_info`
(
    `id`          bigint(20)  NOT NULL COMMENT '任务主键',
    `user_id`     varchar(36)          DEFAULT NULL COMMENT '任务发起人',
    `task_name`   varchar(50)          DEFAULT NULL COMMENT '任务名称',
    `task_desc`   varchar(255)         DEFAULT NULL COMMENT '任务描述',
    `status`      tinyint(4)  NOT NULL DEFAULT '1' COMMENT '任务状态(0:未开始, 1:执行中, 2:成功, 3:失败, 4:取消, 4:已过期)',
    `biz_type`    tinyint(4)           DEFAULT NULL COMMENT '任务对应的业务类型',
    `biz_data`    text COMMENT '任务对应的业务数据',
    `file_path`   varchar(100)         DEFAULT NULL COMMENT '文件存储路径',
    `file_url`    varchar(255)         DEFAULT NULL COMMENT '文件访问url',
    `school_code` varchar(20) NOT NULL COMMENT '学校代码',
    `is_delete`   tinyint(2)  NOT NULL DEFAULT 0 COMMENT '是否删除（0:未删除,1:已删除）',
    `creator`     bigint(12)           DEFAULT 0 COMMENT '创建人ID',
    `create_time` timestamp   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifyer`    bigint(12)           DEFAULT NULL COMMENT '修改人ID',
    `modify_time` timestamp   NULL     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_full` (`school_code`, `user_id`) USING BTREE COMMENT '任务信息索引'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='任务信息表';