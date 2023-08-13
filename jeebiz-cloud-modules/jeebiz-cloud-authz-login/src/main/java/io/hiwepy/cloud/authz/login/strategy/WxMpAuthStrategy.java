package io.hiwepy.cloud.authz.login.strategy;

import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.param.LoginByThirdParam;
import io.hiwepy.cloud.authz.login.param.RegisterParam;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.springframework.security.boot.weixin.authentication.WxMpAuthenticationToken;
import org.springframework.security.boot.weixin.authentication.WxMpLoginRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 微信（公共号、服务号）登录
 */
public class WxMpAuthStrategy extends AbstractAuthStrategy<LoginByThirdParam> {

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.WEIXIN_MP;
    }

    @Override
    public AuthBO<LoginByThirdParam> initInfo(Authentication token) throws AuthenticationException {

        WxMpAuthenticationToken wxToken = (WxMpAuthenticationToken) token;
        WxMpLoginRequest loginRequest = (WxMpLoginRequest) wxToken.getPrincipal();
        WxOAuth2UserInfo userInfo = loginRequest.getUserInfo();

        AuthBO<LoginByThirdParam> authBO = AuthBO.<LoginByThirdParam>builder()
                // loginRequest.getOpenid(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
                // loginRequest.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
                .account(loginRequest.getOpenid())
                .nickname(userInfo.getNickname())
                .avatar(userInfo.getHeadImgUrl())
                .country(userInfo.getCountry())
                .province(userInfo.getProvince())
                .city(userInfo.getCity())
                .gender(userInfo.getSex())
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
