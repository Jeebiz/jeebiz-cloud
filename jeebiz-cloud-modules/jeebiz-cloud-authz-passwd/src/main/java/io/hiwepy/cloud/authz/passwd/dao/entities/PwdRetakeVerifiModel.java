package io.hiwepy.cloud.authz.passwd.dao.entities;

import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * 账号核实字段表对象模型
 */
@Alias("PwdRetakeVerifiModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class PwdRetakeVerifiModel extends PaginationEntity<PwdRetakeVerifiModel> {

    /**
     * 账号核实字段表ID
     */
    protected String id;
    /**
     * 账号核实字段名称
     */
    protected String name;
    /**
     * 账号核实字段Label名称
     */
    protected String label;
    /**
     * 账号核实字段描述，作为提示信息
     */
    protected String desc;
    /**
     * 账号核实字段校验规则
     */
    protected String rules;
    /**
     * 账号核实字段是否必填，1：是，0：否
     */
    protected String required;
    /**
     * 账号核实字段启用状态标记，1：启用，0：停用
     */
    protected String status;
    /**
     * 关键词搜索
     */
    private String keywords;

}
