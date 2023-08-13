package io.hiwepy.cloud.authz.login.strategy;

import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.param.LoginByQrcodeParam;
import io.hiwepy.cloud.authz.login.param.RegisterParam;
import org.springframework.security.boot.qrcode.authentication.QrcodeAuthorizationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 扫码登录
 */
public class QrCodeAuthStrategy extends AbstractAuthStrategy<LoginByQrcodeParam> {

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.QRCODE_SCAN;
    }

    @Override
    public AuthBO<LoginByQrcodeParam> initInfo(Authentication token) throws AuthenticationException {

        QrcodeAuthorizationToken qrcodeToken = (QrcodeAuthorizationToken) token;

        AuthBO<LoginByQrcodeParam> authBO = AuthBO.<LoginByQrcodeParam>builder()

                .channel(this.getChannel())
                .build();

        LoginByQrcodeParam param = new LoginByQrcodeParam();
        authBO.setParam(param);

        return authBO;

    }

    @Override
    protected RegisterParam getRegisterParam(AuthBO<LoginByQrcodeParam> authBO) {
        RegisterParam registerParam = super.getRegisterParam(authBO);
        //registerParam.setAccount(authBO.getParam().getOpenid());
        return registerParam;
    }

}
