/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.setup.config;

import com.github.dozermapper.core.Mapper;
import io.hiwepy.cloud.authz.dbperms.service.IDataPermissionQueryService;
import io.hiwepy.cloud.authz.dbperms.setup.DbPermsRedisKey;
import org.apache.mybatis.dbperms.MybatisDbpermsProperties;
import org.apache.mybatis.dbperms.dto.DataPermission;
import org.apache.mybatis.dbperms.dto.DataPermissionPayload;
import org.apache.mybatis.dbperms.dto.DataSpecialPermission;
import org.apache.mybatis.dbperms.parser.DefaultTablePermissionAutowireHandler;
import org.apache.mybatis.dbperms.parser.ITablePermissionAutowireHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableConfigurationProperties(MybatisDbpermsProperties.class)
public class MybatisDbpermsConfiguration {

    protected static Logger LOG = LoggerFactory.getLogger(MybatisDbpermsConfiguration.class);

    @Autowired
    private RedisOperationTemplate redisOperation;
    @Autowired
    private Mapper beanMapper;

    /**
     * mybatis-dbperms 数据权限插件
     */
    @Bean
    public ITablePermissionAutowireHandler tablePermissionAutowireHandler(IDataPermissionQueryService dataPermissionQuery, MybatisDbpermsProperties properties) {
        String dbPermsKey = DbPermsRedisKey.PERMS_CACHE.getFunction().apply("", "");
        return new DefaultTablePermissionAutowireHandler((metaHandler, tableName) -> {

            // 获取当前登录用户主体对象
            SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);

            // admin 不做数据权限处理
            if (null == principal || principal.isAdmin()) {
                LOG.debug("当前角色是超级管理员，不做数据权限限制！");
                return Optional.empty();
            }

            LOG.debug("处理表{}数据权限！", tableName);

            DataPermissionPayload payload = null;
            // 根据当前角色ID和用户ID,构建Hashkey
            String permsHashKey = DbPermsRedisKey.PERMS_CACHE_HASH.getFunction().apply(principal.getRid(), principal.getUid());
            // 检查缓存是否有数据
            if (redisOperation.hHasKey(dbPermsKey, permsHashKey)) {
                payload = (DataPermissionPayload) redisOperation.hGet(dbPermsKey, permsHashKey);
                if (null != payload) {
                    LOG.debug("load Permission from redis !");
                    return Optional.of(payload);
                }
                return Optional.empty();
            }
            // 缓存中无数据，尝试从数据库查询
            if (null == payload) {

                payload = new DataPermissionPayload();

                // 根据当前角色和用户查询数据库对应的数据权限
                List<DataPermission> permissions = dataPermissionQuery.getPermissions(principal.getRid(), principal.getUid());
                List<DataSpecialPermission> specialPermissions = dataPermissionQuery.getSpecialPermissions(principal.getRid(), principal.getUid());
                if (!CollectionUtils.isEmpty(permissions) || !CollectionUtils.isEmpty(specialPermissions)) {

                    payload.setPermissions(permissions);
                    payload.setSpecialPermissions(specialPermissions);
                    redisOperation.hSet(dbPermsKey, permsHashKey, payload);

                    LOG.debug("load Data Permission from datasource !");
                    return Optional.of(payload);
                } else {
                    redisOperation.hSet(dbPermsKey, permsHashKey, payload);
                }
            }
            return Optional.empty();
        });
    }


    public Mapper getBeanMapper() {
        return beanMapper;
    }

}
