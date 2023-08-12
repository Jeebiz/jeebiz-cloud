/*
 * 权限核心表：
 * 1、应用信息表
 */

-- Create table
create table APPLICATION_INFO
(
    APP_ID VARCHAR2 (32) default sys_guid(),
    APP_NAME VARCHAR2 (50) not null,
    APP_TYPE VARCHAR2 (2) not null,
    APP_DESC VARCHAR2 (1000),
    primary key (APP_ID)
);
-- Add comments to the table 
comment on table APPLICATION_INFO  is '应用信息表';
-- Add comments to the columns 
comment on column APPLICATION_INFO.APP_ID  is '应用信息表ID';
comment on column APPLICATION_INFO.APP_NAME  is '应用名称';
comment on column APPLICATION_INFO.APP_TYPE  is '应用类型';
comment on column APPLICATION_INFO.APP_DESC  is '应用说明';

/* 
 * 权限核心表：
 * 2、角色信息表、角色-用户关系表、角色-用户组关系表、角色-权限关系表（角色-菜单-按钮）、
 */

-- Create table
create table ROLE_INFO
(
    R_ID VARCHAR2 (32) default sys_guid(),
    R_NAME VARCHAR2 (50) not null,
    R_TYPE VARCHAR2 (2) default 1,
    R_DESC VARCHAR2 (1000),
    R_STATUS VARCHAR2 (2) default 1,
    primary key (R_ID)
);
-- Add comments to the table 
comment on table ROLE_INFO  is '角色信息表';
-- Add comments to the columns 
comment on column ROLE_INFO.R_ID  is '角色ID';
comment on column ROLE_INFO.R_NAME  is '角色名称';
comment on column ROLE_INFO.R_TYPE  is '角色类型(1:原生|2:继承|3:复制)';
comment on column ROLE_INFO.R_DESC  is '角色说明';
comment on column ROLE_INFO.R_STATUS  is '角色状态(0:不可用|1:正常|2:锁定)';

-- Create table
create table ROLE_USER_RELATION
(
    R_ID VARCHAR2 (32) not null,
    U_ID VARCHAR2 (32) not null,
    primary key (R_ID, U_ID)
);
-- Add comments to the table 
comment on table ROLE_USER_RELATION  is '角色-用户关系表';
-- Add comments to the columns 
comment on column ROLE_USER_RELATION.R_ID  is '角色ID';
comment on column ROLE_USER_RELATION.U_ID  is '用户ID';

-- Create table
create table ROLE_PERMISSION_RELATION
(
    R_ID VARCHAR2 (32) not null,
    PERMS VARCHAR2 (20),
    primary key (R_ID, PERMS)
);
-- Add comments to the table 
comment on table ROLE_USER_GROUP_RELATION  is '角色-权限关系表（角色-菜单-按钮）';
-- Add comments to the columns 
comment on column ROLE_USER_GROUP_RELATION.R_ID  is '角色ID';
comment on column ROLE_USER_GROUP_RELATION.PERMS  is '权限标记(等同MENU_OPERATION_RELATION.PERMS)';

/* 
 * 权限核心表：
 * 3、用户信息表、用户详情表、用户认证信息表、用户-岗位关系表、用户组信息表、用户组-用户关系表、用户组-权限关系表（用户组-菜单-按钮）
 */

-- Create table
create table USER_INFO
(
    U_ID VARCHAR2 (32) default sys_guid(),
    U_USERNAME VARCHAR2 (50) not null,
    U_PASSWORD VARCHAR2 (2) not null,
    U_PHONE VARCHAR2 (1000),
    U_EMAIL VARCHAR2 (1000),
    U_STATUS VARCHAR2 (1),
    primary key (U_ID)
);
-- Add comments to the table 
comment on table USER_INFO  is '用户信息表';
-- Add comments to the columns 
comment on column USER_INFO.U_ID  is '用户ID';
comment on column USER_INFO.U_USERNAME  is '用户名';
comment on column USER_INFO.U_PASSWORD  is '密码';
comment on column USER_INFO.U_PHONE  is '手机号码';
comment on column USER_INFO.U_EMAIL  is '邮箱地址';
comment on column USER_INFO.U_STATUS  is '用户状态(0:不可用|1:正常|2:锁定)';

-- Create table
create table USER_DETAIL
(
    U_ID VARCHAR2 (32) not null,
    D_ID VARCHAR2 (32) default sys_guid(),
    D_NICKNAME VARCHAR2 (50),
    D_BIRTHDAY VARCHAR2 (20),
    D_GENDER VARCHAR2 (10),
    D_IDCARD VARCHAR2 (20),
    primary key (D_ID)
);
-- Add comments to the table 
comment on table USER_DETAIL  is '用户详情表';
-- Add comments to the columns 
comment on column USER_DETAIL.D_ID  is '用户详情表ID';
comment on column USER_DETAIL.U_ID  is '用户ID';
comment on column USER_DETAIL.D_NICKNAME  is '用户昵称';
comment on column USER_DETAIL.D_BIRTHDAY  is '出生日期';
comment on column USER_DETAIL.D_GENDER  is '性别';
comment on column USER_DETAIL.D_IDCARD  is '身份证号码';

create table USER_AUTH
(
    U_ID VARCHAR2 (32) not null,
    O_ID VARCHAR2 (32) default sys_guid(),
    O_VENDOR VARCHAR2 (20),
    O_UID VARCHAR2 (50),
    primary key (O_ID)
);
-- Add comments to the table 
comment on table USER_AUTH  is '用户认证信息表';
-- Add comments to the columns 
comment on column USER_AUTH.O_ID  is '用户认证信息表ID';
comment on column USER_AUTH.U_ID  is '用户ID';
comment on column USER_AUTH.O_VENDOR  is '关联的认证供应商类型';
comment on column USER_AUTH.O_UID  is '关联的认证账号UID';

-- Create table
create table USER_POST_RELATION
(
    U_ID VARCHAR2 (32) not null,
    P_ID VARCHAR2 (32) not null,
    primary key (U_ID, P_ID)
);
-- Add comments to the table 
comment on table USER_POST_RELATION  is '用户岗位关系表';
-- Add comments to the columns 
comment on column USER_POST_RELATION.U_ID  is '用户ID';
comment on column USER_POST_RELATION.P_ID  is '岗位ID';

-- Create table
create table USER_GROUP
(
    G_ID VARCHAR2 (32) not null,
    G_NAME VARCHAR2 (50),
    G_TIME VARCHAR2 (20) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
    primary key (G_ID)
);
-- Add comments to the table 
comment on table USER_DETAIL  is '用户组信息表';
-- Add comments to the columns 
comment on column USER_DETAIL.G_ID  is '用户组信息ID';
comment on column USER_DETAIL.G_NAME  is '用户组名称';
comment on column USER_DETAIL.G_TIME  is '用户组创建时间';

-- Create table
create table USER_GROUP_RELATION
(
    G_ID VARCHAR2 (32) not null,
    U_ID VARCHAR2 (32) not null,
    primary key (U_ID, G_ID)
);
-- Add comments to the table 
comment on table USER_GROUP_RELATION  is '用户组-用户关系表';
-- Add comments to the columns 
comment on column USER_GROUP_RELATION.G_ID  is '用户组ID';
comment on column USER_GROUP_RELATION.U_ID  is '用户ID';

-- Create table
create table GROUP_ROLE_RELATION
(
    G_ID VARCHAR2 (32) not null,
    R_ID VARCHAR2 (32) not null,
    primary key (G_ID, R_ID)
);
-- Add comments to the table 
comment on table GROUP_ROLE_RELATION  is '角色-用户组关系表';
-- Add comments to the columns 
comment on column GROUP_ROLE_RELATION.G_ID  is '用户组ID';
comment on column GROUP_ROLE_RELATION.R_ID  is '角色ID';

-- Create table
create table GROUP_PERMISSION_RELATION
(
    G_ID VARCHAR2 (32) not null,
    PERMS VARCHAR2 (20),
    primary key (R_ID, PERMS)
);
-- Add comments to the table 
comment on table ROLE_USER_GROUP_RELATION  is '用户组-权限关系表';
-- Add comments to the columns 
comment on column ROLE_USER_GROUP_RELATION.G_ID  is '角色ID';
comment on column ROLE_USER_GROUP_RELATION.PERMS  is '权限标记(等同MENU_OPERATION_RELATION.PERMS)';

/* 权限附属表1：组织基础信息表、组织信息详情表、职务代码表、用户-组织关系表、组织-职务关系表（岗位）、用户-岗位关系表*/

-- Create table
create table ORG_INFO
(
    ORG_ID VARCHAR2 (32) default sys_guid(),
    ORG_CODE VARCHAR2 (20) not null,
    ORG_NAME VARCHAR2 (60),
    ORG_ABB VARCHAR2 (20),
    ORG_ABB VARCHAR2 (20),
    ORG_HIGHER VARCHAR2 (32),
    ORG_STATUS VARCHAR2 (1),
    primary key (ORG_ID)
);
-- Add comments to the table 
comment on table Org_Info  is '组织基础信息表';
-- Add comments to the columns 
comment on column Org_Info.ORG_id  is '组织ID';
comment on column Org_Info.ORG_CODE  is '组织代码';
comment on column Org_Info.ORG_NAME  is '组织名称';
comment on column Org_Info.ORG_ABB  is '组织简称';
comment on column Org_Info.ORG_HIGHER  is '上级组织';

-- Create table
create table ORG_DETAIL
(
    ORG_ID VARCHAR2 (32) not null,
    ORG_TYPE VARCHAR2 (20),
    ORG_ADMIN VARCHAR2 (20),
    ORG_TEL VARCHAR2 (50),
    ORG_EMAIL VARCHAR2 (80),
    ORG_ADDR VARCHAR2 (2000),
    ORG_POSTCODE VARCHAR2 (20),
    ORG_FAX VARCHAR2 (20),
    ORG_ZONE VARCHAR2 (10),
    ORG_ORDER VARCHAR2 (2),
    primary key (ORG_ID)
);
-- Add comments to the table 
comment on table ORG_DETAIL  is '组织信息详情表';
-- Add comments to the columns 
comment on column ORG_DETAIL.ORG_ID  is '组织ID';
comment on column ORG_DETAIL.ORG_TYPE  is '组织类型';
comment on column ORG_DETAIL.ORG_ADMIN  is '组织责人ID';
comment on column ORG_DETAIL.ORG_TEL  is '组织电话';
comment on column ORG_DETAIL.ORG_EMAIL  is '组织邮箱地址';
comment on column ORG_DETAIL.ORG_ADDR  is '组织地址';
comment on column ORG_DETAIL.ORG_ZONE  is '区号';
comment on column ORG_DETAIL.ORG_POSTCODE  is '邮编';
comment on column ORG_DETAIL.ORG_FAX  is '传真';
comment on column ORG_DETAIL.ORG_ORDER  is '组织顺序';

-- Create table
create table DUTY_INFO
(
    D_ID VARCHAR2 (32) default sys_guid(),
    D_CODE VARCHAR2 (50) not null,
    D_NAME VARCHAR2 (50) not null,
    D_DESC VARCHAR2 (1000),
    primary key (DUTY_id)
);
-- Add comments to the table 
comment on table DUTY_INFO  is '职务信息表';
-- Add comments to the columns 
comment on column DUTY_INFO.D_ID  is '职务ID';
comment on column DUTY_INFO.D_CODE  is '职务编码';
comment on column DUTY_INFO.D_NAME  is '职务名称';
comment on column DUTY_INFO.D_DESC  is '职务说明';

-- Create table
create table POST_INFO
(
    P_ID VARCHAR2 (32) default sys_guid(),
    ORG_ID VARCHAR2 (32) not null,
    D_ID VARCHAR2 (50) not null,
    P_DESC VARCHAR2 (1000),
    primary key (POST_id)
);
-- Add comments to the table 
comment on table POST_INFO  is '岗位信息表';
-- Add comments to the columns 
comment on column POST_INFO.P_ID  is '岗位信息ID';
comment on column POST_INFO.ORG_ID  is '组织ID';
comment on column POST_INFO.D_ID  is '职务ID';
comment on column POST_INFO.P_DESC  is '岗位说明';
