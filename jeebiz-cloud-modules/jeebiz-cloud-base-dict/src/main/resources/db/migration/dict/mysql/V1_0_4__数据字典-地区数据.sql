-- ----------------------------
-- Table structure for t_data_region
-- ----------------------------
DROP TABLE IF EXISTS `t_data_region`;
CREATE TABLE `t_data_region`
(
    `id`          bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code2`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '两位字母',
    `code3`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '三位字母',
    `number`      varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '数字',
    `iso_code`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT 'ISO 3166-2相应代码',
    `iso_name`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '国家或地区（ISO 英文用名）',
    `cn_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '中国大陆 惯用名',
    `tw_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '中国台湾 惯用名',
    `hk_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '中国香港 惯用名',
    `remark`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `is_delete`   tinyint(2)                                                    NOT NULL DEFAULT '0' COMMENT '是否删除（0:未删除,1:已删除）',
    `creator`     bigint(12)                                                             DEFAULT '0' COMMENT '创建人ID',
    `create_time` timestamp                                                              DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifyer`    bigint(12)                                                             DEFAULT NULL COMMENT '修改人ID',
    `modify_time` timestamp                                                              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`, `code2`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='国家地区代码 http://doc.chacuo.net/iso-3166-1';

-- ----------------------------
-- Records of t_data_region
-- ----------------------------
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (1, 'AD', 'AND', '020', 'ISO 3166-2:AD', 'Andorra', '安道尔 ', '安道尔 ', '安道尔', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (2, 'AE', 'ARE', '784', 'ISO 3166-2:AE', 'United Arab Emirates', '阿联酋', '阿联', '阿联酋', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (3, 'AF', 'AFG', '004', 'ISO 3166-2:AF', 'Afghanistan', '阿富汗', '阿富汗', '阿富汗', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (4, 'AG', 'ATG', '028', 'ISO 3166-2:AG', 'Antigua & Barbuda', '安提瓜和巴布达', '安提瓜和巴布达',
        '安提瓜和巴布达', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (5, 'AI', 'AIA', '660', 'ISO 3166-2:AI', 'Anguilla', '安圭拉', '英属安圭拉', '安圭拉岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (6, 'AL', 'ALB', '008', 'ISO 3166-2:AL', 'Albania', '阿尔巴尼亚', '阿尔巴尼亚', '阿尔巴尼亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (7, 'AM', 'ARM', '051', 'ISO 3166-2:AM', 'Armenia', '亚美尼亚', '亚美尼亚', '亚美尼亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (8, 'AO', 'AGO', '024', 'ISO 3166-2:AO', 'Angola', '安哥拉', '安哥拉', '安哥拉', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (9, 'AQ', 'ATA', '010', 'ISO 3166-2:AQ', 'Antarctica', '南极洲', '南极洲', '南极洲', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (10, 'AR', 'ARG', '032', 'ISO 3166-2:AR', 'Argentina', '阿根廷', '阿根廷', '阿根廷', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (11, 'AS', 'ASM', '016', 'ISO 3166-2:AS', 'American Samoa', '美属萨摩亚', '美属萨摩亚', '美属萨摩亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (12, 'AT', 'AUT', '040', 'ISO 3166-2:AT', 'Austria', '奥地利', '奥地利', '奥地利', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (13, 'AU', 'AUS', '036', 'ISO 3166-2:AU', 'Australia', '澳大利亚', '澳洲', '澳洲', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (14, 'AW', 'ABW', '533', 'ISO 3166-2:AW', 'Aruba', '阿鲁巴', '阿鲁巴', '阿鲁巴', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (15, 'AX', 'ALA', '248', 'ISO 3166-2:AX', '?aland Island', '奥兰群岛', '奥兰群岛', '亚兰群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (16, 'AZ', 'AZE', '031', 'ISO 3166-2:AZ', 'Azerbaijan', '阿塞拜疆', '阿塞拜疆', '阿塞拜疆', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (17, 'BA', 'BIH', '070', 'ISO 3166-2:BA', 'Bosnia & Herzegovina', '波黑', '波黑', '波黑', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (18, 'BB', 'BRB', '052', 'ISO 3166-2:BB', 'Barbados', '巴巴多斯', '巴巴多斯', '巴巴多斯', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (19, 'BD', 'BGD', '050', 'ISO 3166-2:BD', 'Bangladesh', '孟加拉', '孟加拉', '孟加拉', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (20, 'BE', 'BEL', '056', 'ISO 3166-2:BE', 'Belgium', '比利时', '比利时', '比利时', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (21, 'BF', 'BFA', '854', 'ISO 3166-2:BF', 'Burkina', '布基纳法索', '布基纳法索', '布基纳法索', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (22, 'BG', 'BGR', '100', 'ISO 3166-2:BG', 'Bulgaria', '保加利亚', '保加利亚', '保加利亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (23, 'BH', 'BHR', '048', 'ISO 3166-2:BH', 'Bahrain', '巴林', '巴林', '巴林', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (24, 'BI', 'BDI', '108', 'ISO 3166-2:BI', 'Burundi', '布隆迪', '布隆迪', '布隆迪', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (25, 'BJ', 'BEN', '204', 'ISO 3166-2:BJ', 'Benin', '贝宁', '贝宁', '贝宁', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (26, 'BL', 'BLM', '652', 'ISO 3166-2:BL', 'Saint Barthélemy', '圣巴泰勒米岛', '圣巴瑟米', '圣巴托洛缪岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (27, 'BM', 'BMU', '060', 'ISO 3166-2:BM', 'Bermuda', '百慕大', '百慕大', '百慕大', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (28, 'BN', 'BRN', '096', 'ISO 3166-2:BN', 'Brunei', '文莱', '文莱', '文莱', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (29, 'BO', 'BOL', '068', 'ISO 3166-2:BO', 'Bolivia', '玻利维亚', '玻利维亚', '玻利维亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (30, 'BQ', 'BES', '535', 'ISO 3166-2:BQ', 'Caribbean Netherlands', '荷兰加勒比区', '荷兰加勒比区',
        '荷兰加勒比区', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (31, 'BR', 'BRA', '076', 'ISO 3166-2:BR', 'Brazil', '巴西', '巴西', '巴西', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (32, 'BS', 'BHS', '044', 'ISO 3166-2:BS', 'The Bahamas', '巴哈马', '巴哈马', '巴哈马', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (33, 'BT', 'BTN', '064', 'ISO 3166-2:BT', 'Bhutan', '不丹', '不丹', '不丹', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (34, 'BV', 'BVT', '074', 'ISO 3166-2:BV', 'Bouvet Island', '布韦岛', '布威岛', '鲍威特岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (35, 'BW', 'BWA', '072', 'ISO 3166-2:BW', 'Botswana', '博茨瓦纳', '博茨瓦纳', '博茨瓦纳', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (36, 'BY', 'BLR', '112', 'ISO 3166-2:BY', 'Belarus', '白俄罗斯', '白俄罗斯', '白俄罗斯', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (37, 'BZ', 'BLZ', '084', 'ISO 3166-2:BZ', 'Belize', '伯利兹', '伯利兹', '伯利兹', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (38, 'CA', 'CAN', '124', 'ISO 3166-2:CA', 'Canada', '加拿大', '加拿大', '加拿大', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (39, 'CC', 'CCK', '166', 'ISO 3166-2:CC', 'Cocos (Keeling) Islands', '科科斯群岛', '可可斯群岛', '科科斯群岛',
        NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (40, 'CF', 'CAF', '140', 'ISO 3166-2:CF', 'Central African Republic', '中非', '中非', '中非', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (41, 'CH', 'CHE', '756', 'ISO 3166-2:CH', 'Switzerland', '瑞士', '瑞士', '瑞士', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (42, 'CL', 'CHL', '152', 'ISO 3166-2:CL', 'Chile', '智利', '智利', '智利', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (43, 'CM', 'CMR', '120', 'ISO 3166-2:CM', 'Cameroon', '喀麦隆', '喀麦隆', '喀麦隆', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (44, 'CO', 'COL', '170', 'ISO 3166-2:CO', 'Colombia', '哥伦比亚', '哥伦比亚', '哥伦比亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (45, 'CR', 'CRI', '188', 'ISO 3166-2:CR', 'Costa Rica', '哥斯达黎加', '哥斯达黎加', '哥斯达黎加', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (46, 'CU', 'CUB', '192', 'ISO 3166-2:CU', 'Cuba', '古巴', '古巴', '古巴', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (47, 'CV', 'CPV', '132', 'ISO 3166-2:CV', 'Cape Verde', '佛得角', '佛得角', '佛得角', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (48, 'CX', 'CXR', '162', 'ISO 3166-2:CX', 'Christmas Island', '圣诞岛', '圣诞岛', '圣诞岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (49, 'CY', 'CYP', '196', 'ISO 3166-2:CY', 'Cyprus', '塞浦路斯', '塞浦路斯', '塞浦路斯', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (50, 'CZ', 'CZE', '203', 'ISO 3166-2:CZ', 'Czech Republic', '捷克', '捷克', '捷克', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (51, 'DE', 'DEU', '276', 'ISO 3166-2:DE', 'Germany', '德国', '德国', '德国', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (52, 'DJ', 'DJI', '262', 'ISO 3166-2:DJ', 'Djibouti', '吉布提', '吉布提', '吉布提', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (53, 'DK', 'DNK', '208', 'ISO 3166-2:DK', 'Denmark', '丹麦', '丹麦', '丹麦', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (54, 'DM', 'DMA', '212', 'ISO 3166-2:DM', 'Dominica', '多米尼克', '多米尼克', '多米尼克', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (55, 'DO', 'DOM', '214', 'ISO 3166-2:DO', 'Dominican Republic', '多米尼加', '多米尼加', '多米尼加', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (56, 'DZ', 'DZA', '012', 'ISO 3166-2:DZ', 'Algeria', '阿尔及利亚', '阿尔及利亚', '阿尔及利亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (57, 'EC', 'ECU', '218', 'ISO 3166-2:EC', 'Ecuador', '厄瓜多尔', '厄瓜多尔', '厄瓜多尔', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (58, 'EE', 'EST', '233', 'ISO 3166-2:EE', 'Estonia', '爱沙尼亚', '爱沙尼亚', '爱沙尼亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (59, 'EG', 'EGY', '818', 'ISO 3166-2:EG', 'Egypt', '埃及', '埃及', '埃及', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (60, 'EH', 'ESH', '732', 'ISO 3166-2:EH', 'Western Sahara', '西撒哈拉', '西撒哈拉', '西撒哈拉', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (61, 'ER', 'ERI', '232', 'ISO 3166-2:ER', 'Eritrea', '厄立特里亚', '厄立垂亚', '厄立特里亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (62, 'ES', 'ESP', '724', 'ISO 3166-2:ES', 'Spain', '西班牙', '西班牙', '西班牙', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (63, 'FI', 'FIN', '246', 'ISO 3166-2:FI', 'Finland', '芬兰', '芬兰', '芬兰', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (64, 'FJ', 'FJI', '242', 'ISO 3166-2:FJ', 'Fiji', '斐济群岛', '斐济', '斐济', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (65, 'FK', 'FLK', '238', 'ISO 3166-2:FK', 'Falkland Islands', '马尔维纳斯群岛（ 福克兰）', '福克兰群岛',
        '福克兰群岛（ 马尔维纳斯）', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (66, 'FM', 'FSM', '583', 'ISO 3166-2:FM', 'Federated States of Micronesia', '密克罗尼西亚联邦',
        '密克罗尼西亚联邦', '密克罗尼西亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (67, 'FO', 'FRO', '234', 'ISO 3166-2:FO', 'Faroe Islands', '法罗群岛', '法罗群岛', '法罗群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (68, 'FR', 'FRA', '250', 'ISO 3166-2:FR', 'France', '法国', '法国', '法国', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (69, 'GA', 'GAB', '266', 'ISO 3166-2:GA', 'Gabon', '加蓬', '加蓬', '加蓬', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (70, 'GD', 'GRD', '308', 'ISO 3166-2:GD', 'Grenada', '格林纳达', '格林纳达', '格林纳达', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (71, 'GE', 'GEO', '268', 'ISO 3166-2:GE', 'Georgia', '格鲁吉亚', '乔治亚', '格鲁吉亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (72, 'GF', 'GUF', '254', 'ISO 3166-2:GF', 'French Guiana', '法属圭亚那', '法属圭亚那', '法属圭亚那', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (73, 'GH', 'GHA', '288', 'ISO 3166-2:GH', 'Ghana', '加纳', '加纳', '加纳', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (74, 'GI', 'GIB', '292', 'ISO 3166-2:GI', 'Gibraltar', '直布罗陀', '直布罗陀', '直布罗陀', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (75, 'GL', 'GRL', '304', 'ISO 3166-2:GL', 'Greenland', '格陵兰', '格陵兰', '格陵兰', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (76, 'GN', 'GIN', '324', 'ISO 3166-2:GN', 'Guinea', '几内亚', '几内亚', '几内亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (77, 'GP', 'GLP', '312', 'ISO 3166-2:GP', 'Guadeloupe', '瓜德罗普', '瓜德鲁普岛', '瓜德鲁普岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (78, 'GQ', 'GNQ', '226', 'ISO 3166-2:GQ', 'Equatorial Guinea', '赤道几内亚', '赤道几内亚', '赤道几内亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (79, 'GR', 'GRC', '300', 'ISO 3166-2:GR', 'Greece', '希腊', '希腊', '希腊', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (80, 'GS', 'SGS', '239', 'ISO 3166-2:GS', 'South Georgia and the South Sandwich Islands',
        '南乔治亚岛和南桑威奇群岛', '南乔治亚与南三明治群岛', '南乔治亚岛与南桑威奇群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (81, 'GT', 'GTM', '320', 'ISO 3166-2:GT', 'Guatemala', '危地马拉', '危地马拉', '危地马拉', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (82, 'GU', 'GUM', '316', 'ISO 3166-2:GU', 'Guam', '关岛', '关岛', '关岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (83, 'GW', 'GNB', '624', 'ISO 3166-2:GW', 'Guinea-Bissau', '几内亚比绍', '几内亚比绍', '几内亚比绍', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (84, 'GY', 'GUY', '328', 'ISO 3166-2:GY', 'Guyana', '圭亚那', '圭亚那', '圭亚那', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (85, 'HK', 'HKG', '344', 'ISO 3166-2:HK', 'Hong Kong', '中国香港', '中国香港', '中国香港', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (86, 'HM', 'HMD', '334', 'ISO 3166-2:HM', 'Heard Island and McDonald Islands', '赫德岛和麦克唐纳群岛',
        '赫德及麦当劳群岛', '赫德岛和麦克唐纳群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (87, 'HN', 'HND', '340', 'ISO 3166-2:HN', 'Honduras', '洪都拉斯', '洪都拉斯', '洪都拉斯', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (88, 'HR', 'HRV', '191', 'ISO 3166-2:HR', 'Croatia', '克罗地亚', '克罗地亚', '克罗地亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (89, 'HT', 'HTI', '332', 'ISO 3166-2:HT', 'Haiti', '海地', '海地', '海地', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (90, 'HU', 'HUN', '348', 'ISO 3166-2:HU', 'Hungary', '匈牙利', '匈牙利', '匈牙利', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (91, 'ID', 'IDN', '360', 'ISO 3166-2:ID', 'Indonesia', '印尼', '印尼', '印尼', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (92, 'IE', 'IRL', '372', 'ISO 3166-2:IE', 'Ireland', '爱尔兰', '爱尔兰', '爱尔兰', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (93, 'IL', 'ISR', '376', 'ISO 3166-2:IL', 'Israel', '以色列', '以色列', '以色列', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (94, 'IM', 'IMN', '833', 'ISO 3166-2:IM', 'Isle of Man', '马恩岛', '马恩岛', '马恩岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (95, 'IN', 'IND', '356', 'ISO 3166-2:IN', 'India', '印度', '印度', '印度', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (96, 'IO', 'IOT', '086', 'ISO 3166-2:IO', 'British Indian Ocean Territory', '英属印度洋领地', '英属印度洋地区',
        '英属印度洋地区', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (97, 'IQ', 'IRQ', '368', 'ISO 3166-2:IQ', 'Iraq', '伊拉克', '伊拉克', '伊拉克', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (98, 'IR', 'IRN', '364', 'ISO 3166-2:IR', 'Iran', '伊朗', '伊朗', '伊朗', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (99, 'IS', 'ISL', '352', 'ISO 3166-2:IS', 'Iceland', '冰岛', '冰岛', '冰岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (100, 'IT', 'ITA', '380', 'ISO 3166-2:IT', 'Italy', '意大利', '意大利', '意大利', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (101, 'JE', 'JEY', '832', 'ISO 3166-2:JE', 'Jersey', '泽西岛', '泽西岛', '泽西', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (102, 'JM', 'JAM', '388', 'ISO 3166-2:JM', 'Jamaica', '牙买加', '牙买加', '牙买加', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (103, 'JO', 'JOR', '400', 'ISO 3166-2:JO', 'Jordan', '约旦', '约旦', '约旦', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (104, 'JP', 'JPN', '392', 'ISO 3166-2:JP', 'Japan', '日本', '日本', '日本', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (105, 'KH', 'KHM', '116', 'ISO 3166-2:KH', 'Cambodia', '柬埔寨', '柬埔寨', '柬埔寨', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (106, 'KI', 'KIR', '296', 'ISO 3166-2:KI', 'Kiribati', '基里巴斯', '基里巴斯', '基里巴斯', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (107, 'KM', 'COM', '174', 'ISO 3166-2:KM', 'The Comoros', '科摩罗', '科摩罗', '科摩罗', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (108, 'KW', 'KWT', '414', 'ISO 3166-2:KW', 'Kuwait', '科威特', '科威特', '科威特', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (109, 'KY', 'CYM', '136', 'ISO 3166-2:KY', 'Cayman Islands', '开曼群岛', '开曼群岛', '开曼群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (110, 'LB', 'LBN', '422', 'ISO 3166-2:LB', 'Lebanon', '黎巴嫩', '黎巴嫩', '黎巴嫩', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (111, 'LI', 'LIE', '438', 'ISO 3166-2:LI', 'Liechtenstein', '列支敦士登', '列支敦士登', '列支敦士登', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (112, 'LK', 'LKA', '144', 'ISO 3166-2:LK', 'Sri Lanka', '斯里兰卡', '斯里兰卡', '斯里兰卡', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (113, 'LR', 'LBR', '430', 'ISO 3166-2:LR', 'Liberia', '利比里亚', '利比里亚', '利比里亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (114, 'LS', 'LSO', '426', 'ISO 3166-2:LS', 'Lesotho', '莱索托', '莱索托', '莱索托', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (115, 'LT', 'LTU', '440', 'ISO 3166-2:LT', 'Lithuania', '立陶宛', '立陶宛', '立陶宛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (116, 'LU', 'LUX', '442', 'ISO 3166-2:LU', 'Luxembourg', '卢森堡', '卢森堡', '卢森堡', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (117, 'LV', 'LVA', '428', 'ISO 3166-2:LV', 'Latvia', '拉脱维亚', '拉脱维亚', '拉脱维亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (118, 'LY', 'LBY', '434', 'ISO 3166-2:LY', 'Libya', '利比亚', '利比亚', '利比亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (119, 'MA', 'MAR', '504', 'ISO 3166-2:MA', 'Morocco', '摩洛哥', '摩洛哥', '摩洛哥', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (120, 'MC', 'MCO', '492', 'ISO 3166-2:MC', 'Monaco', '摩纳哥', '摩纳哥', '摩纳哥', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (121, 'MD', 'MDA', '498', 'ISO 3166-2:MD', 'Moldova', '摩尔多瓦', '摩尔多瓦', '摩尔多瓦', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (122, 'ME', 'MNE', '499', 'ISO 3166-2:ME', 'Montenegro', '黑山', '黑山', '黑山', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (123, 'MF', 'MAF', '663', 'ISO 3166-2:MF', 'Saint Martin (France)', '法属圣马丁', '法属圣马丁　', '法属圣马丁',
        NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (124, 'MG', 'MDG', '450', 'ISO 3166-2:MG', 'Madagascar', '马达加斯加', '马达加斯加', '马达加斯加', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (125, 'MH', 'MHL', '584', 'ISO 3166-2:MH', 'Marshall islands', '马绍尔群岛', '马绍尔群岛', '马绍尔群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (126, 'MK', 'MKD', '807', 'ISO 3166-2:MK', 'Republic of Macedonia (FYROM)', '马其顿', '马其顿', '马其顿', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (127, 'ML', 'MLI', '466', 'ISO 3166-2:ML', 'Mali', '马里', '马利', '马里', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (128, 'MM', 'MMR', '104', 'ISO 3166-2:MM', 'Myanmar (Burma)', '缅甸', '缅甸', '缅甸', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (129, 'MO', 'MAC', '446', 'ISO 3166-2:MO', 'Macao', '澳门', '澳门', '澳门', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (130, 'MQ', 'MTQ', '474', 'ISO 3166-2:MQ', 'Martinique', '马提尼克', '法属马丁尼克', '马提尼克', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (131, 'MR', 'MRT', '478', 'ISO 3166-2:MR', 'Mauritania', '毛里塔尼亚', '毛里塔尼亚', '毛里塔尼亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (132, 'MS', 'MSR', '500', 'ISO 3166-2:MS', 'Montserrat', '蒙塞拉特岛', '蒙塞拉特岛', '蒙塞拉特岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (133, 'MT', 'MLT', '470', 'ISO 3166-2:MT', 'Malta', '马耳他', '马耳他', '马耳他', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (134, 'MV', 'MDV', '462', 'ISO 3166-2:MV', 'Maldives', '马尔代夫', '马尔代夫', '马尔代夫', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (135, 'MW', 'MWI', '454', 'ISO 3166-2:MW', 'Malawi', '马拉维', '马拉维', '马拉维', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (136, 'MX', 'MEX', '484', 'ISO 3166-2:MX', 'Mexico', '墨西哥', '墨西哥', '墨西哥', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (137, 'MY', 'MYS', '458', 'ISO 3166-2:MY', 'Malaysia', '马来西亚', '马来西亚', '马来西亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (138, 'NA', 'NAM', '516', 'ISO 3166-2:NA', 'Namibia', '纳米比亚', '纳米比亚', '纳米比亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (139, 'NE', 'NER', '562', 'ISO 3166-2:NE', 'Niger', '尼日尔', '尼日', '尼日尔', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (140, 'NF', 'NFK', '574', 'ISO 3166-2:NF', 'Norfolk Island', '诺福克岛', '诺福克岛', '诺福克岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (141, 'NG', 'NGA', '566', 'ISO 3166-2:NG', 'Nigeria', '尼日利亚', '尼日利亚', '尼日利亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (142, 'NI', 'NIC', '558', 'ISO 3166-2:NI', 'Nicaragua', '尼加拉瓜', '尼加拉瓜', '尼加拉瓜', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (143, 'NL', 'NLD', '528', 'ISO 3166-2:NL', 'Netherlands', '荷兰', '荷兰', '荷兰', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (144, 'NO', 'NOR', '578', 'ISO 3166-2:NO', 'Norway', '挪威', '挪威', '挪威', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (145, 'NP', 'NPL', '524', 'ISO 3166-2:NP', 'Nepal', '尼泊尔', '尼泊尔', '尼泊尔', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (146, 'NR', 'NRU', '520', 'ISO 3166-2:NR', 'Nauru', '瑙鲁', '瑙鲁', '瑙鲁', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (147, 'OM', 'OMN', '512', 'ISO 3166-2:OM', 'Oman', '阿曼', '阿曼', '阿曼', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (148, 'PA', 'PAN', '591', 'ISO 3166-2:PA', 'Panama', '巴拿马', '巴拿马', '巴拿马', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (149, 'PE', 'PER', '604', 'ISO 3166-2:PE', 'Peru', '秘鲁', '秘鲁', '秘鲁', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (150, 'PF', 'PYF', '258', 'ISO 3166-2:PF', 'French polynesia', '法属波利尼西亚', '法属波利尼西亚',
        '法属波利尼西亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (151, 'PG', 'PNG', '598', 'ISO 3166-2:PG', 'Papua New Guinea', '巴布亚新几内亚', '巴布亚新几内亚',
        '巴布亚新几内亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (152, 'PH', 'PHL', '608', 'ISO 3166-2:PH', 'The Philippines', '菲律宾', '菲律宾', '菲律宾', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (153, 'PK', 'PAK', '586', 'ISO 3166-2:PK', 'Pakistan', '巴基斯坦', '巴基斯坦', '巴基斯坦', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (154, 'PL', 'POL', '616', 'ISO 3166-2:PL', 'Poland', '波兰', '波兰', '波兰', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (155, 'PN', 'PCN', '612', 'ISO 3166-2:PN', 'Pitcairn Islands', '皮特凯恩群岛', '皮特康岛', '皮特凯恩群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (156, 'PR', 'PRI', '630', 'ISO 3166-2:PR', 'Puerto Rico', '波多黎各', '波多黎各', '波多黎各', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (157, 'PS', 'PSE', '275', 'ISO 3166-2:PS', 'Palestinian territories', '巴勒斯坦', '巴勒斯坦', '巴勒斯坦', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (158, 'PW', 'PLW', '585', 'ISO 3166-2:PW', 'Palau', '帕劳', '帕劳', '帕劳 帕劳', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (159, 'PY', 'PRY', '600', 'ISO 3166-2:PY', 'Paraguay', '巴拉圭', '巴拉圭', '巴拉圭', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (160, 'QA', 'QAT', '634', 'ISO 3166-2:QA', 'Qatar', '卡塔尔', '卡达', '卡塔尔', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (161, 'RE', 'REU', '638', 'ISO 3166-2:RE', 'Réunion', '留尼汪', '留尼汪', '留尼汪', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (162, 'RO', 'ROU', '642', 'ISO 3166-2:RO', 'Romania', '罗马尼亚', '罗马尼亚', '罗马尼亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (163, 'RS', 'SRB', '688', 'ISO 3166-2:RS', 'Serbia', '塞尔维亚', '塞尔维亚', '塞尔维亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (164, 'RU', 'RUS', '643', 'ISO 3166-2:RU', 'Russian Federation', '俄罗斯', '俄罗斯', '俄罗斯', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (165, 'RW', 'RWA', '646', 'ISO 3166-2:RW', 'Rwanda', '卢旺达', '卢旺达', '卢旺达', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (166, 'SB', 'SLB', '090', 'ISO 3166-2:SB', 'Solomon Islands', '所罗门群岛', '所罗门群岛', '所罗门群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (167, 'SC', 'SYC', '690', 'ISO 3166-2:SC', 'Seychelles', '塞舌尔', '塞舌尔', '塞舌尔', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (168, 'SD', 'SDN', '729', 'ISO 3166-2:SD', 'Sudan', '苏丹', '苏丹', '苏丹', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (169, 'SE', 'SWE', '752', 'ISO 3166-2:SE', 'Sweden', '瑞典', '瑞典', '瑞典', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (170, 'SG', 'SGP', '702', 'ISO 3166-2:SG', 'Singapore', '新加坡', '新加坡 星加坡', '新加坡 星加坡', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (171, 'SI', 'SVN', '705', 'ISO 3166-2:SI', 'Slovenia', '斯洛文尼亚', '斯洛文尼亚', '斯洛文尼亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (172, 'SJ', 'SJM', '744', 'ISO 3166-2:SJ', 'Template:Country data SJM Svalbard', '斯瓦尔巴群岛和 扬马延岛',
        '斯瓦巴及 尖棉岛', '斯瓦尔巴特群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (173, 'SK', 'SVK', '703', 'ISO 3166-2:SK', 'Slovakia', '斯洛伐克', '斯洛伐克', '斯洛伐克', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (174, 'SL', 'SLE', '694', 'ISO 3166-2:SL', 'Sierra Leone', '塞拉利昂', '狮子山', '塞拉利昂', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (175, 'SM', 'SMR', '674', 'ISO 3166-2:SM', 'San Marino', '圣马力诺', '圣马力诺', '圣马力诺', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (176, 'SN', 'SEN', '686', 'ISO 3166-2:SN', 'Senegal', '塞内加尔', '塞内加尔', '塞内加尔', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (177, 'SO', 'SOM', '706', 'ISO 3166-2:SO', 'Somalia', '索马里', '索马里', '索马里', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (178, 'SR', 'SUR', '740', 'ISO 3166-2:SR', 'Suriname', '苏里南', '苏里南', '苏里南', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (179, 'SS', 'SSD', '728', 'ISO 3166-2:SS', 'South Sudan', '南苏丹', '南苏丹', '南苏丹', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (180, 'ST', 'STP', '678', 'ISO 3166-2:ST', 'Sao Tome & Principe', '圣多美和普林西比', '圣多美普林西比',
        '圣多美及普林西比', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (181, 'SV', 'SLV', '222', 'ISO 3166-2:SV', 'El Salvador', '萨尔瓦多', '萨尔瓦多', '萨尔瓦多', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (182, 'SY', 'SYR', '760', 'ISO 3166-2:SY', 'Syria', '叙利亚', '叙利亚', '叙利亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (183, 'SZ', 'SWZ', '748', 'ISO 3166-2:SZ', 'Swaziland', '斯威士兰', '斯威士兰', '斯威士兰', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (184, 'TC', 'TCA', '796', 'ISO 3166-2:TC', 'Turks & Caicos Islands', '特克斯和凯科斯群岛', '土克斯及开科斯群岛',
        '特克斯和凯科斯群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (185, 'TD', 'TCD', '148', 'ISO 3166-2:TD', 'Chad', '乍得', '查德', '乍得', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (186, 'TG', 'TGO', '768', 'ISO 3166-2:TG', 'Togo', '多哥', '多哥', '多哥', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (187, 'TH', 'THA', '764', 'ISO 3166-2:TH', 'Thailand', '泰国', '泰国', '泰国', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (188, 'TK', 'TKL', '772', 'ISO 3166-2:TK', 'Tokelau', '托克劳', '托克劳群岛', '托克劳群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (189, 'TL', 'TLS', '626', 'ISO 3166-2:TP', 'Timor-Leste (East Timor)', '东帝汶', '东帝汶', '东帝汶', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (190, 'TN', 'TUN', '788', 'ISO 3166-2:TN', 'Tunisia', '突尼斯', '突尼斯', '突尼斯', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (191, 'TO', 'TON', '776', 'ISO 3166-2:TO', 'Tonga', '汤加', '东加', '汤加', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (192, 'TR', 'TUR', '792', 'ISO 3166-2:TR', 'Turkey', '土耳其', '土耳其', '土耳其', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (193, 'TV', 'TUV', '798', 'ISO 3166-2:TV', 'Tuvalu', '图瓦卢', '吐瓦鲁', '图瓦卢', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (194, 'TZ', 'TZA', '834', 'ISO 3166-2:TZ', 'Tanzania', '坦桑尼亚', '坦桑尼亚', '坦桑尼亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (195, 'UA', 'UKR', '804', 'ISO 3166-2:UA', 'Ukraine', '乌克兰', '乌克兰', '乌克兰', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (196, 'UG', 'UGA', '800', 'ISO 3166-2:UG', 'Uganda', '乌干达', '乌干达', '乌干达', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (197, 'US', 'USA', '840', 'ISO 3166-2:US', 'United States of America (USA)', '美国', '美国', '美国', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (198, 'UY', 'URY', '858', 'ISO 3166-2:UY', 'Uruguay', '乌拉圭', '乌拉圭', '乌拉圭', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (199, 'VA', 'VAT', '336', 'ISO 3166-2:VA', 'Vatican City (The Holy See)', '梵蒂冈', '梵蒂冈', '梵蒂冈', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (200, 'VE', 'VEN', '862', 'ISO 3166-2:VE', 'Venezuela', '委内瑞拉', '委内瑞拉', '委内瑞拉', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (201, 'VG', 'VGB', '092', 'ISO 3166-2:VG', 'British Virgin Islands', '英属维尔京群岛', '英属维尔京群岛',
        '英属处女群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (202, 'VI', 'VIR', '850', 'ISO 3166-2:VI', 'United States Virgin Islands', '美属维尔京群岛', '美属维尔京群岛',
        '美属处女群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (203, 'VN', 'VNM', '704', 'ISO 3166-2:VN', 'Vietnam', '越南', '越南', '越南', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (204, 'WF', 'WLF', '876', 'ISO 3166-2:WF', 'Wallis and Futuna', '瓦利斯和富图纳', '沃里斯与伏塔那岛',
        '瓦利斯群岛和富图纳群岛', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (205, 'WS', 'WSM', '882', 'ISO 3166-2:WS', 'Samoa', '萨摩亚', '萨摩亚', '萨摩亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (206, 'YE', 'YEM', '887', 'ISO 3166-2:YE', 'Yemen', '也门', '也门', '也门', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (207, 'YT', 'MYT', '175', 'ISO 3166-2:YT', 'Mayotte', '马约特', '美亚特', '马约特', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (208, 'ZA', 'ZAF', '710', 'ISO 3166-2:ZA', 'South Africa', '南非', '南非', '南非', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (209, 'ZM', 'ZMB', '894', 'ISO 3166-2:ZM', 'Zambia', '赞比亚', '赞比亚', '赞比亚', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (210, 'ZW', 'ZWE', '716', 'ISO 3166-2:ZW', 'Zimbabwe', '津巴布韦', '津巴布韦', '津巴布韦', NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (211, 'CN', 'CHN', '156', 'ISO 3166-2:CN', 'China', '中国', '大陆 中国', '大陆 内地',
        '“ GB/T 2659-2000”的“CN”适用于整个 中华人民共和国辖区（包括 中国大陆、 香港、 澳门）。而“ISO 3166-1”和“ CNS 12842”的“CN”则仅适用于中国大陆，不含 港澳地区。');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (212, 'CG', 'COG', '178', 'ISO 3166-2:CG', 'Republic of the Congo', '刚果（布）', '刚果', '刚果',
        '中国大陆主要使用“刚果（布）”一词，意指“首都为 布拉柴维尔的 刚果（共和国）”，而“刚果”一词亦普遍为民间所用。');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (213, 'CD', 'COD', '180', 'ISO 3166-2:CD', 'Democratic Republic of the Congo', '刚果（金）', '民主刚果',
        '民主刚果',
        '中国大陆主要使用“刚果（金）”一词，意指“首都为 金沙萨的 刚果（共和国）”，而“民主刚果”一词亦普遍为民间所用。');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (214, 'MZ', 'MOZ', '508', 'ISO 3166-2:MZ', 'Mozambique', '莫桑比克', '莫桑比克', '莫桑比克',
        '中国大陆和 台湾均曾将之普遍译作“ 莫三比给”');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (215, 'GG', 'GGY', '831', 'ISO 3166-2:GG', 'Guernsey', '根西岛', '根息岛', '根西岛',
        '中国大陆曾将之普遍译作“ 格恩西岛”');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (216, 'GM', 'GMB', '270', 'ISO 3166-2:GM', 'Gambia', '冈比亚', '冈比亚', '冈比亚',
        '亦有部份人士使用“ 刚比亚”一词于 港澳地区');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (217, 'MP', 'MNP', '580', 'ISO 3166-2:MP', 'Northern Mariana Islands', '北马里亚纳群岛', '北马里亚纳群岛',
        '北马里亚纳群岛', '亦有部份人士使用“ 北玛利安娜群岛”一词于 港澳地区');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (218, 'ET', 'ETH', '231', 'ISO 3166-2:ET', 'Ethiopia', '埃塞俄比亚', '埃塞俄比亚', '埃塞俄比亚',
        '亦有部份人士使用“ 埃塞俄比亚”一词于 台湾');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (219, 'NC', 'NCL', '540', 'ISO 3166-2:NC', 'New Caledonia', '新喀里多尼亚', '新喀里多尼亚岛', '新喀里多尼亚',
        '亦有部份人士使用“ 新喀尔多尼亚”一词于 港澳地区');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (220, 'VU', 'VUT', '548', 'ISO 3166-2:VU', 'Vanuatu', '瓦努阿图', '瓦努阿图', '瓦努阿图',
        '亦有部份人士使用“ 瓦努阿图”一词于 港澳地区');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (221, 'TF', 'ATF', '260', 'ISO 3166-2:TF', 'French Southern Territories', '法属南部领地', '法属南部属地',
        '法属南部地区', '台湾亦普遍采用“ 法属南方及南极陆地”一词于其它场合（如 MSN台湾）');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (222, 'NU', 'NIU', '570', 'ISO 3166-2:NU', 'Niue', '纽埃', '纽埃', '纽埃',
        '台湾亦普遍采用“ 纽威岛”（ CNS 12842译名）一词于其它场合（如 MSN台湾）');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (223, 'UM', 'UMI', '581', 'ISO 3166-2:UM', 'United States Minor Outlying Islands', '美国本土外小岛屿',
        '美国边疆小岛', '美国海外小岛', '台湾亦普遍采用“ 美国外岛”一词于其它场合（如 MSN台湾）');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (224, 'CK', 'COK', '184', 'ISO 3166-2:CK', 'Cook Islands', '库克群岛', '库克群岛', '库克群岛',
        '台湾和 香港亦普遍采用“ 科克群岛”（ CNS 12842译名）一词于其它场合');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (225, 'GB', 'GBR', '826', 'ISO 3166-2:GB', 'Great Britain (United Kingdom; England)', '英国', '英国', '英国',
        '台湾和 香港亦普遍采用“ 联合王国”一词于其它场合');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (226, 'TT', 'TTO', '780', 'ISO 3166-2:TT', 'Trinidad & Tobago', '特立尼达和多巴哥', '特立尼达和多巴哥',
        '特立尼达和多巴哥', '台湾和 香港均将之简称为“ 特立尼达”');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (227, 'VC', 'VCT', '670', 'ISO 3166-2:VC', 'St. Vincent & the Grenadines', '圣文森特和格林纳丁斯',
        '圣文森特和格林纳丁斯', '圣文森特和格林纳丁斯', '台湾将之简称为“ 圣文森”');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (228, 'TW', 'TWN', '158', 'ISO 3166-2:TW', 'Taiwan', '中国台湾', '中国台湾', '中国台湾',
        '所用英文名称系依据 国际标准化组织之称呼 [1] [2]所示。台湾地区的国际政治地位可参见 未被国际普遍承认的国家列表、 台海现状以及 旧金山条约。');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (229, 'NZ', 'NZL', '554', 'ISO 3166-2:NZ', 'New Zealand', '新西兰', '新西兰', '新西兰',
        '新加坡与 马来西亚均将之译作“ 新西兰”。 香港亦普遍采用“新西兰”一词于其它场合');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (230, 'SA', 'SAU', '682', 'ISO 3166-2:SA', 'Saudi Arabia', '沙特阿拉伯', '沙特阿拉伯', '沙特阿拉伯',
        '新加坡与 马来西亚均将之译作“ 沙特阿拉伯”。 香港亦普遍采用“沙特阿拉伯”一词于其它场合');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (231, 'LA', 'LAO', '418', 'ISO 3166-2:LA', 'Laos', '老挝', '老挝', '老挝', '新加坡与 马来西亚均将之译作“老挝”');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (232, 'KP', 'PRK', '408', 'ISO 3166-2:KP', 'North Korea', '朝鲜 北朝鲜', '北朝鲜', '朝鲜 北朝鲜',
        '澳门习惯称之为“ 北朝鲜”');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (233, 'KR', 'KOR', '410', 'ISO 3166-2:KR', 'South Korea', '韩国 南朝鲜', '韩国 韩国', '韩国 韩国',
        '澳门习惯称之为“ 韩国”');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (234, 'PT', 'PRT', '620', 'ISO 3166-2:PT', 'Portugal', '葡萄牙', '葡萄牙', '葡萄牙', '澳门民间亦普遍称之为葡国');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (235, 'KG', 'KGZ', '417', 'ISO 3166-2:KG', 'Kyrgyzstan', '吉尔吉斯斯坦', '吉尔吉斯', '吉尔吉斯',
        '香港习惯略去“斯坦”后缀，有必要会用全称');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (236, 'KZ', 'KAZ', '398', 'ISO 3166-2:KZ', 'Kazakhstan', '哈萨克斯坦', '哈萨克', '哈萨克',
        '香港习惯略去“斯坦”后缀，有必要会用全称');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (237, 'TJ', 'TJK', '762', 'ISO 3166-2:TJ', 'Tajikistan', '塔吉克斯坦', '塔吉克', '塔吉克',
        '香港习惯略去“斯坦”后缀，有必要会用全称');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (238, 'TM', 'TKM', '795', 'ISO 3166-2:TM', 'Turkmenistan', '土库曼斯坦', '土库曼', '土库曼',
        '香港习惯略去“斯坦”后缀，有必要会用全称');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (239, 'UZ', 'UZB', '860', 'ISO 3166-2:UZ', 'Uzbekistan', '乌兹别克斯坦', '乌兹别克', '乌兹别克',
        '香港习惯略去“斯坦”后缀，有必要会用全称');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (240, 'KN', 'KNA', '659', 'ISO 3166-2:KN', 'St. Kitts & Nevis', '圣基茨和尼维斯', '圣基茨和尼维斯',
        '圣基茨和尼维斯',
        '香港亦普遍采用“ 圣克里斯托佛岛及尼维斯岛”一词于其它场合（如 香港邮政的邮政指南附录表）。亦有部份人士使用“ 圣基茨和尼维斯”一词于 港澳地区');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (241, 'PM', 'SPM', '666', 'ISO 3166-2:PM', 'Saint-Pierre and Miquelon', '圣皮埃尔和密克隆', '圣皮耶与密克隆群岛',
        '圣皮埃尔岛和密克隆岛', '香港亦普遍采用“ 圣皮埃兰和密克隆群岛”一词于其它场合（如 香港邮政的邮政指南附录表）');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (242, 'SH', 'SHN', '654', 'ISO 3166-2:SH', 'St. Helena & Dependencies', '圣赫勒拿', '圣赫勒拿岛', '圣赫勒拿',
        '香港亦普遍采用“ 圣赫勒拿岛”一词于其它场合（如 香港邮政的邮政指南附录表）。亦有部份人士使用“ 圣海伦娜岛”一词于 港澳地区');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (243, 'LC', 'LCA', '662', 'ISO 3166-2:LC', 'St. Lucia', '圣卢西亚', '圣卢西亚', '圣卢西亚',
        '香港亦普遍采用“ 圣路西亚”一词于其它场合');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (244, 'MU', 'MUS', '480', 'ISO 3166-2:MU', 'Mauritius', '毛里求斯', '毛里求斯', '毛里求斯',
        '香港亦普遍采用“ 毛里求斯”一词于其它场合');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (245, 'CI', 'CIV', '384', 'ISO 3166-2:CI', 'C?te d\'Ivoire', '科特迪瓦', '科特迪瓦', '科特迪瓦',
        '香港亦普遍采用“科特迪瓦”一词于其它场合（如 香港邮政的邮政指南附录表）');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (246, 'KE', 'KEN', '404', 'ISO 3166-2:KE', 'Kenya', '肯尼亚', '肯尼亚', '肯尼亚',
        '香港亦普遍采用“ 肯尼亚”一词于其它场合');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (247, 'MN', 'MNG', '496', 'ISO 3166-2:MN', 'Mongolia', '蒙古国', '蒙古', '蒙古国',
        '香港亦普遍采用“ 蒙古”一词于其它场合');
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (248, 'UK', 'UNKNOWN', '998', NULL, 'Unknown', '未知国家地区', NULL, NULL, NULL);
INSERT INTO `t_data_region`(`id`, `code2`, `code3`, `number`, `iso_code`, `iso_name`, `cn_name`, `tw_name`, `hk_name`,
                            `remark`)
VALUES (249, 'TS', 'TEST', '999', 'test', 'Test', '测试', NULL, NULL, NULL);
