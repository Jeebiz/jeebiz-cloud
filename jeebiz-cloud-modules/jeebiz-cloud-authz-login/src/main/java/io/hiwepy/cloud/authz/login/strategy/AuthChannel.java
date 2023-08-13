/**
 * Copyright (C) 2018 Hiwepy (http://hiwepy.io).
 * All Rights Reserved.
 */
package io.hiwepy.cloud.authz.login.strategy;

import org.pac4j.springframework.security.authentication.Pac4jAuthenticationToken;
import org.springframework.security.boot.dingtalk.authentication.DingTalkMaAuthenticationToken;
import org.springframework.security.boot.dingtalk.authentication.DingTalkScanCodeAuthenticationToken;
import org.springframework.security.boot.dingtalk.authentication.DingTalkTmpCodeAuthenticationToken;
import org.springframework.security.boot.jwt.authentication.JwtAuthenticationToken;
import org.springframework.security.boot.jwt.authentication.JwtAuthorizationToken;
import org.springframework.security.boot.qrcode.authentication.QrcodeAuthorizationToken;
import org.springframework.security.boot.weixin.authentication.WxMaAuthenticationToken;
import org.springframework.security.boot.weixin.authentication.WxMpAuthenticationToken;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.*;
import java.util.function.BiFunction;

/**
 * 认证方式
 */
public enum AuthChannel {

    ACCOUNT("account", "Account登录", (token, p2) -> false),
    USER_ID("userId", "UserId登录", (token, p2) -> false),
    TOKEN("token", "UserId登录", (token, p2) -> JwtAuthorizationToken.class.isAssignableFrom(token.getClass())),
    CAS("cas", "Cas登录", (token, p2) -> CasAssertionAuthenticationToken.class.isAssignableFrom(token.getClass())),
    PASSWORD("password", "密码登录", (token, p2) -> JwtAuthenticationToken.class.isAssignableFrom(token.getClass())),
    DINGTALK_MA("dingtalk-ma", "钉钉小程序登录", (token, p2) -> DingTalkMaAuthenticationToken.class.isAssignableFrom(token.getClass())),
    DINGTALK_SCAN_CODE("dingtalk-scancode", "钉钉扫码登录", (token, p2) -> DingTalkScanCodeAuthenticationToken.class.isAssignableFrom(token.getClass())),
    DINGTALK_TMPCODE("dingtalk-tmpcode", "钉钉内部应用免登", (token, p2) -> DingTalkTmpCodeAuthenticationToken.class.isAssignableFrom(token.getClass())),
    WEIXIN_MP("wx-mp", "微信（公共号、服务号）登录", (token, p2) -> WxMpAuthenticationToken.class.isAssignableFrom(token.getClass())),
    WEIXIN_MA("wx-ma", "微信（小程序）登录", (token, p2) -> WxMaAuthenticationToken.class.isAssignableFrom(token.getClass())),
    QRCODE_SCAN("qrcode", "扫码登录", (token, p2) -> QrcodeAuthorizationToken.class.isAssignableFrom(token.getClass())),
    SMS("sms", "短信登录", (token, p2) -> QrcodeAuthorizationToken.class.isAssignableFrom(token.getClass())),
    PAC4J("pac4j", "Pac4j三方登录", (token, p2) -> Pac4jAuthenticationToken.class.isAssignableFrom(token.getClass())),
    ;

    private String key;
    private String desc;
    private BiFunction<Authentication, Object, Boolean> function;

    private AuthChannel(String key, String desc, BiFunction<Authentication, Object, Boolean> function) {
        this.key = key;
        this.desc = desc;
        this.function = function;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean equals(AuthChannel channel) {
        return this.compareTo(channel) == 0;
    }

    public boolean equals(String key) {
        return this.compareTo(AuthChannel.valueOfIgnoreCase(key)) == 0;
    }

    public static AuthChannel valueOf(Authentication token) {
        for (AuthChannel channel : AuthChannel.values()) {
            if (channel.function.apply(token, null)) {
                return channel;
            }
        }
        throw new NoSuchElementException("Cannot found AuthChannel with token '" + token.getName() + "'.");
    }

    public static AuthChannel valueOfIgnoreCase(String key) {
        for (AuthChannel channel : AuthChannel.values()) {
            if (channel.getKey().equalsIgnoreCase(key)) {
                return channel;
            }
        }
        throw new NoSuchElementException("Cannot found AuthChannel with key '" + key + "'.");
    }

    public static List<Map<String, String>> toList() {
        List<Map<String, String>> channelList = new LinkedList<Map<String, String>>();
        for (AuthChannel channel : AuthChannel.values()) {
            channelList.add(channel.toMap());
        }
        return channelList;
    }

    public Map<String, String> toMap() {
        Map<String, String> optMap = new HashMap<String, String>();
        optMap.put("key", this.getKey());
        optMap.put("desc", this.getDesc());
        return optMap;
    }

}
