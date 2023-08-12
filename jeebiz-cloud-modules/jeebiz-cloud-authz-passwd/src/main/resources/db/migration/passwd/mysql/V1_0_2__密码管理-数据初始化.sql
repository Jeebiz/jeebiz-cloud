INSERT INTO t_PASSWD_STRATEGY (S_KEY, S_NAME, S_DESC, S_STATUS)
values ('oksms', '手机号码', '通过绑定手机号接收验证码的方式重置密码', '1');
INSERT INTO t_PASSWD_STRATEGY (S_KEY, S_NAME, S_DESC, S_STATUS)
values ('email', '电子邮箱', '通过绑定邮箱接收验证码的方式重置密码', '1');
INSERT INTO t_PASSWD_STRATEGY (S_KEY, S_NAME, S_DESC, S_STATUS)
values ('otp', '动态口令', '通过个人动态口令：One-time Password （otp）方式重置密码', '0');

COMMIT;