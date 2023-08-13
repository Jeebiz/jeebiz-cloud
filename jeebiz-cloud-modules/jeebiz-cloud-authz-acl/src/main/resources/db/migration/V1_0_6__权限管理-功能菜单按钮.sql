/**************【系统管理模块】功能菜单**************/

/*系统管理*/

delete
from zftal_xtgl_jsgnmkdmb
where gnmkdm = 'N01';
insert into zftal_xtgl_jsgnmkdmb(gnmkdm, gnmkmc, fjgndm, dyym, xssx)
values ('N01', '系统管理', 'N', '', '1');

/*系统设置*/
delete
from zftal_xtgl_jsgnmkdmb
where gnmkdm = 'N0101';
insert into zftal_xtgl_jsgnmkdmb(gnmkdm, gnmkmc, fjgndm, dyym, xssx)
values ('N0101', '系统设置', 'N01', '', '1');

/*参数设置*/

delete
from zftal_xtgl_jsgnmkdmb
where gnmkdm = 'N010101';
insert into zftal_xtgl_jsgnmkdmb(gnmkdm, gnmkmc, fjgndm, dyym, xssx)
values ('N010101', '参数设置', 'N0101', '/xtgl/xtsz/cxCssz', '1');

delete
from zftal_xtgl_gnmkczb
where gnmkdm = 'N010101';
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010101_bc', 'N010101', 'bc', 'cssz:bc', '1');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010101_#', 'N010101', '#', 'cssz:cx', '');

delete
from zftal_xtgl_jsgnmkczb
where gnczid like 'N010101%'
  and jsdm = 'admin';
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010101_#');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010101_bc');

/*通知公告*/

delete
from zftal_xtgl_jsgnmkdmb
where gnmkdm = 'N010113';
insert into zftal_xtgl_jsgnmkdmb(gnmkdm, gnmkmc, fjgndm, dyym, xssx)
values ('N010113', '通知公告', 'N0101', '/xtgl/xwgl/cxXw.zf', '8');

delete
from zftal_xtgl_gnmkczb
where gnmkdm = 'N010113';
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010113_#', 'N010113', '#', 'xwgl:cx', '');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010113_zj', 'N010113', 'zj', 'xwgl:zj', '1');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010113_xg', 'N010113', 'xg', 'xwgl:xg', '2');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010113_sc', 'N010113', 'sc', 'xwgl:sc', '3');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010113_ck', 'N010113', 'ck', 'xwgl:ck', '4');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010113_fb', 'N010113', 'fb', 'xwgl:fb', '5');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010113_qxfb', 'N010113', 'qxfb', 'xwgl:qxfb', '6');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010113_zd', 'N010113', 'zd', 'xwgl:zd', '7');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010113_qxzd', 'N010113', 'qxzd', 'xwgl:qxzd', '8');

delete
from zftal_xtgl_jsgnmkczb
where gnczid like 'N010113%'
  and jsdm = 'admin';
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010113_#');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010113_ck');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010113_fb');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010113_qxfb');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010113_qxzd');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010113_sc');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010113_xg');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010113_zd');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010113_zj');

/*日志管理*/
delete
from zftal_xtgl_jsgnmkdmb
where gnmkdm = 'N010114';
insert into zftal_xtgl_jsgnmkdmb(gnmkdm, gnmkmc, fjgndm, dyym, xssx)
values ('N010114', '日志管理', 'N0101', '/xtgl/rzgl/cxRz.zf', '9');

delete
from zftal_xtgl_gnmkczb
where gnmkdm = 'N010114';
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010114_#', 'N010114', '#', 'rzgl:cx', '');

delete
from zftal_xtgl_jsgnmkczb
where gnczid like 'N010114%'
  and jsdm = 'admin';
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010114_#');

/*缓存管理*/
delete
from zftal_xtgl_jsgnmkdmb
where gnmkdm = 'N010199';
insert into zftal_xtgl_jsgnmkdmb(gnmkdm, gnmkmc, fjgndm, dyym, xssx)
values ('N010199', '缓存管理', 'N0101', '/system/cache/cacheManagement.zf', '99');

delete
from zftal_xtgl_gnmkczb
where gnmkdm = 'N010199';
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010199_#', 'N010199', '#', '', '');

delete
from zftal_xtgl_jsgnmkczb
where gnczid like 'N010199%'
  and jsdm = 'admin';
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010199_#');

/*权限管理*/
delete
from zftal_xtgl_jsgnmkdmb
where gnmkdm = 'N0102';
insert into zftal_xtgl_jsgnmkdmb(gnmkdm, gnmkmc, fjgndm, dyym, xssx)
values ('N0102', '权限管理', 'N01', '', '2');

/*角色管理*/
delete
from zftal_xtgl_jsgnmkdmb
where gnmkdm = 'N010201';
insert into zftal_xtgl_jsgnmkdmb(gnmkdm, gnmkmc, fjgndm, dyym, xssx)
values ('N010201', '角色管理', 'N0102', '/xtgl/jsgl/cxJsxx.zf', '1');

delete
from zftal_xtgl_gnmkczb
where gnmkdm = 'N010201';
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010201_zj', 'N010201', 'zj', 'jsgl:zj', '1');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010201_xg', 'N010201', 'xg', 'jsgl:xg', '2');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010201_sc', 'N010201', 'sc', 'jsgl:sc', '3');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010201_ck', 'N010201', 'ck', 'jsgl:ck', '4');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010201_gnsq', 'N010201', 'gnsq', 'jsgl:gnsq', '5');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010201_sjsq', 'N010201', 'sjsq', 'jsgl:sjsq', '6');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010201_fpyh', 'N010201', 'fpyh', 'jsgl:fpyh', '7');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010201_#', 'N010201', '#', 'jsgl:cx', '');

delete
from zftal_xtgl_jsgnmkczb
where gnczid like 'N010201%'
  and jsdm = 'admin';
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010201_#');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010201_ck');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010201_fpyh');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010201_gnsq');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010201_sc');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010201_sjsq');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010201_xg');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010201_zj');

/*用户管理*/
delete
from zftal_xtgl_jsgnmkdmb
where gnmkdm = 'N010202';
insert into zftal_xtgl_jsgnmkdmb(gnmkdm, gnmkmc, fjgndm, dyym, xssx)
values ('N010202', '用户管理', 'N0102', '/xtgl/yhgl/cxYhxx.zf', '2');

delete
from zftal_xtgl_gnmkczb
where gnmkdm = 'N010202';
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010202_zj', 'N010202', 'zj', 'yhgl:zj', '1');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010202_xg', 'N010202', 'xg', 'yhgl:xg', '2');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010202_sc', 'N010202', 'sc', 'yhgl:sc', '3');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010202_ck', 'N010202', 'ck', 'yhgl:ck', '4');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010202_mmcsh', 'N010202', 'mmcsh', 'yhgl:mmcsh', '5');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010202_qy', 'N010202', 'qy', 'yhgl:qy', '6');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010202_ty', 'N010202', 'ty', 'yhgl:ty', '7');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010202_#', 'N010202', '#', 'yhgl:cx', '');

delete
from zftal_xtgl_jsgnmkczb
where gnczid like 'N010202%'
  and jsdm = 'admin';
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010202_#');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010202_ck');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010202_mmcsh');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010202_qy');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010202_sc');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010202_ty');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010202_xg');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010202_zj');

/*基础数据*/
delete
from zftal_xtgl_jsgnmkdmb
where gnmkdm = 'N0105';
insert into zftal_xtgl_jsgnmkdmb(gnmkdm, gnmkmc, fjgndm, dyym, xssx)
values ('N0105', '基础数据', 'N01', '', '5');

/*基础数据维护*/
delete
from zftal_xtgl_jsgnmkdmb
where gnmkdm = 'N010501';
insert into zftal_xtgl_jsgnmkdmb(gnmkdm, gnmkmc, fjgndm, dyym, xssx)
values ('N010501', '基础数据维护', 'N0105', '/xtgl/jcsj/cxJcsj.zf', '1');

delete
from zftal_xtgl_gnmkczb
where gnmkdm = 'N010501';
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010501_#', 'N010501', '#', 'jcsj:cx', '');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010501_zj', 'N010501', 'zj', 'jcsj:zj', '1');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010501_xg', 'N010501', 'xg', 'jcsj:xg', '2');
insert into zftal_xtgl_gnmkczb(gnczid, gnmkdm, czdm, qxdm, xssx)
values ('N010501_sc', 'N010501', 'sc', 'jcsj:sc', '3');

delete
from zftal_xtgl_jsgnmkczb
where gnczid like 'N010501%'
  and jsdm = 'admin';
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010501_#');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010501_sc');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010501_xg');
insert into zftal_xtgl_jsgnmkczb(jsdm, gnczid)
values ('admin', 'N010501_zj');

commit;


/*操作代码*/

insert into zftal_xtgl_czdmb (CZDM, CZMC, ANYS, YWMC)
values ('xzzjs', '新增子角色', 'icon-user', 'Add New Sub-Roles');


commit;

