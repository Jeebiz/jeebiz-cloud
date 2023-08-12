package io.hiwepy.cloud.authz.passwd.dao.entities;

import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * 密码找回策略Model对象
 */
@Alias("PwdRetakeStrategyModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class PwdRetakeStrategyModel extends PaginationEntity<PwdRetakeStrategyModel> {

    /**
     * 策略表ID
     */
    protected String id;

    /**
     * 策略Key，该名称必须与策略实现对象name方法提供的返回值一致
     */
    protected String key;

    /**
     * 策略名称
     */
    protected String name;

    /**
     * 策略描述，简述该策略的实现方式
     */
    protected String desc;

    /**
     * 是否启用，1：启用(该状态下系统必要有与name对应的策略实现,才能有效)，0：停用
     */
    protected String status;
    /**
     * 关键词搜索
     */
    private String keywords;

}
