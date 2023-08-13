-- Create table
create table t_user_face_repository
(
    repo_id VARCHAR2 (32) default sys_guid() not null,
    repo_name VARCHAR2 (50) not null,
    repo_status VARCHAR2 (2) default 1,
    create_time VARCHAR2 (32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),

    CONSTRAINT PK_USER_FACE_REPO_UID PRIMARY KEY (repo_id)
);
-- Add comments to the table
comment on table t_user_face_repository  is '用户-人脸识别数据分组表';
-- Add comments to the columns
comment on column t_user_face_repository.repo_id  is '分组ID';
comment on column t_user_face_repository.repo_name  is '分组名称';
comment on column t_user_face_repository.repo_status  is '分组状态（0:禁用|1:可用）';
comment on column t_user_face_repository.create_time  is '初始化时间';

-- Create table
create table t_user_face
(
    user_id VARCHAR2 (32) not null,
    repo_id VARCHAR2 (32) not null,
    face_id VARCHAR2 (32) default sys_guid() not null,
    face_base64 CLOB not null,
    face_type VARCHAR2 (20) default 'LIVE' not null,
    face_token VARCHAR2 (500),
    create_time VARCHAR2 (32) default to_char(sysdate ,'yyyy-mm-dd hh24:mi:ss'),
    CONSTRAINT PK_USER_FACE_UID PRIMARY KEY (U_FID)
);
-- Add comments to the table
comment on table t_user_face  is '用户-人脸识别数据表';
-- Add comments to the columns
comment on column t_user_face.user_id  is '用户ID';
comment on column t_user_face.repo_id  is '人脸识别数据分组ID';
comment on column t_user_face.face_id  is '人脸识别数据ID';
comment on column t_user_face.face_base64  is '人脸识别图片base64编码后的图片数据（图片的base64编码不包含图片头的，如data:image/jpg;base64,）';
comment on column t_user_face.face_type  is '人脸的类型：(LIVE:表示生活照;通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等|IDCARD:表示身份证芯片照;二代身份证内置芯片中的人像照片|WATERMARK:表示带水印证件照;一般为带水印的小图，如公安网小图|CERT:表示证件照片;如拍摄的身份证、工卡、护照、学生证等证件图片); 默认LIVE';
comment on column t_user_face.face_token  is '人脸图片的唯一标识';
comment on column t_user_face.create_time  is '人脸图片采集时间';
