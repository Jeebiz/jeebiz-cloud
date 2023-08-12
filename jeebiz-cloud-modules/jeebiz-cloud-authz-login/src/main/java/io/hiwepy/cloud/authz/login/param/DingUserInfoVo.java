package io.hiwepy.cloud.authz.login.param;

import com.dingtalk.api.response.OapiV2UserGetResponse;
import lombok.Data;

/**
 * @author wj
 * @version 1.0
 * @description
 * @date 2022/7/4 16:16
 */
@Data
public class DingUserInfoVo extends OapiV2UserGetResponse.UserGetResponse {

    private String accessToken;
}
