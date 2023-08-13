/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.dbperms.setup.config;

import org.apache.mybatis.dbperms.MybatisDbpermsProperties;
import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MybatisDbpermsProperties.class)
public class JeebizDataPermissionConfiguration {

    @Bean
    public FlywayFluentConfiguration flywayDbpermsConfiguration() {

        FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("dbperms",
                "数据权限-模块初始化", "1.0.0");

        return configuration;
    }
	
/*
	protected static Logger LOG = LoggerFactory.getLogger(DataPermissionConfiguration.class);
	
	@Autowired
    private RedisOperationTemplate redisOperation;
	
	@Autowired
    private IDataPermissionAllotDao dataPermissionAllotDao;
    
	@Bean
    public ITablePermissionAutowireHandler tablePermissionAutowireHandler(MybatisDbpermsProperties properties) {
    	
    	BoundHashOperations<String, Object, Object> opsForHash = getRedisTemplate().boundHashOps(Constants.PERMS_CACHE_HASH);
        //opsForHash.expire(properties.getExpire(), properties.getUnit());
        
    	return new DefaultTablePermissionAutowireHandler((metaHandler, tableName) -> {
    		// 获取当前登录用户主体对象
        	SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
        	// 根据当前角色ID和用户ID,构建Hashkey
        	String permsHashKey = String.format(Constants.PERMS_CACHE_HASH, principal.getRid(), principal.getUid());
        	// 检查缓存是否有数据
        	List<DataPermission> retList = null;
        	if (getRedisTemplate().hasKey(Constants.PERMS_CACHE_KEY) && opsForHash.hasKey(permsHashKey)) {
            	retList = (List<DataPermission>) opsForHash.get(permsHashKey);
            	LOG.debug("load {} Data Permission from redis !", CollectionUtils.isEmpty(retList) ? 0 : retList.size());
            	if (!CollectionUtils.isEmpty(retList)) {
            		return retList.stream().filter(perm -> StringUtils.equalsIgnoreCase(perm.getTable(), tableName)).findFirst();
            	}
            }
        	// 缓存中无数据，尝试从数据库查询
            if(CollectionUtils.isEmpty(retList)) {
            	// 根据当前角色和用户查询数据库对应的数据权限
            	List<DataPermissionModel> perms = getDataPermissionAllotDao().getDataPermissions(principal.getRid(), principal.getUid());
            	// 判断是否有数据权限限制
            	if(!CollectionUtils.isEmpty(perms)) {
        			// 组装表对应的数据权限
        			retList = perms.stream().map(permModel -> {
        				// 数据权限
            			DataPermission perm = new DataPermission();
            			// 数据权限项集合
            			List<DataPermissionColumn> columns = new ArrayList<>();
            			if(!CollectionUtils.isEmpty(permModel.getItems())) {
            				columns = permModel.getItems().stream().map(itemModel -> {
            					// 对权限项进行类型转换
            					DataPermissionColumn column = new DataPermissionColumn();
                				column.setColumn(itemModel.getColumn());
                				column.setCondition(itemModel.getCondition());
                				column.setOrder(itemModel.getOrder());
                				column.setPerms(itemModel.getPerms());
                				column.setStatus(itemModel.getStatus());
                				return column;
            				}).collect(Collectors.toList());
            			}
            			perm.setTable(permModel.getTable());
            			perm.setRelation(permModel.getRelation());
            			perm.setStatus(permModel.getStatus());
            			perm.setColumns(columns);
            			return perm;
        			}).collect(Collectors.toList());
            	}
            	if (!CollectionUtils.isEmpty(retList)) {
                    opsForHash.put(permsHashKey, retList);
                    return retList.stream().filter(perm -> StringUtils.equalsIgnoreCase(perm.getTable(), tableName)).findFirst();
                }
                LOG.debug("load {} Data Permission from datasource!", retList.size());
            }
    		return Optional.empty();
    	});
    }
    
    public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}
    
   public IDataPermissionAllotDao getDataPermissionAllotDao() {
	   return dataPermissionAllotDao;
   }
    
*/
}
