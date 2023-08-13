/* 数据权限表：数据权限对象表、数据权限组信息表、数据权限信息表、数据权限项信息表、数据权限-数据权限组关系表、数据权限角色关系表、
	数据权限组角色关系表、数据权限用户关系表、数据权限组用户关系表*/

-- Create table
create table t_data_perms_table
(
    T_ID VARCHAR2 (32) default sys_guid() NOT NULL,
    T_NAME VARCHAR2 (60),
    T_TABLE VARCHAR2 (100),
    T_STATUS NUMBER (1) default 1 NOT NULL,
    T_ORDER NUMBER (3) default 1 NOT NULL,
    CONSTRAINT DATA_PERMS_TABLE_PK PRIMARY KEY (T_ID)
);
-- Add comments to the table 
comment on table t_data_perms_table  is '数据权限对象表';
-- Add comments to the columns 
comment on column t_data_perms_table.T_ID  is '数据权限对象ID';
comment on column t_data_perms_table.T_NAME  is '数据权限对象名称';
comment on column t_data_perms_table.T_TABLE  is '受限表名称（实体表名称）';
comment on column t_data_perms_table.T_STATUS  is '数据权限对象可用状态:（0:不可用|1：可用）';
comment on column t_data_perms_table.T_ORDER  is '数据权限对象排序';

-- Create table
create table t_data_perms_table_columns
(
    T_ID VARCHAR2 (32) NOT NULL,
    C_ID VARCHAR2 (32) default sys_guid() NOT NULL,
    C_NAME VARCHAR2 (60),
    C_LABEL VARCHAR2 (100),
    C_SQL CLOB,
    C_STATUS NUMBER (1) default 1 NOT NULL,
    C_ORDER NUMBER (3) default 1 NOT NULL,
    CONSTRAINT DATA_PERMS_TABLE_COLUMNS_PK PRIMARY KEY (C_ID)
);
-- Add comments to the table 
comment on table t_data_perms_table_columns  is '数据权限对象字段表';
-- Add comments to the columns 
comment on column t_data_perms_table_columns.T_ID  is '数据权限对象ID';
comment on column t_data_perms_table_columns.C_ID  is '数据权限对象字段ID';
comment on column t_data_perms_table_columns.C_NAME  is '受数据权限对象字段限制的业务数据表字段名称（实体表字段列名称）';
comment on column t_data_perms_table_columns.C_LABEL  is '数据权限对象字段标签（页面显示名称）';
comment on column t_data_perms_table_columns.C_SQL  is '受限数据项数据查询SQL';
comment on column t_data_perms_table_columns.C_STATUS  is '受限数据项字段可用状态:（0:不可用|1：可用）';
comment on column t_data_perms_table_columns.C_ORDER  is '受限数据项字段排序';

-- Create table
create table t_DATA_PERMS_GROUP
(
    G_ID VARCHAR2 (32) default sys_guid() NOT NULL,
    G_NAME VARCHAR2 (60),
    G_INTRO CLOB,
    G_STATUS NUMBER (1) default 1 NOT NULL,
    G_ORDER NUMBER (3) default 1 NOT NULL,
    G_TIME24 VARCHAR2 (32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
    CONSTRAINT DATA_PERMS_GROUP_PK PRIMARY KEY (G_ID)
);
-- Add comments to the table 
comment on table t_DATA_PERMS_GROUP  is '数据权限组信息表';
-- Add comments to the columns 
comment on column t_DATA_PERMS_GROUP.G_ID  is '数据权限组ID';
comment on column t_DATA_PERMS_GROUP.G_NAME  is '数据权限组名称';
comment on column t_DATA_PERMS_GROUP.G_INTRO  is '数据权限组简介';
comment on column t_DATA_PERMS_GROUP.G_STATUS  is '数据权限组可用状态:（0:不可用|1：可用）';
comment on column t_DATA_PERMS_GROUP.G_ORDER  is '数据权限组排序';
comment on column t_DATA_PERMS_GROUP.G_TIME24  is '数据权限组创建时间';

-- Create table
create table t_DATA_PERMS
(
    P_ID VARCHAR2 (32) default sys_guid() NOT NULL,
    P_NAME VARCHAR2 (60) NOT NULL,
    P_TYPE NUMBER (1) default 1 NOT NULL,
    P_INTRO CLOB,
    P_TABLE VARCHAR2 (100) NOT NULL,
    P_RELATION VARCHAR2 (20) NOT NULL,
    P_STATUS NUMBER (1) default 1 NOT NULL,
    P_ORDER NUMBER (4) default 1 NOT NULL,
    CONSTRAINT PK_DATA_PERMS_RULE PRIMARY KEY (P_ID)
);
-- Add comments to the table 
comment on table t_DATA_PERMS  is '数据权限表';
-- Add comments to the columns 
comment on column t_DATA_PERMS.P_ID  is '数据权限ID';
comment on column t_DATA_PERMS.P_NAME  is '数据权限名称';
comment on column t_DATA_PERMS.P_TYPE  is '数据权限类型(1:原生|2:继承|3:复制)';
comment on column t_DATA_PERMS.P_INTRO  is '数据权限简介';
comment on column t_DATA_PERMS.P_TABLE  is '受限表名称（实体表名称）';
comment on column t_DATA_PERMS.P_RELATION  is '数据权限项关系 and/or';
comment on column t_DATA_PERMS.P_STATUS  is '数据权限可用状态:（0:不可用|1：可用）';
comment on column t_DATA_PERMS.P_ORDER  is '数据权限排序';

-- Create table
create table t_DATA_PERMS_ITEM
(
    P_ID VARCHAR2 (32) NOT NULL,
    I_ID VARCHAR2 (32) default sys_guid() NOT NULL,
    I_COLUMN VARCHAR2 (100) NOT NULL,
    I_NAME VARCHAR2 (100) NOT NULL,
    I_CONDITION VARCHAR2 (50) NOT NULL,
    I_PINYIN VARCHAR2 (100),
    I_PERMS CLOB,
    I_STATUS NUMBER (1) default 1 NOT NULL,
    I_ORDER NUMBER (4) default 1 NOT NULL,
    CONSTRAINT DATA_PERMS_ITEM_PK PRIMARY KEY (I_ID)
);
-- Add comments to the table 
comment on table t_DATA_PERMS_ITEM  is '数据权限项信息表';
-- Add comments to the columns 
comment on column t_DATA_PERMS_ITEM.P_ID  is '数据权限ID';
comment on column t_DATA_PERMS_ITEM.I_ID  is '数据权限项ID';
comment on column t_DATA_PERMS_ITEM.I_COLUMN  is '受限表字段名称（实体表字段列名称）';
comment on column t_DATA_PERMS_ITEM.I_NAME  is '数据权限项名称（实体表字段中文名称）';
comment on column t_DATA_PERMS_ITEM.I_PINYIN  is '受限表字段中文拼音';
comment on column t_DATA_PERMS_ITEM.I_CONDITION  is '受限表字段与限制条件之间的关联条件';
comment on column t_DATA_PERMS_ITEM.I_PERMS  is '受限表字段限制条件';
comment on column t_DATA_PERMS_ITEM.I_STATUS  is '数据权限项可用状态:（0:不可用|1：可用）';
comment on column t_DATA_PERMS_ITEM.I_ORDER  is '数据权限项排序';

-- Create table
create table t_DATA_PERMS_GROUP_REL
(
    G_ID VARCHAR2 (32) NOT NULL,
    P_ID VARCHAR2 (32) NOT NULL,
    CONSTRAINT DATA_PERMS_GROUP_UK UNIQUE (G_ID, P_ID)
);
-- Add comments to the table 
comment on table t_DATA_PERMS_GROUP_REL  is '数据权限-数据权限组关系表';
-- Add comments to the columns 
comment on column t_DATA_PERMS_GROUP_REL.G_ID  is '数据权限组ID';
comment on column t_DATA_PERMS_GROUP_REL.P_ID  is '数据权限ID';

-- Create table
create table t_DATA_PERMS_ROLE_REL
(
    APP_ID VARCHAR2 (32) NOT NULL,
    P_ID VARCHAR2 (32) NOT NULL,
    R_ID VARCHAR2 (32) NOT NULL,
    CONSTRAINT DATA_PERMS_ROLE_UK UNIQUE (P_ID, R_ID)
);
-- Add comments to the table 
comment on table t_DATA_PERMS_ROLE_REL  is '数据权限角色关系表';
-- Add comments to the columns 
comment on column t_DATA_PERMS_ROLE_REL.APP_ID  is '应用ID';
comment on column t_DATA_PERMS_ROLE_REL.R_ID  is '角色ID';
comment on column t_DATA_PERMS_ROLE_REL.P_ID  is '数据权限ID';

-- Create table
create table t_DATA_PERMS_USER_REL
(
    APP_ID VARCHAR2 (32) NOT NULL,
    U_ID VARCHAR2 (32) NOT NULL,
    P_ID VARCHAR2 (32) NOT NULL,
    CONSTRAINT DATA_PERMS_USER_UK UNIQUE (P_ID, U_ID)
);
-- Add comments to the table 
comment on table t_DATA_PERMS_USER_REL  is '数据权限用户关系表';
-- Add comments to the columns 
comment on column t_DATA_PERMS_USER_REL.APP_ID  is '应用ID';
comment on column t_DATA_PERMS_USER_REL.U_ID  is '用户ID';
comment on column t_DATA_PERMS_USER_REL.P_ID  is '数据权限ID';

-- Create table
create table t_DATA_PERMS_GROUP_ROLE_REL
(
    APP_ID VARCHAR2 (32) NOT NULL,
    R_ID VARCHAR2 (32) NOT NULL,
    G_ID VARCHAR2 (32) NOT NULL,
    CONSTRAINT DATA_PERMS_GROUP_ROLE_UK UNIQUE (G_ID, R_ID)
);
-- Add comments to the table 
comment on table t_DATA_PERMS_GROUP_ROLE_REL  is '数据权限组角色关系表';
-- Add comments to the columns 
comment on column t_DATA_PERMS_GROUP_ROLE_REL.APP_ID  is '应用ID';
comment on column t_DATA_PERMS_GROUP_ROLE_REL.R_ID  is '角色ID';
comment on column t_DATA_PERMS_GROUP_ROLE_REL.G_ID  is '数据权限组ID';

-- Create table
create table t_DATA_PERMS_GROUP_USER_REL
(
    APP_ID VARCHAR2 (32) NOT NULL,
    G_ID VARCHAR2 (32) NOT NULL,
    U_ID VARCHAR2 (32) NOT NULL,
    CONSTRAINT DATA_PERMS_GROUP_USER_UK UNIQUE (G_ID, U_ID)
);
-- Add comments to the table 
comment on table t_DATA_PERMS_GROUP_USER_REL  is '数据权限组用户关系表';
-- Add comments to the columns
comment on column t_DATA_PERMS_GROUP_USER_REL.APP_ID  is '应用ID';
comment on column t_DATA_PERMS_GROUP_USER_REL.U_ID  is '用户ID';
comment on column t_DATA_PERMS_GROUP_USER_REL.G_ID  is '数据权限组ID';

-- Create table
create table t_DATA_PERMS_SPCL
(
    T_ID VARCHAR2 (32) default sys_guid() NOT NULL,
    T_CODE VARCHAR2 (60),
    T_NAME VARCHAR2 (60),
    T_STATUS NUMBER (1) default 1 NOT NULL,
    T_ORDER NUMBER (3) default 1 NOT NULL,
    CONSTRAINT DATA_PERMS_SPCL_PK PRIMARY KEY (T_ID)
);
-- Add comments to the table 
comment on table t_DATA_PERMS_SPCL  is '数据权限专项表';
-- Add comments to the columns 
comment on column t_DATA_PERMS_SPCL.T_ID  is '数据权限专项ID';
comment on column t_DATA_PERMS_SPCL.T_CODE  is '数据权限专项编码（对应程序中的）';
comment on column t_DATA_PERMS_SPCL.T_NAME  is '数据权限专项名称';
comment on column t_DATA_PERMS_SPCL.T_STATUS  is '数据权限专项可用状态:（0:不可用|1：可用）';
comment on column t_DATA_PERMS_SPCL.T_ORDER  is '数据权限专项排序';

-- Create table
create table t_DATA_PERMS_SPCL_COLUMNS
(
    T_ID VARCHAR2 (32) NOT NULL,
    C_ID VARCHAR2 (32) default sys_guid() NOT NULL,
    C_TABLE VARCHAR2 (100),
    C_NAME VARCHAR2 (60),
    C_LABEL VARCHAR2 (100),
    C_SQL VARCHAR2 (2000),
    C_STATUS NUMBER (1) default 1 NOT NULL,
    C_ORDER NUMBER (3) default 1 NOT NULL,
    CONSTRAINT DATA_PERMS_SPCL_COLUMNS_PK PRIMARY KEY (C_ID)
);
-- Add comments to the table 
comment on table t_DATA_PERMS_SPCL_COLUMNS  is '数据权限专项字段表';
-- Add comments to the columns 
comment on column t_DATA_PERMS_SPCL_COLUMNS.T_ID  is '数据权限专项ID';
comment on column t_DATA_PERMS_SPCL_COLUMNS.C_ID  is '数据权限专项字段ID';
comment on column t_DATA_PERMS_SPCL_COLUMNS.C_TABLE  is '受限表名称（实体表名称）';
comment on column t_DATA_PERMS_SPCL_COLUMNS.C_NAME  is '受数据权限专项字段限制的业务数据表字段名称（实体表字段列名称）';
comment on column t_DATA_PERMS_SPCL_COLUMNS.C_LABEL  is '数据权限专项字段标签（页面显示名称）';
comment on column t_DATA_PERMS_SPCL_COLUMNS.C_SQL  is '受限数据项数据查询SQL';
comment on column t_DATA_PERMS_SPCL_COLUMNS.C_STATUS  is '受限数据项字段可用状态:（0:不可用|1：可用）';
comment on column t_DATA_PERMS_SPCL_COLUMNS.C_ORDER  is '受限数据项字段排序';

-- Create table
create table t_DATA_PERMS_SPCL_ROLE_REL
(
    APP_ID VARCHAR2 (32) NOT NULL,
    R_ID VARCHAR2 (32) NOT NULL,
    T_ID VARCHAR2 (32) NOT NULL,
    CONSTRAINT DATA_PERMS_SPCL_ROLE_UK UNIQUE (T_ID, R_ID)
);
-- Add comments to the table 
comment on table t_DATA_PERMS_SPCL_ROLE_REL  is '数据权限专项角色关系表';
-- Add comments to the columns 
comment on column t_DATA_PERMS_SPCL_ROLE_REL.APP_ID  is '应用ID';
comment on column t_DATA_PERMS_SPCL_ROLE_REL.R_ID  is '角色ID';
comment on column t_DATA_PERMS_SPCL_ROLE_REL.T_ID  is '数据权限专项ID';

-- Create table
create table t_DATA_PERMS_SPCL_USER_REL
(
    APP_ID VARCHAR2 (32) NOT NULL,
    T_ID VARCHAR2 (32) NOT NULL,
    U_ID VARCHAR2 (32) NOT NULL,
    CONSTRAINT DATA_PERMS_SPCL_USER_UK UNIQUE (T_ID, U_ID)
);
-- Add comments to the table 
comment on table t_DATA_PERMS_SPCL_USER_REL  is '数据权限专项用户关系表';
-- Add comments to the columns
comment on column t_DATA_PERMS_SPCL_USER_REL.APP_ID  is '应用ID';
comment on column t_DATA_PERMS_SPCL_USER_REL.U_ID  is '用户ID';
comment on column t_DATA_PERMS_SPCL_USER_REL.T_ID  is '数据权限专项ID';


