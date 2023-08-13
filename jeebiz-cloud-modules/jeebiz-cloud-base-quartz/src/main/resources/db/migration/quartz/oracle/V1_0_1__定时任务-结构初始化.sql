-- ----------------------------
-- Create table 
-- ----------------------------
create table t_DATA_QUARTZ_JOBS
(
    JOB_ID VARCHAR2 (32) default sys_guid() not null,
    BIZ_ID VARCHAR2 (32) not null,
    JOB_GROUP VARCHAR2 (255) not null,
    JOB_NAME VARCHAR2 (255) not null,
    JOB_INTRO VARCHAR2 (2000),
    JOB_CRON VARCHAR2 (255),
    JOB_CLASS VARCHAR2 (255),
    JOB_START VARCHAR2 (34),
    JOB_DELAY VARCHAR2 (32),
    JOB_PERIOD VARCHAR2 (32),
    JOB_STATUS CHAR(1) default '0' not null,
    TIME24 VARCHAR2 (34) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
    CONSTRAINT QUARTZ_JOBS_PK PRIMARY KEY (JOB_ID)
);
-- Add comments to the table 
comment on table t_DATA_QUARTZ_JOBS is '定时任务信息表';
-- Add comments to the columns 
comment on column t_DATA_QUARTZ_JOBS.JOB_ID is '任务记录ID编号';
comment on column t_DATA_QUARTZ_JOBS.BIZ_ID  is '业务记录ID编号';
comment on column t_DATA_QUARTZ_JOBS.JOB_GROUP  is '任务分组';
comment on column t_DATA_QUARTZ_JOBS.JOB_NAME  is '任务名称';
comment on column t_DATA_QUARTZ_JOBS.JOB_INTRO  is '任务描述';
comment on column t_DATA_QUARTZ_JOBS.JOB_CRON  is '任务cron表达式';
comment on column t_DATA_QUARTZ_JOBS.JOB_CLASS  is '任务执行时调用哪个类的方法 包名+类名';
comment on column t_DATA_QUARTZ_JOBS.JOB_START  is '任务开始时间';
comment on column t_DATA_QUARTZ_JOBS.JOB_DELAY  is '任务延时（单位：毫秒）';
comment on column t_DATA_QUARTZ_JOBS.JOB_PERIOD  is '任务周期';
comment on column t_DATA_QUARTZ_JOBS.JOB_STATUS  is '任务执行状态（0:待执行|1:运行中|2:暂停中|3:被锁定|4:已完成|5:失败|6:删除）';
comment on column t_DATA_QUARTZ_JOBS.TIME24  is '任务创建时间';


