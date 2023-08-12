package io.hiwepy.cloud.authz.login.strategy;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.param.LoginByThirdParam;
import io.hiwepy.cloud.authz.login.param.RegisterParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.boot.biz.userdetails.JwtPayloadRepository;
import org.springframework.security.boot.dingtalk.authentication.DingTalkScanCodeAuthenticationToken;
import org.springframework.security.boot.dingtalk.authentication.DingTalkScanCodeLoginRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;

/**
 * 钉钉扫码登录第三方网站
 * https://open.dingtalk.com/document/orgapp-server/scan-qr-code-to-log-on-to-third-party-websites
 */
@Slf4j
public class DingtalkScanCodeAuthStrategy extends AbstractAuthStrategy<LoginByThirdParam> {

    @Autowired
    private JwtPayloadRepository jwtPayloadRepository;

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.DINGTALK_SCAN_CODE;
    }

    @Override
    public AuthBO<LoginByThirdParam> initInfo(Authentication token) throws AuthenticationException {

        DingTalkScanCodeAuthenticationToken ddToken = (DingTalkScanCodeAuthenticationToken) token;
        OapiSnsGetuserinfoBycodeResponse.UserInfo userInfo = ddToken.getUserInfo();

        AuthBO<LoginByThirdParam> authBO = AuthBO.<LoginByThirdParam>builder()
                // ddToken.getOpenid(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
                // ddToken.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
                .account(ddToken.getUnionid())
                .nickname(userInfo.getNick())
                .channel(this.getChannel())
                .build();

        LoginByThirdParam param = new LoginByThirdParam();
        param.setOpenid(ddToken.getOpenid());
        param.setUnionid(ddToken.getUnionid());
        param.setRawdata(JSONObject.toJSONString(userInfo));
        DingTalkScanCodeLoginRequest loginRequest = (DingTalkScanCodeLoginRequest) ddToken.getPrincipal();
        param.setToken(loginRequest.getToken());
        authBO.setParam(param);
        return authBO;

    }

    @Override
    protected Boolean needReg(AuthBO<LoginByThirdParam> authBO) {
        String token = authBO.getParam().getToken();
        return this.isRegisterSwitch() || StringUtils.hasText(token);
    }

    @Override
    protected RegisterParam getRegisterParam(AuthBO<LoginByThirdParam> authBO) {
        RegisterParam registerParam = super.getRegisterParam(authBO);
        registerParam.setAccount(authBO.getParam().getUnionid());
        registerParam.setPassword(authBO.getParam().getUnionid());
        registerParam.setRawdata(authBO.getParam().getRawdata());
        String token = authBO.getParam().getToken();
        //根据手机号姓名查询用户
        if (StringUtils.hasText(token)) {
            if (authBO.getAccount().equals(token)) {
                registerParam.setUserId(null);
            } else {
                //扫码绑定
                String userId = jwtPayloadRepository.getPayload(authBO.getParam().getToken(), false).getUid();
                registerParam.setUserId(userId);
            }
        }
        return registerParam;
    }

}