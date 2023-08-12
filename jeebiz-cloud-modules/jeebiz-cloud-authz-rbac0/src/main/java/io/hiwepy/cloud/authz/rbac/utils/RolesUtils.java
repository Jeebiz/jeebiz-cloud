/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.rbac.utils;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class RolesUtils {

    /**
     * 获取角色id增量集合
     * @param roles        ：此次提交的角色id
     * @param oldroles    ： 已经角色id
     * @return
     */
    public static List<String> increment(List<String> roles, List<String> oldroles) {
        if (CollectionUtils.isEmpty(roles)) {
            return Lists.newArrayList();
        }
        return roles.stream()
                .filter(serv -> !oldroles.contains(serv))
                .collect(Collectors.toList());
    }

    /**
     * 获取角色id减量集合
     * @param roles        ：此次提交的角色id
     * @param oldroles    ： 已经角色id
     * @return
     */
    public static List<String> decrement(List<String> roles, List<String> oldroles) {
        if (CollectionUtils.isEmpty(oldroles)) {
            return Lists.newArrayList(roles);
        }
        return oldroles.stream()
                .filter(serv -> !roles.contains(serv))
                .collect(Collectors.toList());
    }

}
