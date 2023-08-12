package io.hiwepy.cloud.authz.login.strategy;

import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.param.LoginByPasswordParam;
import io.hiwepy.cloud.authz.login.param.RegisterParam;
import org.springframework.security.boot.jwt.authentication.JwtAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 账号密码登录策略
 */
public class PasswordAuthStrategy extends AbstractAuthStrategy<LoginByPasswordParam> {

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.PASSWORD;
    }

    @Override
    public AuthBO<LoginByPasswordParam> initInfo(Authentication token) throws AuthenticationException {

        JwtAuthenticationToken upToken = (JwtAuthenticationToken) token;

        AuthBO<LoginByPasswordParam> authBO = AuthBO.<LoginByPasswordParam>builder()
                .account(upToken.getPrincipal().toString())
                .password(upToken.getCredentials().toString())
                .channel(this.getChannel())
                .build();

        LoginByPasswordParam param = new LoginByPasswordParam();
        param.setAccount(upToken.getPrincipal().toString());
        param.setPassword(upToken.getCredentials().toString());
        authBO.setParam(param);

        return authBO;

    }

    @Override
    protected Boolean needReg(AuthBO<LoginByPasswordParam> authBO) {
        Integer needRegType = 1;
        return needRegType.equals(((LoginByPasswordParam) (authBO.getParam())).getNeedReg());
    }

    @Override
    protected RegisterParam getRegisterParam(AuthBO<LoginByPasswordParam> authBO) {
        RegisterParam registerParam = super.getRegisterParam(authBO);
        registerParam.setAccount(authBO.getAccount());
        return registerParam;
    }

}
