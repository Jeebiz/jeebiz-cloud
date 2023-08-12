-- ----------------------------
-- Table structure for t_user_face_repository
-- ----------------------------
DROP TABLE IF EXISTS `t_user_face_repository`;
CREATE TABLE `t_user_face_repository`
(
    `repo_id`     bigint(12) NOT NULL AUTO_INCREMENT COMMENT '分组ID',
    `repo_name`   varchar(255)        DEFAULT NULL COMMENT '分组名称',
    `repo_status` tinyint(1)          DEFAULT '0' COMMENT '分组状态（0:禁用|1:可用）',
    `is_delete`   tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
    `creator`     bigint(12)          DEFAULT NULL COMMENT '创建人ID',
    `create_time` timestamp  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifyer`    bigint(12)          DEFAULT NULL COMMENT '修改人ID',
    `modify_time` timestamp  NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`repo_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户-人脸识别数据分组表';

-- ----------------------------
-- Table structure for t_user_face
-- ----------------------------
DROP TABLE IF EXISTS `t_user_face`;
CREATE TABLE `t_user_face`
(
    `user_id`     bigint(12) NOT NULL COMMENT '用户ID',
    `repo_id`     bigint(12)          DEFAULT NULL COMMENT '人脸识别数据分组ID',
    `face_id`     bigint(12) NOT NULL COMMENT '人脸识别数据ID',
    `face_base64` text COMMENT '人脸识别图片base64编码后的图片数据（图片的base64编码不包含图片头的，如data:image/jpg;base64,）',
    `face_type`   varchar(50)         DEFAULT NULL COMMENT '人脸的类型：(LIVE:表示生活照;通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等|IDCARD:表示身份证芯片照;二代身份证内置芯片中的人像照片|WATERMARK:表示带水印证件照;一般为带水印的小图，如公安网小图|CERT:表示证件照片;如拍摄的身份证、工卡、护照、学生证等证件图片); 默认LIVE',
    `face_token`  varchar(255)        DEFAULT NULL COMMENT '人脸图片的唯一标识',
    `is_delete`   tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
    `creator`     bigint(12)          DEFAULT NULL COMMENT '创建人ID',
    `create_time` timestamp  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifyer`    bigint(12)          DEFAULT NULL COMMENT '修改人ID',
    `modify_time` timestamp  NULL     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`face_id`),
    KEY `idx_facex` (`user_id`, `repo_id`, `face_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户-人脸识别数据表';

