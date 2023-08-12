/* 基础数据服务及功能菜单（编号50-100）  */

/*================基础数据================*/

DELETE
FROM t_SERVICE_LIST
WHERE S_ID = '6';

insert into t_SERVICE_LIST (S_ID, S_ICON, S_NAME, S_ABB, S_INTRO, S_VENDOR, S_PATH, S_AUTHC, S_TYPE, S_CATEGORY, S_REC,
                            S_STATUS, S_ORDER)
values ('6', 'static/images/logo/base-data.svg', '基础数据', '基础数据', null, 'Jeebiz', '/baseinfo', 'JWT', '0', '1',
        '1', '1', '50');

DELETE
FROM t_SERVICE_PERMS
WHERE S_ID = '6';

INSERT INTO t_SERVICE_PERMS (S_ID, R_ID)
VALUES ('6', '1');

DELETE
FROM t_AUTHZ_FEATURE_LIST
WHERE F_ID in ('50', '51', '52', '53', '54', '55', '56', '57', '58', '59', '60', '61');
DELETE
FROM t_AUTHZ_FEATURE_OPTS
WHERE F_ID in ('50', '51', '52', '53', '54', '55', '56', '57', '58', '59', '60', '61');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
values ('50', '基础数据', '基础数据', 'baseinfo', '#', '/baseinfo', '1', null, '6', '1', '50');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
values ('51', '学校信息', '学校信息', 'university-mgt', '/baseinfo/university', '/baseinfo/university-mgt', '1', null,
        '50', '1', '51');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('51', '学校基本信息详情', null, '0', 'university:detail', '1');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('51', '编辑学校信息', null, '0', 'university:renew', '2');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
values ('52', '校区管理', '校区管理', 'campus-mgt', '/baseinfo/campus', '/baseinfo/campus-mgt', '1', null, '50', '1',
        '52');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('52', '校区信息列表', null, '0', 'campus:list', '1');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('52', '删除校区信息', null, '0', 'campus:delete', '1');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('52', '根据校区ID查询校区信息', null, '0', 'campus:detail', '2');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('52', '新增校区信息', null, '0', 'campus:new', '3');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('52', '修改校区信息', null, '0', 'campus:renew', '4');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('52', '是否启用校区信息', null, '0', 'campus:status', '5');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
values ('54', '部门管理', '部门管理', 'dept-mgt', '/baseinfo/depts', '/baseinfo/dept-mgt', '1', null, '50', '1', '54');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('54', '部门信息列表', null, '0', 'dept:list', '1');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('54', '添加部门信息', null, '0', 'dept:new', '2');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('54', '修改部门信息', null, '0', 'dept:renew', '3');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('54', '批量删除部门信息', null, '0', 'dept:batch-delete', '4');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('54', '删除部门信息', null, '0', 'dept:delete', '5');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('54', '导入部门信息', null, '0', 'dept:import', '6');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('54', '导出部门信息', null, '0', 'dept:export', '7');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('54', '查看部门信息', null, '0', 'dept:detail', '8');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
values ('55', '学年学期', '学年学期', 'xnxq-mgt', '/baseinfo/xnxq', '/baseinfo/xnxq-mgt', '1', null, '50', '1', '55');

INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('55', '查询学年学期', '', '0', 'xnxq:list', 1);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('55', '创建学年学期', '', '1', 'xnxq:new', 2);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('55', '删除学年学期', '', '1', 'xnxq:delete', 3);
INSERT INTO t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('55', '修改学年学期', '', '0', 'xnxq:renew', 4);


insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
values ('56', '学院管理', '学院管理', 'college-mgt', '/baseinfo/colleges', '/baseinfo/college-mgt', '1', null, '50',
        '1', '56');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('56', '学院信息列表', null, '0', 'collegeinfo:list', '1');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('56', '批量删除学院信息', null, '0', 'collegeinfo:batch-delete', '2');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('56', '导入学院信息', null, '0', 'collegeinfo:import', '3');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('56', '导出学院信息', null, '0', 'collegeinfo:export', '4');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('56', '添加信息学院', null, '0', 'collegeinfo:new', '5');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('56', '修改学院信息', null, '0', 'collegeinfo:renew', '6');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('56', '删除学院信息', null, '0', 'collegeinfo:delete', '7');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
values ('57', '专业管理', '专业管理', 'major-mgt', '/baseinfo/majors', '/baseinfo/major-mgt', '1', null, '50', '1',
        '57');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('57', '专业信息列表', null, '0', 'major:list', '2');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('57', '删除专业信息', null, '0', 'major:delete', '3');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('57', '新增专业信息', null, '0', 'major:new', '4');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('57', '修改专业信息', null, '0', 'major:renew', '5');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('57', '导入专业信息', null, '0', 'major:import', '6');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('57', '导出专业信息', null, '0', 'major:export', '7');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
values ('58', '班级管理', '班级管理', 'class-mgt', '/baseinfo/classes', '/baseinfo/class-mgt', '1', null, '50', '1',
        '58');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('58', '班级信息列表', null, '0', 'classinfo:list', '1');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('58', '添加班级信息', null, '1', 'classinfo:new', '2');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('58', '修改班级信息', null, '0', 'classinfo:renew', '3');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('58', '删除班级信息', null, '0', 'classinfo:delete', '4');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('58', '批量删除班级信息', null, '1', 'classinfo:batch-delete', '5');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('58', '班级信息详细', null, '0', 'classinfo:detail', '6');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('58', '导入班级信息', null, '1', 'classinfo:import', '7');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('58', '导出班级信息', null, '1', 'classinfo:export', '8');

insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
values ('59', '教职工管理', '教职工管理', 'teacher-mgt', '/baseinfo/teachers', '/baseinfo/teacher/list', '1', null,
        '50', '1', '59');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '教师信息查询', null, '0', 'teacher:list', '1');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '新增教师信息', null, '0', 'teacher:new', '2');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '修改教师信息', null, '0', 'teacher:renew', '3');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '批量删除教师信息', null, '0', 'teacher:batch-delete', '4');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '删除教师信息', null, '0', 'teacher:delete', '5');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '教师信息详情', null, '0', 'teacher:detail', '6');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '导入教师信息', null, '0', 'teacher:import', '7');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '导出教师信息', null, '0', 'teacher:export', '8');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '查询工作经历', null, '0', 'teacher-workexperience:list', '9');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '新增工作经历', null, '0', 'teacher-workexperience:new', '10');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '修改工作经历', null, '0', 'teacher-workexperience:renew', '11');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '工作经历详情', null, '0', 'teacher-workexperience:detail', '12');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '删除工作经历', null, '0', 'teacher-workexperience:delete', '13');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '查询学历学位', null, '0', 'teacher-degree:list', '14');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '新增学历学位', null, '0', 'teacher-degree:new', '15');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '修改学历学位', null, '0', 'teacher-degree:renew', '16');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '学历学位详情', null, '0', 'teacher-degree:detail', '17');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '删除学历学位', null, '0', 'teacher-degree:delete', '18');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '查询行政职位', null, '0', 'teacher-administrative:list', '19');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '新增行政职位', null, '0', 'teacher-administrative:new', '20');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '修改行政职位', null, '0', 'teacher-administrative:renew', '21');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '行政职位详情', null, '0', 'teacher-administrative:detail', '22');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '删除行政职位', null, '0', 'teacher-administrative:delete', '23');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '查询专业职务', null, '0', 'teacher-majorpost:list', '24');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '新增专业职务', null, '0', 'teacher-majorpost:new', '25');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '修改专业职务', null, '0', 'teacher-majorpost:renew', '26');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '删除专业职务', null, '0', 'teacher-majorpost:delete', '27');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '查询职业培训', null, '0', 'teacher-train:list', '28');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '新增职业培训', null, '0', 'teacher-train:new', '29');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '修改职业培训', null, '0', 'teacher-train:renew', '50');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '职业培训详情', null, '0', 'teacher-train:detail', '51');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '删除职业培训', null, '0', 'teacher-train:delete', '52');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '查询项目学习记录', null, '0', 'teacher-projectlearning:list', '53');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '新增项目学习记录', null, '0', 'teacher-projectlearning:new', '54');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '修改项目学习记录', null, '0', 'teacher-projectlearning:renew', '55');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '项目学习记录详情', null, '0', 'teacher-projectlearning:detail', '56');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '删除项目学习记录', null, '0', 'teacher-projectlearning:delete', '57');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '查询职业证书', null, '0', 'teacher-certificate:list', '58');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '新增职业证书', null, '0', 'teacher-certificate:new', '59');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '修改职业证书', null, '0', 'teacher-certificate:renew', '60');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '职业证书详情', null, '0', 'teacher-certificate:detail', '61');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '删除职业证书', null, '0', 'teacher-certificate:delete', '42');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '查询高校交流记录', null, '0', 'teacher-communication:list', '43');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '新增高校交流记录', null, '0', 'teacher-communication:new', '44');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '修改高校交流记录', null, '0', 'teacher-communication:renew', '45');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '高校交流详情', null, '0', 'teacher-communication:detail', '46');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '删除高校交流记录', null, '0', 'teacher-communication:delete', '47');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '查询科研情况', null, '0', 'teacher-scientific:list', '48');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '新增科研情况', null, '0', 'teacher-scientific:new', '49');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '修改科研情况', null, '0', 'teacher-scientific:renew', '50');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '科研情况详情', null, '0', 'teacher-scientific:detail', '51');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '删除科研情况', null, '0', 'teacher-scientific:delete', '52');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '查询奖励情况', null, '0', 'teacher-reward:list', '53');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '新增奖励情况', null, '0', 'teacher-reward:new', '54');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '修改奖励情况', null, '0', 'teacher-reward:renew', '55');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '奖励情况详情', null, '0', 'teacher-reward:detail', '56');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('59', '删除奖励情况', null, '0', 'teacher-reward:delete', '57');


insert into t_AUTHZ_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_PATH, F_TYPE, F_ICON, F_PARENT, F_VISIBLE,
                                  F_ORDER)
values ('60', '学生管理', '学生管理', 'student-mgt', '/baseinfo/students', '/baseinfo/student/list', '1', null, '50',
        '1', '60');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询学生列表', null, '0', 'student:list', '1');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '新增学生信息', null, '0', 'student:new', '2');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '修改学生信息', null, '0', 'student:renew', '3');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '删除学生信息', null, '0', 'student:delete', '4');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '学生详细信息', null, '0', 'student:detail', '5');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '导入学生信息', null, '0', 'student:import', '6');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '导出学生信息', null, '0', 'student:export', '7');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询家庭成员', null, '0', 'student-family:list', '8');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '新增家庭成员', null, '0', 'student-family:new', '9');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '修改家庭成员', null, '0', 'student-family:renew', '10');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '删除家庭成员', null, '0', 'student-family:delete', '11');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询个人经历', null, '0', 'student-exp:list', '12');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '新增个人经历', null, '0', 'student-exp:new', '13');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '修改个人经历', null, '0', 'student-exp:renew', '14');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '删除个人经历', null, '0', 'student-exp:delete', '15');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询奖励信息', null, '0', 'student-bonus:list', '16');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '添加奖励信息', null, '0', 'student-bonus:new', '17');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '修改奖励信息', null, '0', 'student-bonus:renew', '18');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询奖励详情', null, '0', 'student-bonus:detail', '19');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '删除奖励信息', null, '0', 'student-bonus:delete', '20');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询惩罚信息', null, '0', 'student-penalty:list', '21');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '添加惩罚信息', null, '0', 'student-penalty:new', '22');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '修改惩罚信息', null, '0', 'student-penalty:renew', '23');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询惩罚详情', null, '0', 'student-penalty:detail', '24');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '删除惩罚信息', null, '0', 'student-penalty:delete', '25');

insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询访谈记录', null, '0', 'student-interview:list', '26');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询重点关注记录', null, '0', 'student-focus:list', '27');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询资助信息', null, '0', 'student-scholarship:list', '28');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询学习成绩', null, '0', 'student-grade:list', '29');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询访谈记录新增', null, '0', 'student-interview:new', '50');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询访谈记录删除', null, '0', 'student-interview:dtlete', '51');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询访谈记录修改', null, '0', 'student-interview:renew', '52');
insert into t_AUTHZ_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
values ('60', '查询访谈记录详情', null, '0', 'student-interview:detail', '53');

COMMIT;
