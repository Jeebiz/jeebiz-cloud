/* 基础服务及功能菜单（编号1-50）  */

/*================服务分类================*/

DELETE
FROM t_SERVICE_CATEGORY
WHERE C_CODE in ('1');

INSERT INTO t_SERVICE_CATEGORY (C_CODE, C_NAME, C_TYPE, C_STATUS, C_ORDER)
VALUES ('1', '系统管理', '1', '1', '1');

/*================服务管理================*/

DELETE
FROM t_SERVICE_LIST
WHERE S_ID = '1';

insert into t_SERVICE_LIST (S_ID, S_ICON, S_NAME, S_ABB, S_INTRO, S_VENDOR, S_PATH, S_AUTHC, S_TYPE, S_CATEGORY, S_REC,
                            S_STATUS, S_ORDER)
values ('1', 'static/images/logo/base-dict.svg', '服务管理', '服务管理', null, 'Jeebiz', '/servmgt', 'JWT', '0', '1',
        '1', '1', '1');

DELETE
FROM t_SERVICE_PERMS
WHERE S_ID = '1';
INSERT INTO t_SERVICE_PERMS (S_ID, R_ID)
VALUES ('1', '1');

DELETE
FROM t_AUTHZ_FEATURE_LIST
WHERE F_ID in ('5', '6', '7');
DELETE
FROM t_AUTHZ_FEATURE_OPTS
WHERE F_ID in ('5', '6', '7');

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER)
VALUES ('5', '服务管理', '服务管理', 'serv', '#', '1', '', '1', '1', '5');

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
VALUES ('6', '服务管理', '服务管理', 'serv-list', '/servmgt/list', '/servmgt/list/index', '1', '', '5', '1', '6');

INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('6', '服务列表', '', '0', 'serv:list', 1);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('6', '服务接入', '', '1', 'serv:new', 2);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('6', '停用/启用', '', '0', 'serv:status', 3);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('6', '修改服务', '', '0', 'serv:renew', 4);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('6', '服务详情', '', '0', 'serv:detail', 5);

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
VALUES ('7', '服务分组', '服务分组', 'serv-category', '/servmgt/category', '/servmgt/category/index', '1', '', '5', '1',
        '7');

INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('7', '分组列表', '', '0', 'serv-category:list', 1);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('7', '创建分组', '', '1', 'serv-category:new', 2);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('7', '修改分组', '', '0', 'serv-category:renew', 3);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('7', '停用/启用', '', '0', 'serv-category:status', 4);


/*================数据字典================*/

DELETE
FROM t_SERVICE_LIST
WHERE S_ID = '2';

insert into t_SERVICE_LIST (S_ID, S_ICON, S_NAME, S_ABB, S_INTRO, S_VENDOR, S_PATH, S_AUTHC, S_TYPE, S_CATEGORY, S_REC,
                            S_STATUS, S_ORDER)
values ('2', 'static/images/logo/base-dict.svg', '数据字典', '数据字典', null, 'Jeebiz', '/settings', 'JWT', '0', '1',
        '1', '1', '2');

DELETE
FROM t_SERVICE_PERMS
WHERE S_ID = '2';

INSERT INTO t_SERVICE_PERMS (S_ID, R_ID)
VALUES ('2', '1');

DELETE
FROM t_AUTHZ_FEATURE_LIST
WHERE F_ID in ('10', '11', '12', '13', '14');
DELETE
FROM t_AUTHZ_FEATURE_OPTS
WHERE F_ID in ('10', '11', '12', '13', '14');

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER)
VALUES ('10', '数据字典', '数据字典', 'sets', '#', '1', 'icon-set', '2', '1', '10');

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
VALUES ('11', '数据字典', '数据字典', 'keyvalue', '/keyvalue/list', 'settings/basic/index', '1', '', '10', '1', '11');

INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('11', '查询数据', '', '0', 'keyvalue:list', 1);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('11', '创建数据', '', '1', 'keyvalue:new', 2);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('11', '删除数据', '', '1', 'keyvalue:delete', 3);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('11', '修改数据', '', '0', 'keyvalue:renew', 4);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('11', '启用/停用', '', '0', 'keyvalue:status', 5);

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
VALUES ('12', '数据分组', '数据分组', 'keygroup', '/keygroup/list', '/settings/group/index', '1', '', '10', '1', '12');
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('12', '查询分组', '', '0', 'keygroup:list', 1);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('12', '创建分组', '', '1', 'keygroup:new', 2);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('12', '删除分组', '', '1', 'keygroup:delete', 3);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('12', '修改分组', '', '0', 'keygroup:renew', 2);

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
VALUES ('13', '系统设置', '系统设置', 'setting', '/settings/list', '/settings/params/index', '1', '', '10', '1', '13');
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('13', '查询设置', '', '0', 'setting:list', 1);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('13', '修改设置', '', '0', 'setting:renew', 2);

/*================权限管理================*/

DELETE
FROM t_SERVICE_LIST
WHERE S_ID = '3';

insert into t_SERVICE_LIST (S_ID, S_ICON, S_NAME, S_ABB, S_INTRO, S_VENDOR, S_PATH, S_AUTHC, S_TYPE, S_CATEGORY, S_REC,
                            S_STATUS, S_ORDER)
values ('3', 'static/images/logo/base-authz.svg', '权限管理', '权限管理', null, 'Jeebiz', '/authz', 'JWT', '0', '1',
        '1', '1', '3');

DELETE
FROM t_SERVICE_PERMS
WHERE S_ID = '3';

INSERT INTO t_SERVICE_PERMS (S_ID, R_ID)
VALUES ('3', '1');

DELETE
FROM t_AUTHZ_FEATURE_LIST
WHERE F_ID in ('20', '21', '22', '23', '24');
DELETE
FROM t_AUTHZ_FEATURE_OPTS
WHERE F_ID in ('20', '21', '22', '23', '24');

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER)
VALUES ('20', '权限管理', '权限管理', 'authz-perms', '#', '1', 'layui-icon-user', '3', '1', '20');

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
VALUES ('21', '角色管理', '角色管理', 'role', '/authz/role', '/authz/role/list', '1', '', '20', '1', '21');

INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '查询角色', '', '0', 'role:list', 1);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '增加角色', '', '1', 'role:new', 2);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '删除角色', '', '1', 'role:delete', 3);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '修改角色', '', '0', 'role:renew', 4);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '启用/停用', '', '0', 'role:status', 5);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '服务授权', '', '0', 'role:perms', 6);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '分配用户', '', '0', 'role:allot', 7);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '角色详情', '', '0', 'role:detail', 8);

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
VALUES ('22', '用户管理', '用户管理', 'user', '/authz/user', '/authz/user/list', '1', '', '20', '1', '22');

INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('22', '查询用户', '', '0', 'user:list', 1);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('22', '增加用户', '', '1', 'user:new', 2);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('22', '删除用户', '', '1', 'user:delete', 3);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('22', '修改用户', '', '0', 'user:renew', 4);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('22', '启用/停用', '', '0', 'user:status', 5);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('22', '初始化密码', '', '1', 'user:resetpwd', 6);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('22', '用户详情', '', '0', 'user:detail', 7);

-- INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER)
-- VALUES ('23', '数据规则', '权限规则', 'authz-dbperms', '/authz/dbperms', '/authz/dbperms/perms-list', '1', '', '20', '1', '23');
-- INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
-- VALUES ('23', '查询权限规则', '', '0', 'authz-dbperms:list', 1);
-- INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
-- VALUES ('23', '新增权限规则', '', '1', 'authz-dbperms:new', 2);
-- INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
-- VALUES ('23', '删除权限规则', '', '1', 'authz-dbperms:delete', 3);
-- INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
-- VALUES ('23', '修改权限规则', '', '0', 'authz-dbperms:renew', 4);
-- INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
-- VALUES ('23', '启用/停用', '', '0', 'authz-dbperms:status', 5);
-- INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
-- VALUES ('23', '权限规则详情', '', '0', 'authz-dbperms:detail', 6);

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
VALUES ('24', '数据权限', '数据权限', 'authz-dbperms-group', '/authz/dbperms-group', '/authz/dbperms/group-list', '1',
        '', '20', '1', '24');

INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('24', '查询数据权限', '', '0', 'authz-dbperms-group:list', 1);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('24', '增加数据权限', '', '1', 'authz-dbperms-group:new', 2);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('24', '删除数据权限', '', '1', 'authz-dbperms-group:delete', 3);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('24', '修改数据权限', '', '0', 'authz-dbperms-group:renew', 4);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('24', '启用/停用', '', '0', 'authz-dbperms-group:status', 5);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('24', '数据权限组详情', '', '0', 'authz-dbperms-group:detail', 6);

/*================安全审计================*/

DELETE
FROM t_SERVICE_LIST
WHERE S_ID = '4';

insert into t_SERVICE_LIST (S_ID, S_ICON, S_NAME, S_ABB, S_INTRO, S_VENDOR, S_PATH, S_AUTHC, S_TYPE, S_CATEGORY, S_REC,
                            S_STATUS, S_ORDER)
values ('4', 'static/images/logo/base-inspect.svg', '安全审计', '安全审计', null, 'Jeebiz', '/logs', 'JWT', '0', '1',
        '1', '1', '4');

DELETE
FROM t_SERVICE_PERMS
WHERE S_ID = '4';

INSERT INTO t_SERVICE_PERMS (S_ID, R_ID)
VALUES ('4', '1');

DELETE
FROM t_AUTHZ_FEATURE_LIST
WHERE F_ID in ('30', '31', '32');
DELETE
FROM t_AUTHZ_FEATURE_OPTS
WHERE F_ID in ('30', '31', '32');

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER)
VALUES ('30', '安全审计', '安全审计', 'feature', '#', '1', '', '4', '1', '30');

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
VALUES ('31', '登录日志', '登录日志', 'serv-category', '/logs/authz', '/logs/attestation/attestation', '1', '', '30',
        '1', '31');

INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('31', '日志列表', '', '0', 'serv-category:list', 1);

INSERT INTO t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
VALUES ('32', '操作日志', '操作日志', 'serv-category', '/logs/biz', '/logs/business/business', '1', '', '30', '1',
        '32');

INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('32', '日志列表', '', '0', 'serv-category:list', 1);


/*================通知公告================*/

DELETE
FROM t_SERVICE_LIST
WHERE S_ID = '5';

insert into t_SERVICE_LIST (S_ID, S_ICON, S_NAME, S_ABB, S_INTRO, S_VENDOR, S_PATH, S_AUTHC, S_TYPE, S_CATEGORY, S_REC,
                            S_STATUS, S_ORDER, S_INNER)
values ('5', 'static/images/logo/base-article.svg', '通知公告', '通知公告', '通知公告', 'Jeebiz', '/article', 'JWT',
        '1', '1', '1', '1', '5', '1');

DELETE
FROM t_SERVICE_PERMS
WHERE S_ID = '5';

INSERT INTO t_SERVICE_PERMS (S_ID, R_ID)
VALUES ('5', '1');

DELETE
FROM t_AUTHZ_FEATURE_LIST
WHERE F_ID in ('40', '41', '42', '43', '44');
DELETE
FROM t_AUTHZ_FEATURE_OPTS
WHERE F_ID in ('40', '41', '42', '43', '44');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_ORDER, F_PARENT, F_VISIBLE)
values ('40', '通知公告', '通知公告', 'article', '#', '1', null, '40', '5', '1');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_ORDER, F_PARENT,
                                  F_VISIBLE)
values ('41', '通知公告列表', '通知公告列表', 'article-list', '/article/article-list', '/article/article-list', '1',
        null, '41', '40', '1');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '通知公告查询', null, '0', 'article:list', '7');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '添加通知公告', null, '1', 'article:new', '2');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '修改通知公告', null, '0', 'article:renew', '3');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '删除通知公告', null, '0', 'article:delete', '4');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '通知公告详情', null, '0', 'article:detail', '5');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '发布通知公告（管理员）', null, '0', 'article:deploy', '6');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '通知公告查询（移动端）', null, '0', 'article-app:list', '7');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '通知公告详情（移动端）', null, '0', 'article-app:detail', '8');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '查询评论（管理员）', null, '0', 'article-comment:list', '6');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '查询评论（评论人）', null, '0', 'article-comment:list-own', '7');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '新增评论', null, '0', 'article-comment:new', '8');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '编辑评论', null, '0', 'article-comment:renew', '8');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '审核评论', null, '0', 'article-comment:review', '9');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '删除评论（管理员）', null, '0', 'article-comment:delete', '10');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('41', '删除评论（评论人）', null, '0', 'article-comment:delete-own ', '11');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_ORDER, F_PARENT,
                                  F_VISIBLE)
values ('42', '通知公告栏目', '通知公告栏目', 'article-topic', '/article/topic-list', '/article/topic-list', '1', null,
        '42', '40', '1');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('42', '通知公告栏目查询', null, '0', 'article-topic:list', '1');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('42', '新增通知公告栏目', null, '1', 'article-topic:new', '2');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('42', '修改通知公告栏目', null, '0', 'article-topic:renew', '3');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('42', '删除通知公告栏目', null, '0', 'article-topic:delete', '4');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('42', '通知公告栏目详情', null, '0', 'article-topic:detail', '5');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_ORDER, F_PARENT,
                                  F_VISIBLE)
values ('43', '通知公告分类', '通知公告分类', 'article-category', '/article/category-list', '/article/category-list',
        '1', null, '43', '40', '1');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('43', '通知公告分类查询', null, '0', 'article-category:list', '1');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('43', '新增通知公告分类', null, '1', 'article-category:new', '2');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('43', '修改通知公告分类', null, '0', 'article-category:renew', '3');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('43', '删除通知公告分类', null, '0', 'article-category:delete', '4');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('43', '通知公告分类详情', null, '0', 'article-category:detail', '5');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_ORDER, F_PARENT,
                                  F_VISIBLE)
values ('44', '我的通知公告', '我的通知公告', 'article-forme', '/article/category-forme', '/article/article-forme', '1',
        null, '44', '40', '1');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('44', '通知公告查询', null, '0', 'article-forme:list', '1');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('44', '通知公告详情', null, '0', 'article-forme:detail', '2');

COMMIT;
