package io.hiwepy.cloud.authz.login.strategy;

import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.spring.boot.DingTalkTemplate;
import com.taobao.api.ApiException;
import io.hiwepy.cloud.authz.login.bo.AuthBO;
import io.hiwepy.cloud.authz.login.param.LoginByThirdParam;
import io.hiwepy.cloud.authz.login.param.RegisterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.boot.dingtalk.authentication.DingTalkTmpCodeAuthenticationToken;
import org.springframework.security.boot.dingtalk.authentication.DingTalkTmpCodeLoginRequest;
import org.springframework.security.boot.dingtalk.exception.DingTalkAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 内部应用免登
 */
public class DingtalkTmpCodeAuthStrategy extends AbstractAuthStrategy<LoginByThirdParam> {

    @Autowired
    private DingTalkTemplate dingTalkTemplate;

    @Override
    public AuthChannel getChannel() {
        return AuthChannel.DINGTALK_TMPCODE;
    }

    @Override
    public AuthBO<LoginByThirdParam> initInfo(Authentication token) throws AuthenticationException {

        DingTalkTmpCodeAuthenticationToken ddToken = (DingTalkTmpCodeAuthenticationToken) token;

        DingTalkTmpCodeLoginRequest loginRequest = (DingTalkTmpCodeLoginRequest) ddToken.getPrincipal();

        try {

            OapiUserGetuserinfoResponse response = dingTalkTemplate.opsForUser().getUserinfoByCode(loginRequest.getCode(), loginRequest.getAccessToken());
            if (!response.isSuccess()) {
                throw new DingTalkAuthenticationServiceException(response.getErrmsg());
            }
            // 根据UserId 获取用户信息
            OapiUserGetResponse userInfoResponse = dingTalkTemplate.opsForAccount().getUserByUserid(response.getUserid(), loginRequest.getAccessToken());
            if (!userInfoResponse.isSuccess()) {
                throw new DingTalkAuthenticationServiceException(userInfoResponse.getErrmsg());
            }

            AuthBO<LoginByThirdParam> authBO = AuthBO.<LoginByThirdParam>builder()
                    // userInfoResponse.getOpenId(): 第三方平台 Unionid（通常指第三方账号体系下用户的唯一id）
                    // userInfoResponse.getUnionid(): 第三方平台 Openid（通常指第三方账号体系下某应用中用户的唯一id）
                    .account(response.getUserid())
                    .nickname(userInfoResponse.getNickname())
                    .avatar(userInfoResponse.getAvatar())
                    .userCode(userInfoResponse.getJobnumber())
                    .phone(userInfoResponse.getMobile())
                    .email(userInfoResponse.getEmail())
                    .channel(this.getChannel())
                    .build();

            LoginByThirdParam param = new LoginByThirdParam();
            param.setOpenid(userInfoResponse.getOpenId());
            param.setUnionid(userInfoResponse.getUnionid());
            authBO.setParam(param);

            return authBO;

        } catch (ApiException e) {
            throw new DingTalkAuthenticationServiceException(e.getErrMsg(), e);
        }

    }

    @Override
    protected RegisterParam getRegisterParam(AuthBO<LoginByThirdParam> authBO) {
        RegisterParam registerParam = super.getRegisterParam(authBO);
        registerParam.setAccount(authBO.getAccount());
        return registerParam;
    }

}
