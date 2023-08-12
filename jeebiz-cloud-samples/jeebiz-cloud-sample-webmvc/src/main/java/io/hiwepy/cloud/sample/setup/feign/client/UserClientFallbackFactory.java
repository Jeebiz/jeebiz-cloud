package io.hiwepy.cloud.sample.setup.feign.client;

import feign.hystrix.FallbackFactory;
import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.cloud.api.dto.UserInfoDTO;
import io.hiwepy.cloud.api.dto.UserInfoSimpleDTO;
import io.hiwepy.cloud.autoconfigure.feign.FeignFallbackHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 数美鉴黄接口降级
 */
@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClientApi> {

    @Autowired
    protected FeignFallbackHandler fallbackHandler;

    @Override
    public UserClientApi create(Throwable throwable) {
        fallbackHandler.handle(throwable);
        return new  UserClientApi() {

			@Override
			public ApiRestResponse<UserInfoDTO> getUserInfoByUserId(Long userId) {
				return ApiRestResponse.fail("Feign调用异常");
			}

			@Override
			public ApiRestResponse<UserInfoSimpleDTO> getBaseUserInfoByUserCode(String userCode) {
				return ApiRestResponse.fail("Feign调用异常");
			}

			@Override
			public ApiRestResponse<List<UserInfoDTO>> getUserInfoListByUserIds(Collection<Long> userIds) {
				return ApiRestResponse.fail("Feign调用异常");
			}

			@Override
			public ApiRestResponse<UserInfoSimpleDTO> getUserInfoSimpleByUserId(Long userId) {
				return ApiRestResponse.fail("Feign调用异常");
			}

			@Override
			public ApiRestResponse<List<UserInfoSimpleDTO>> getUserInfoSimpleListByUserIds(Collection<Long> userIds) {
				return ApiRestResponse.fail("Feign调用异常");
			}

			@Override
			public ApiRestResponse<Set<Long>> getMatchUserIds(String keywords) {
				return ApiRestResponse.fail("Feign调用异常");
			}

		};
    }
}
