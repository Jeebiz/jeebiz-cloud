package io.hiwepy.cloud.authz.login.strategy;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.param.LoginByThirdParam;
import io.hiwepy.cloud.authz.login.param.RegisterParam;
import org.springframework.security.boot.weixin.authentication.WxMaAuthenticationToken;
import org.springframework.security.boot.weixin.authentication.WxMaLoginRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 微信（小程序）登录
 */
public class WxMaAuthStrategy extends AbstractAuthStrategy<LoginByThirdParam> {

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.WEIXIN_MA;
    }

    @Override
    public AuthBO<LoginByThirdParam> initInfo(Authentication token) throws AuthenticationException {

        WxMaAuthenticationToken wxToken = (WxMaAuthenticationToken) token;
        WxMaLoginRequest loginRequest = (WxMaLoginRequest) wxToken.getPrincipal();
        // 获取用户手机号信息
        WxMaPhoneNumberInfo phoneNumberInfo = loginRequest.getPhoneNumberInfo();
        // 获取用户信息
        WxMaUserInfo userInfo = loginRequest.getUserInfo();

        AuthBO<LoginByThirdParam> authBO = AuthBO.<LoginByThirdParam>builder()
                // loginRequest.getOpenid(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
                // loginRequest.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
                .account(loginRequest.getOpenid())
                .nickname(userInfo.getNickName())
                .avatar(userInfo.getAvatarUrl())
                .country(userInfo.getCountry())
                .province(userInfo.getProvince())
                .phone(phoneNumberInfo != null ? phoneNumberInfo.getPhoneNumber() : "")
                .city(userInfo.getCity())
                .gender(userInfo.getGender() == "1" ? 1 : 0)
                .channel(this.getChannel())
                .build();

        LoginByThirdParam param = new LoginByThirdParam();
        param.setOpenid(loginRequest.getOpenid());
        param.setUnionid(loginRequest.getUnionid());
        authBO.setParam(param);

        return authBO;

    }

    @Override
    protected RegisterParam getRegisterParam(AuthBO<LoginByThirdParam> authBO) {
        RegisterParam registerParam = super.getRegisterParam(authBO);
        registerParam.setAccount(authBO.getParam().getOpenid());
        return registerParam;
    }

}
