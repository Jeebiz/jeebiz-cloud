package io.hiwepy.cloud.sample.setup.feign.client;

import io.hiwepy.boot.api.ApiRestResponse;
import io.hiwepy.cloud.api.dto.UserInfoDTO;
import io.hiwepy.cloud.api.dto.UserInfoSimpleDTO;
import io.hiwepy.cloud.autoconfigure.FeignClientsExtConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@FeignClient(name = "jeebiz-cloud-user", fallback = UserClientFallbackFactory.class, configuration = FeignClientsExtConfiguration.class)
@Component
public interface UserClientApi {

    @PostMapping("/user/api/getUserInfoByUserId")
    ApiRestResponse<UserInfoDTO> getUserInfoByUserId(@RequestParam("userId") Long userId);

    @PostMapping("/user/api/getBaseUserInfoByUserCode")
    ApiRestResponse<UserInfoSimpleDTO> getBaseUserInfoByUserCode(@RequestParam("userCode") String userCode);

    @PostMapping("/user/api/getUserInfoListByUserIds")
    ApiRestResponse<List<UserInfoDTO>> getUserInfoListByUserIds(@RequestBody Collection<Long> userIds);

    @PostMapping("/user/api/getUserInfoSimpleByUserId")
    ApiRestResponse<UserInfoSimpleDTO> getUserInfoSimpleByUserId(@RequestParam("userId") Long userId);

    @PostMapping("/user/api/getUserInfoSimpleListByUserIds")
    ApiRestResponse<List<UserInfoSimpleDTO>> getUserInfoSimpleListByUserIds(@RequestBody Collection<Long> userIds);

    @PostMapping("/user/api/getMatchUserIds")
    ApiRestResponse<Set<Long>> getMatchUserIds(@RequestParam("keywords") String keywords);

}