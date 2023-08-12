package io.hiwepy.cloud.sample.service.impl;

import io.hiwepy.boot.api.service.BaseServiceImpl;
import io.hiwepy.boot.api.utils.CollectionUtils;
import io.hiwepy.cloud.api.UserProfiles;
import io.hiwepy.cloud.sample.dao.DataPermissionQueryMapper;
import io.hiwepy.cloud.sample.service.IDataPermissionQueryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.mybatis.dbperms.annotation.Condition;
import org.apache.mybatis.dbperms.dto.DataPermission;
import org.apache.mybatis.dbperms.dto.DataPermissionColumn;
import org.apache.mybatis.dbperms.dto.DataSpecialPermission;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DataPermissionQueryServiceImpl extends BaseServiceImpl<DataPermissionQueryMapper, DataPermission> implements IDataPermissionQueryService {

    public static Pattern pattern_find = Pattern.compile("(?:(?:\\#\\{)([\\S]*?)(?:\\}))+", Pattern.CASE_INSENSITIVE);

    @Override
    public List<DataPermission> getPermissions(String rid, String uid) {

        // 获取当前登录用户主体对象
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);

        // admin 不做数据权限处理
        if (null == principal || principal.isAdmin()) {
            return null;
        }

        // 普通数据权限控制
        return getBaseMapper().getPermissions(rid, uid);
    }

    @Override
    public List<DataPermissionColumn> getPermissionColumns(String gid) {
        return getBaseMapper().getPermissionColumns(gid);
    }

    @Override
    public List<DataSpecialPermission> getSpecialPermissions(String rid, String uid) {

        // 获取当前登录用户主体对象
        SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        // admin 不做数据权限处理
        if (null == principal || principal.isAdmin()) {
            return null;
        }
        // 特殊权限控制
        List<DataSpecialPermission> specialPerms = getBaseMapper().getSpecialPermissions(rid, uid);
        if (CollectionUtils.isNotEmpty(specialPerms)) {

            // 循环特殊权限
            for (DataSpecialPermission specialPermission : specialPerms) {

                /**
                 * 数据权限项
                 */
                DataPermissionColumn column = new DataPermissionColumn();

                column.setColumn(specialPermission.getColumn());
                column.setCondition(Condition.EQ);
                column.setOrder(specialPermission.getOrder());
                column.setStatus(specialPermission.getStatus());

                // 根据SQL解析限制数据
                String permsSQL = StringUtils.trim(specialPermission.getSql());
                // 用户ID
                if (StringUtils.equalsIgnoreCase(permsSQL, UserProfiles.UID)) {
                    column.setPerms(StringUtils.defaultString(principal.getUid()));
                    specialPermission.setColumns(Arrays.asList(column));
                }
                // 用户业务ID
                else if (StringUtils.equalsIgnoreCase(permsSQL, UserProfiles.UKEY)) {
                    column.setPerms(StringUtils.defaultString(principal.getUkey()));
                    specialPermission.setColumns(Arrays.asList(column));
                }
                // 用户业务code
                else if (StringUtils.equalsIgnoreCase(permsSQL, UserProfiles.UCODE)) {
                    column.setPerms(StringUtils.defaultString(principal.getUcode()));
                    specialPermission.setColumns(Arrays.asList(column));
                }
                // 角色ID
                else if (StringUtils.equalsIgnoreCase(permsSQL, UserProfiles.RID)) {
                    column.setPerms(StringUtils.defaultString(principal.getRid()));
                    specialPermission.setColumns(Arrays.asList(column));
                }
                // 角色编码
                else if (StringUtils.equalsIgnoreCase(permsSQL, UserProfiles.RKEY)) {
                    column.setPerms(StringUtils.defaultString(principal.getRkey()));
                    specialPermission.setColumns(Arrays.asList(column));
                }
                // SQL语句
                else {

                    // uid、ukey、rid、rkey
                    Matcher matcher = pattern_find.matcher(permsSQL);
                    // 查找匹配的#{}片段
                    while (matcher.find()) {
                        //在#{}区域内符合当前正则表达式的整段字符
                        String segment = matcher.group(0);
                        // 取得#{}内容开始结束位置
                        int begain = permsSQL.indexOf(segment);
                        int end = begain + segment.length();
                        // 获取#{}中间的内容
                        String cname = matcher.group(1);

                        // 用户ID
                        if (StringUtils.equalsIgnoreCase(cname, UserProfiles.UID)) {
                            permsSQL = permsSQL.substring(0, begain) + StringUtils.wrap(principal.getUid(), "'") + permsSQL.substring(end);
                        }
                        // 用户业务ID
                        else if (StringUtils.equalsIgnoreCase(cname, UserProfiles.UKEY)) {
                            permsSQL = permsSQL.substring(0, begain) + StringUtils.wrap(principal.getUkey(), "'") + permsSQL.substring(end);
                        }
                        // 角色ID
                        else if (StringUtils.equalsIgnoreCase(cname, UserProfiles.RID)) {
                            permsSQL = permsSQL.substring(0, begain) + StringUtils.wrap(principal.getRid(), "'") + permsSQL.substring(end);
                        }
                        // 用户教工号
                        else if (StringUtils.equalsIgnoreCase(cname, UserProfiles.UCODE)) {
                            permsSQL = permsSQL.substring(0, begain) + StringUtils.wrap(principal.getUcode(), "'") + permsSQL.substring(end);
                        }
                        // 角色编码
                        else if (StringUtils.equalsIgnoreCase(cname, UserProfiles.RKEY)) {
                            permsSQL = permsSQL.substring(0, begain) + StringUtils.wrap(principal.getRkey(), "'") + permsSQL.substring(end);
                        } else {
                            permsSQL = permsSQL.substring(0, begain) + "" + permsSQL.substring(end);
                        }
                    }

                    if (!(StringUtils.startsWith(permsSQL, "(") && StringUtils.endsWith(permsSQL, ")"))) {
                        specialPermission.setSql("(" + permsSQL + ")");
                    } else {
                        specialPermission.setSql(permsSQL);
                    }

                }

            }

            return specialPerms;
        }

        return null;
    }

}
