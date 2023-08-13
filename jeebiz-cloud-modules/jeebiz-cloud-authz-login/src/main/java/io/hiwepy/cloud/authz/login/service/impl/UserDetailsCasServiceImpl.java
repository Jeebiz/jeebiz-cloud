/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.login.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.param.LoginByCasParam;
import io.hiwepy.cloud.authz.login.strategy.AuthChannel;
import io.hiwepy.cloud.authz.login.strategy.AuthStrategyRouter;
import lombok.extern.slf4j.Slf4j;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class UserDetailsCasServiceImpl extends AbstractCasAssertionUserDetailsService {

    @Autowired
    private AuthStrategyRouter authStrategyRouter;

    @SuppressWarnings("unused")
    @Override
    protected UserDetails loadUserDetails(Assertion assertion) {
        /*
         * 获取用户的唯一标识信息 由UIA的配置不同可分为两种： (1)学生：学号；教工：身份证号 (2)学生：学号；教工：教工号
         */
        String ssoId = assertion.getPrincipal().getName();

        log.info("{} >> loadUserDetails assertion:{}", ssoId, JSONObject.toJSONString(assertion, SerializerFeature.WriteNonStringValueAsString));

		/*
		{
			"attributes": {},
			"authenticationDate": 1571890054243,
			"principal": {
				"attributes": {
					"comsys_department": "",
					"comsys_post": "",
					"comsys_cardid": "331082198610190357",
					"comsys_post_type": "%E5%85%B6%E4%BB%96%E8%81%8C%E5%8A%A1%E7%B1%BB%E5%9E%8B",
					"comsys_educational": "%E6%9C%AC%E7%A7%91",
					"comsys_phone": "15258299108",
					"comsys_genders": "%E7%94%B7",
					"comsys_name": "%E4%BE%AF%E5%B4%87%E5%B2%B3",
					"comsys_loginid": "2009014",
					"comsys_email": "39330397%40qq.com",
					"comsys_role": "ROLECNNAME%3A%E6%95%99%E5%B7%A5%2CROLEIDENTIFY%3AROLE_TEACHER",
					"comsys_other_post": "%E5%85%B6%E4%BB%96%E8%81%8C%E7%A7%B0",
					"comsys_usertype": "2",
					"comsys_teaching_number": "2009014"
				},
				"name": "2009014"
			},
			"valid": true,
			"validFromDate": 1571890054243
		}*/

        AuthBO<LoginByCasParam> authBO = AuthBO.<LoginByCasParam>builder()
                .account(ssoId)
                .channel(AuthChannel.CAS)
                .build();

        LoginByCasParam param = new LoginByCasParam();
        param.setAssertion(assertion);
        authBO.setParam(param);

        return authStrategyRouter.route(authBO).login(authBO);

    }

}