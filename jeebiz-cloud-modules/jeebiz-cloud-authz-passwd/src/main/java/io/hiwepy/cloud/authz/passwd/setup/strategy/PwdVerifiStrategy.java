package io.hiwepy.cloud.authz.passwd.setup.strategy;

import io.hiwepy.cloud.authz.passwd.setup.dto.PwdRetakeResult;
import io.hiwepy.cloud.authz.passwd.web.dto.PwdRetakeVerifiWrapDTO;

public interface PwdVerifiStrategy {

    /**
     * 账号核实字段名称，该名称将对应系统提供的账号核实字段名称
     */
    public String name();

    /**
     * 账号核实字段验证结果
     */
    public PwdRetakeResult verifi(PwdRetakeVerifiWrapDTO verifiDTO);

}
