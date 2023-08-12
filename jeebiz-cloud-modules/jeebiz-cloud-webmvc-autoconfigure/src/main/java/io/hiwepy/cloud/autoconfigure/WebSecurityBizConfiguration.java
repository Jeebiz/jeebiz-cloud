/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@Configuration
public class WebSecurityBizConfiguration {

    /**
     * 密码加密工具
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // PasswordEncoder encoder =
        // PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder(6);
    }

    /**
     * 将登陆后的用户及权限信息存入session中
     *
     * @return
     */
    @Bean
    ServerSecurityContextRepository serverSecurityContextRepository() {
        return new WebSessionServerSecurityContextRepository();
    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(6);
        System.out.println(passwordEncoder.encode("123456"));
    }
}
