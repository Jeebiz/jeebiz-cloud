/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.login.strategy;

import java.util.Arrays;
import java.util.List;

public class Constants {

    /**
     * 教师异动
     */
    public static class TeacherReshuffle {

        /**
         * 教职工异动类别： 1	支教
         */
        public static final String JZGYDLBM_1 = "1";

        /**
         * 教职工异动类别： 2	交流
         */
        public static final String JZGYDLBM_2 = "2";

        /**
         * 教职工异动类别： 3	借用
         */
        public static final String JZGYDLBM_3 = "3";

        /**
         * 教职工异动类别： 4	借调
         */
        public static final String JZGYDLBM_4 = "4";

        /**
         * 教职工异动类别： 5	挂职
         */
        public static final String JZGYDLBM_5 = "5";

        /**
         * 教职工异动类别： 6	调动
         */
        public static final String JZGYDLBM_6 = "6";

        /**
         * 流程状态: 已提交
         */
        public static final String ZT_SUBMIT = "1";

        /**
         * 流程状态: 审核中
         */
        public static final String ZT_EXAMINE = "2";

        /**
         * 流程状态: 同意
         */
        public static final String ZT_AGREE = "3";

        /**
         * 流程状态: 驳回
         */
        public static final String ZT_REJECT = "4";

        /**
         * 流程状态: 已撤回
         */
        public static final String ZT_BACK = "5";

        /**
         * 流程节点处理状态（0：未提交）
         */
        public static final Integer AUDITFLAG_WTJ = 0;
        /**
         * 流程节点处理状态（1：已提交）
         */
        public static final Integer AUDITFLAG_YTJ = 1;
        /**
         * 流程节点处理状态（2：审核中）
         */
        public static final Integer AUDITFLAG_SHZ = 2;
        /**
         * 流程节点处理状态（3：已同意）
         */
        public static final Integer AUDITFLAG_YTY = 3;
        /**
         * 流程节点处理状态（4：已驳回）
         */
        public static final Integer AUDITFLAG_YBH = 4;
        /**
         * 流程节点处理状态（5：已撤回）
         */
        public static final Integer AUDITFLAG_YCH = 5;
        /**
         * 流程节点处理状态（6：已中止）
         */
        public static final Integer AUDITFLAG_YZZ = 6;


        /**
         * 流程步骤（0：发起人）
         */
        public static final Integer STEPINDEX_0 = 0;
        /**
         * 流程步骤（1：异动后机构审核）
         */
        public static final Integer STEPINDEX_1 = 1;
        /**
         * 流程步骤（2：异动前机构审核）
         */
        public static final Integer STEPINDEX_2 = 2;
        /**
         * 流程步骤（3：申请人撤回）
         */
        public static final Integer STEPINDEX_3 = 3;
        /**
         * 是否当前步骤（0：未开始步骤）
         */
        public static final Integer STEPTYPE_0 = 0;
        /**
         * 是否当前步骤（1：当前步骤）
         */
        public static final Integer STEPTYPE_1 = 1;
        /**
         * 是否当前步骤（2：历史步骤）
         */
        public static final Integer STEPTYPE_2 = 2;
        /**
         * 是否当前步骤（2：历史步骤）
         */
        public static final String AGREE_STRING = "同意";
    }

    /**
     * 基础代码
     */
    public static class BaseCode {

        /**
         * 基础代码：是否：1：是
         */
        public static final Integer TRUE = 1;

        /**
         * 基础代码：是否：0：否
         */
        public static final Integer FALSE = 0;

        /**
         * 基础代码：是否：0：否
         */
        public static final Integer SUCESS = 0;


        /**
         * 基础代码：是否：0：否
         */
        public static final Integer FAIL = 1;

    }

    /**
     * 基础代码
     */
    public static class OrgTreeConfig {

        /**
         * 树ID（区分不同的数据） 1：校区管理树
         */
        public static final String TREEID_1 = "1";

        /**
         * 树ID（区分不同的数据） 2：学生/家长管理树
         */
        public static final String TREEID_2 = "2";

        /**
         * 树ID（区分不同的数据） 3：教职工管理树
         */
        public static final String TREEID_3 = "3";

        /**
         * 树ID（区分不同的数据） 4：
         */
        public static final String TREEID_4 = "4";

        /**
         * 树ID（区分不同的数据） 5：
         */
        public static final String TREEID_5 = "5";

        /**
         * 树ID（区分不同的数据） 6：
         */
        public static final String TREEID_6 = "6";

        /**
         * 树ID（区分不同的数据） 7：
         */
        public static final String TREEID_7 = "7";

        /**
         * 树节点类型 —— 虚拟节点
         */
        public static final String FAKE_TREE = "1";

        /**
         * 树节点类型 —— 数据节点
         */
        public static final String DATA_TREE = "2";

    }

    public static class Jzgjbsjzlb {
        /**
         * 是否停用（1:启用）
         */
        public static final String NODETYPE_CLASS = "class";
        /**
         * 是否停用（1:停用）
         */
        public static final String NODETYPE_GRADE = "grade";
    }

    public static class Jzg {
        public static final String YES = "是";

        public static final String NO = "否";

        public static final List<String> types = Arrays.asList("sfstjjs", "jkzkm", "gwlbm", "sfxjjysggjs", "sfzbm", "bzlbm", "ryzt", "gwzym", "mzm", "sfzjlxm", "zzmmm", "xbm", "rjxd");
    }

    public static class Gwxx {
        /**
         * 是否停用（1:启用）
         */
        public static final Integer ZT_USING = 0;
        /**
         * 是否停用（1:停用）
         */
        public static final Integer ZT_STOPUSING = 1;
        /**
         * 是否停用（0:启用）
         */
        public static final String ZT_USING_NAME = "启用";
        /**
         * 是否停用（1:停用）
         */
        public static final String ZT_STOPUSING_NAME = "停用";
    }

    public static class Xqjbsjzlb {
        /**
         * 基础默认的校区码（唯一默认，主校区必定是01）
         */
        public static final String BASE_SCHOOL_MAINXQM = "01";
        /**
         * 是否主校区码（0：主校）
         */
        public static final Integer SFZXQM_YES = 1;
        /**
         * 是否主校区码（1：分校）
         */
        public static final Integer SFZXQM_NO = 0;
    }

    public static class CheckUserColumn {
        /**
         * 操作对象（student:学生）
         */
        public static final String CHOOSE_YES = "0";
        /**
         * 操作对象（teacher:教职工）
         */
        public static final String CHOOSE_NO = "1";
        /**
         * 操作对象（student:学生）
         */
        public static final String TYPE_STUDENT = "student";
        /**
         * 操作对象（teacher:教职工）
         */
        public static final String TYPE_TEACHER = "teacher";
        /**
         * 操作类型（parent:家长）
         */
        public static final String TYPE_PARENT = "parent";
    }

    public static class MoveUserLog {
        /**
         * 操作类型（0:信息修改）
         */
        public static final Integer MOVE_USER_LOG_ISNEW_NEW = 0;
        /**
         * 操作类型（1:离校）
         */
        public static final Integer MOVE_USER_LOG_ISNEW_OLD = 1;
        /**
         * 操作类型（0:信息修改）
         */
        public static final Integer MOVE_USER_LOG_TYPE_0 = 0;
        /**
         * 操作类型（1:离校）
         */
        public static final Integer MOVE_USER_LOG_TYPE_1 = 1;
        /**
         * 操作类型（2:入校）
         */
        public static final Integer MOVE_USER_LOG_TYPE_2 = 2;
        /**
         * 操作类型（3:部门移除）
         */
        public static final Integer MOVE_USER_LOG_TYPE_3 = 3;
        /**
         * 操作类型（4:部门新增）
         */
        public static final Integer MOVE_USER_LOG_TYPE_4 = 4;
        /**
         * 操作类型（5:班级移除）
         */
        public static final Integer MOVE_USER_LOG_TYPE_5 = 5;
        /**
         * 操作类型（6:班级新增）
         */
        public static final Integer MOVE_USER_LOG_TYPE_6 = 6;
        /**
         * 是否归档（0:待确认）
         */
        public static final Integer MOVE_USER_LOG_ZT_0 = 0;
        /**
         * 是否归档（1:待提交）
         */
        public static final Integer MOVE_USER_LOG_ZT_1 = 1;
        /**
         * 是否归档（2:已确认）
         */
        public static final Integer MOVE_USER_LOG_ZT_2 = 2;
        /**
         * 是否归档（3:已归档）
         */
        public static final Integer MOVE_USER_LOG_ZT_3 = 3;

        /**
         * 操作对象（student:学生）
         */
        public static final String MOVE_USER_LOG_USER_TYPE_STUDENT = "student";
        /**
         * 操作对象（student:学生）
         */
        public static final String MOVE_USER_LOG_USER_TYPE_CLASS = "class";
        /**
         * 操作对象（teacher:教师）
         */
        public static final String MOVE_USER_LOG_USER_TYPE_TEACHER = "teacher";
        /**
         * 操作类型（parent:家长）
         */
        public static final String MOVE_USER_LOG_USER_TYPE_PARENT = "parent";
        /**
         * 操作对象（deptUser:部门人员）
         */
        public static final String MOVE_USER_LOG_USER_TYPE_DEPTUSER = "deptUser";
    }


    public static final int ISDELETE_USEING = 0;
    public static final int ISDELETE_DELETE = 1;
    public static final int ZT_USEING = 0;
    public static final int ZT_NO_USE = 1;

    public static final String DEMO = "Demo模块";


    public static class Biz {

        public static final String DEMO_LIST = "xxx分页查询";
        public static final String DEMO_NEW = "新增xxx";
        public static final String DEMO_RENEW = "修改xxx";
        public static final String DEMO_DETAIL = "查询xxx";
        public static final String DEMO_DELETE = "删除xxx";

    }

    public static class Normal {
        public static final Integer IS_DELETE_0 = 0; //未删除
        public static final Integer IS_DELETE_1 = 1; //已删除

        public static final Integer IS_DEFAULT_NO = 0; //非默认
        public static final Integer IS_DEFAULT_YES = 1; //是默认

        public static final Integer GENDER_MAIL = 1;
        public static final Integer GENDER_FEMAIL = 2;

        public static final Integer IS_STATUS_NO = 0; //停用
        public static final Integer IS_STATUS_YES = 1; //启用

    }

    public static class Role {
        public static final Integer ROLETYPE_GLOBAL = 1; //全局
        public static final Integer ROLETYPE_SELF = 2; //内部

        public static final Integer BASE_ROLE_ADMIN = 1; //内置的超级管理员局端角色

        public static final Integer BASE_ROLE_SCHOOLADMIN = 2; //内置的组织机构管理员校端角色

    }

    public static class dept {
        /**
         * 机构等级（0虚拟）
         */
        public static final Integer DEPT_LEVEL_XN = 0;
        /**
         * 机构等级（1局端）
         */
        public static final Integer DEPT_LEVEL_JD = 1;
        /**
         * 机构等级（2校端）
         */
        public static final Integer DEPT_LEVEL_XD = 2;
        /**
         * 机构等级（3分校）
         */
        public static final Integer DEPT_LEVEL_FX = 3;

        /**
         * 部门展示时的分类 如学校类型（局机关）（局级）
         */
        public static final Integer SHOW_TYPE_JJG = 1;
        /**
         * 部门展示时的分类 如学校类型（直属机关）（部门级）
         */
        public static final Integer SHOW_TYPE_ZSJG = 2;
        /**
         * 部门展示时的分类 如学校类型（幼儿园）（校级）
         */
        public static final Integer SHOW_TYPE_YEY = 3;
        /**
         * 部门展示时的分类 如学校类型（小学）（校级）
         */
        public static final Integer SHOW_TYPE_XX = 4;
        /**
         * 部门展示时的分类 如学校类型（初级中学）（校级）
         */
        public static final Integer SHOW_TYPE_CJZX = 5;
        /**
         * 部门展示时的分类 如学校类型（九年一贯制）（校级）
         */
        public static final Integer SHOW_TYPE_JNYGZ = 6;
        /**
         * 部门展示时的分类 如学校类型（高级中学）（校级）
         */
        public static final Integer SHOW_TYPE_GJZX = 7;
        /**
         * 部门展示时的分类 如学校类型（其他）（部门级）
         */
        public static final Integer SHOW_TYPE_QT = 8;

        /**
         * 部门业务分类 (1用户组)
         */
        public static final Integer DEPT_TYPE_YHZ = 1;
        /**
         * 部门业务分类 (2教职工)
         */
        public static final Integer DEPT_TYPE_JZG = 2;
        /**
         * 部门业务分类 (3学生)
         */
        public static final Integer DEPT_TYPE_XS = 3;
        /**
         * 部门业务分类 (4家长)
         */
        public static final Integer DEPT_TYPE_JZ = 4;
        /**
         * 部门业务分类 (5应用上架审核人)
         */
        public static final Integer DEPT_TYPE_YYSJSHR = 5;
        /**
         * 部门业务分类 (6应用授权审核人)
         */
        public static final Integer DEPT_TYPE_YYSQSHR = 6;
        /**
         * 部门业务分类 (7API接口授权审核人)
         */
        public static final Integer DEPT_TYPE_APIJKSQSHR = 7;
        /**
         * 部门业务分类 (8合作者)
         */
        public static final Integer DEPT_TYPE_HZZ = 8;


    }

    public static class Tree {
        public static final Integer TREE_LEVEL_FAKE = 0; //虚拟
        public static final Integer TREE_LEVEL_BUREAU = 1; //局端
        public static final Integer TREE_LEVEL_SCHOOL = 2; //校端
        public static final Integer TREE_LEVEL_BRANCH = 3; //分校
        public static final Integer TREE_LEVEL_OTHER = 4; //其他

        public static final String NODE_SYSTEM = "00";//节点-默认类型
        public static final String NODE_ORGANIZATION = "01";//节点-机构类型

        public static final String NODE_DEPARTMENT = "40";//节点-机构类型
        public static final String NODE_GRADE = "60";//节点-年级类型
        public static final String NODE_CLASS = "70";//节点-班级类型
        public static final String NODE_ROLE = "80";//节点-角色类型
        public static final String NODE_TEACHER = "10";//节点-教职工类型
        public static final String NODE_STUDENT = "20";//节点-学生类型
        public static final String NODE_PATRIARCH = "30";//节点-家长类型

        public static final String NODE_TEAM = "50";//节点-组类型

        public static final String IDCACHE_KEY_DEPT = "D";
        public static final String IDCACHE_KEY_GRADE = "G";
        public static final String IDCACHE_KEY_CLASS = "C";
        public static final String IDCACHE_KEY_ROLE = "R";
        public static final String IDCACHE_KEY_TEAM = "T";

        public static final String SOURCE_TYPE_DEPT = "D";
        public static final String SOURCE_TYPE_GRADE = "G";
        public static final String SOURCE_TYPE_CLASS = "C";
        public static final String SOURCE_TYPE_ROLE = "R";
        public static final String SOURCE_TYPE_TEAM = "T";

        public static final Integer USERSEARCH_TEACHER = 1;
        public static final Integer USERSEARCH_STUDENT = 2;
        public static final Integer USERSEARCH_PATRIARCH = 3;
    }

    public static class Auth {
        public static final Integer AUTH_TYPE_PRINCIPAL = 1; //超管
        public static final Integer AUTH_TYPE_ADMINISTRATOR = 2; //机构管理员
        public static final Integer AUTH_TYPE_PUBLISH_APP_AUDITOR = 3; // 应用上架审核人员
        public static final Integer AUTH_TYPE_APP_ACCREDIT_AUDITOR = 4; //应用授权审核人员
        public static final Integer AUTH_TYPE_API_ACCREDIT_AUDITOR = 5; //API授权审核人员

        public static final Integer AUTH_TYPE_THIRD_USER_AUDITOR = 6; //开发者审核人员

    }

    public static class Developer {
        public static final Integer USERTYPE_COMPANY = 1; //公司身份
        public static final Integer USERTYPE_PERSONAL = 2; //个人身份
        public static final Integer STATUS_PASS = 1; // 通过
        public static final Integer STATUS_WAIT = 0; // 等待审核
        public static final Integer STATUS_NOPASS = 2; // 不通过
    }

    public static class Identity {
        public static final Integer USERTYPE_TEACHER = 1; //教师
        public static final Integer USERTYPE_STUDENT = 2; //学生
        public static final Integer USERTYPE_PARENTS = 3; //家长
        public static final Integer USERTYPE_DEVELOPER = 4; //开发者
    }
}
