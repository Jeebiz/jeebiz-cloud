package io.hiwepy.cloud.authz.login.strategy;

import io.hiwepy.cloud.api.UserProfiles;
import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.param.LoginByAccountParam;
import io.hiwepy.cloud.authz.login.param.RegisterParam;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.springframework.security.authentication.Pac4jAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 第三方认证登录
 */
public class Pac4jAuthStrategy extends AbstractAuthStrategy<LoginByAccountParam> {

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.PAC4J;
    }

    @Override
    public AuthBO<LoginByAccountParam> initInfo(Authentication token) throws AuthenticationException {

        Pac4jAuthenticationToken pac4jToken = (Pac4jAuthenticationToken) token;
        CommonProfile profile = pac4jToken.getProfile();

        AuthBO<LoginByAccountParam> authBO = AuthBO.<LoginByAccountParam>builder()
                // wxToken.getOpenid(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
                // wxToken.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
                .account(profile.getId())
                .nickname(profile.getDisplayName())
                .avatar(profile.getAttribute(UserProfiles.AVATAR, String.class))
                .gender(profile.getAttribute(UserProfiles.GENDER, Integer.class))
                .channel(this.getChannel())
                .build();

        LoginByAccountParam param = new LoginByAccountParam();
        param.setAccount(profile.getId());
        authBO.setParam(param);

        return authBO;

    }

    @Override
    protected RegisterParam getRegisterParam(AuthBO<LoginByAccountParam> authBO) {
        RegisterParam registerParam = super.getRegisterParam(authBO);
        registerParam.setAccount(authBO.getParam().getAccount());
        return registerParam;
    }

}
