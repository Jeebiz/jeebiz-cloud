package io.hiwepy.cloud.authz.login.strategy;

import com.github.hiwepy.jwt.JwtPayload;
import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.param.LoginByTokenParam;
import io.hiwepy.cloud.authz.login.param.RegisterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.boot.biz.userdetails.JwtPayloadRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Token登录策略
 */
public class TokenAuthStrategy extends AbstractAuthStrategy<LoginByTokenParam> {

    @Autowired
    private JwtPayloadRepository jwtPayloadRepository;

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.TOKEN;
    }

    @Override
    public AuthBO<LoginByTokenParam> initInfo(Authentication token) throws AuthenticationException {

        String tokenStr = (String) token.getCredentials();

        AuthBO<LoginByTokenParam> authBO = AuthBO.<LoginByTokenParam>builder()
                .channel(this.getChannel())
                .build();

        LoginByTokenParam param = new LoginByTokenParam();
        param.setToken(tokenStr);
        authBO.setParam(param);

        JwtPayload payload = jwtPayloadRepository.getPayload(tokenStr, false);

        authBO.setUserId(Long.parseLong(payload.getUid()));
        authBO.setAccountId(Long.parseLong(payload.getUuid()));

        return authBO;
    }

    @Override
    public AuthBO<LoginByTokenParam> initInfo(AuthBO<LoginByTokenParam> authBO) throws AuthenticationException {

        LoginByTokenParam param = authBO.getParam();
        JwtPayload payload = jwtPayloadRepository.getPayload(param.getToken(), false);

        authBO.setUserId(Long.parseLong(payload.getUid()));
        authBO.setAccountId(Long.parseLong(payload.getUuid()));

        return authBO;
    }

    @Override
    protected RegisterParam getRegisterParam(AuthBO<LoginByTokenParam> authBO) {
        RegisterParam registerParam = super.getRegisterParam(authBO);
        registerParam.setAccount(authBO.getAccount());
        return registerParam;
    }

}
